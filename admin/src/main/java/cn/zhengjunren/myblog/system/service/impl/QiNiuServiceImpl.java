package cn.zhengjunren.myblog.system.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.zhengjunren.myblog.common.dto.ListInfo;
import cn.zhengjunren.myblog.common.exception.BadRequestException;
import cn.zhengjunren.myblog.common.utils.FileUtil;
import cn.zhengjunren.myblog.common.utils.QiNiuUtil;
import cn.zhengjunren.myblog.system.domain.QiniuConfig;
import cn.zhengjunren.myblog.system.domain.QiniuContent;
import cn.zhengjunren.myblog.system.dto.condition.QiniuQueryCondition;
import cn.zhengjunren.myblog.system.mapper.QiniuConfigMapper;
import cn.zhengjunren.myblog.system.mapper.QiniuContentMapper;
import cn.zhengjunren.myblog.system.service.QiNiuService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * <p>ClassName: QiNiuServiceImpl</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/30 14:36
 */
@Service
@Slf4j
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QiNiuServiceImpl implements QiNiuService {

    private final QiniuContentMapper qiniuContentMapper;

    private final QiniuConfigMapper qiniuConfigMapper;


    @Value("${qiniu.max-size}")
    private Long maxSize;

    @Autowired
    public QiNiuServiceImpl(QiniuContentMapper qiniuContentMapper, QiniuConfigMapper qiniuConfigMapper) {
        this.qiniuContentMapper = qiniuContentMapper;
        this.qiniuConfigMapper = qiniuConfigMapper;
    }

    @Override
    public ListInfo page(QiniuQueryCondition condition) {
        Page<QiniuContent> page = new Page<>(condition.getPage(), condition.getLimit());
        QueryWrapper<QiniuContent> qiniuContentQueryWrapper = new QueryWrapper<>();
        boolean flag = condition.getStart() != null && condition.getEnd() != null;
        qiniuContentQueryWrapper.between(flag, QiniuContent.COL_CREATE_TIME, condition.getStart(), condition.getEnd());
        qiniuContentQueryWrapper.like(StringUtils.isNotBlank(condition.getKey()), QiniuContent.COL_NAME, condition.getKey());
        IPage<QiniuContent> qiniuContentIPage = qiniuContentMapper.selectPage(page, qiniuContentQueryWrapper);
        return new ListInfo(qiniuContentIPage.getRecords(), qiniuContentIPage.getTotal());
    }

    @Override
    public List<QiniuContent> selectAll() {
        return qiniuContentMapper.selectList(null);
    }

    @Override
    public QiniuContent selectById(long id) {
        return qiniuContentMapper.selectOne(new QueryWrapper<QiniuContent>().eq(QiniuContent.COL_ID, id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QiniuContent upload(MultipartFile file, QiniuConfig qiniuConfig) throws Exception {

        FileUtil.checkSize(maxSize, file.getSize());
        if(qiniuConfig.getId() == null){
            throw new BadRequestException(400, "请先添加相应配置，再操作");
        }
        Configuration cfg = new Configuration(QiNiuUtil.getRegion(qiniuConfig.getZone()));
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        String upToken = auth.uploadToken(qiniuConfig.getBucket());
        try {
            String key = file.getOriginalFilename();
            assert key != null;
            String name = StrUtil.subPre(key, key.lastIndexOf('.'));
            String type = StrUtil.subAfter(key, '.', true);
            QiniuContent content = qiniuContentMapper.findByKey(name);
            if (content != null && content.getSuffix().equals(type)) {
                key = QiNiuUtil.getKey(key);
            }
            Response response = uploadManager.put(file.getBytes(), key, upToken);
            //解析上传成功的结果

            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);

            //存入数据库
            QiniuContent qiniuContent = new QiniuContent();
            qiniuContent.setSuffix(FileUtil.getExtensionName(putRet.key));
            qiniuContent.setBucket(qiniuConfig.getBucket());
            qiniuContent.setType(qiniuConfig.getType());
            qiniuContent.setName(FileUtil.getFileNameNoEx(putRet.key));
            qiniuContent.setUrl(qiniuConfig.getHost()+"/"+putRet.key);
            qiniuContent.setSize(FileUtil.getSize(Integer.parseInt(file.getSize()+"")));
            qiniuContent.setCreateTime(new Date());
            qiniuContentMapper.insert(qiniuContent);
            return qiniuContent;
        }catch (Exception e) {
            throw new BadRequestException(500, e.getMessage());
        }
    }

    @Override
    public QiniuConfig find() {
        return qiniuConfigMapper.selectById(1L);
    }

    @Override
    public void updateConfig(QiniuConfig qiniuConfig) {
        qiniuConfigMapper.updateById(qiniuConfig);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void synchronize(QiniuConfig config) {
        if(config.getId() == null){
            throw new BadRequestException(500, "请先添加相应配置，再操作");
        }
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(QiNiuUtil.getRegion(config.getZone()));
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //文件名前缀
        String prefix = "";
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";
        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(config.getBucket(), prefix, limit, delimiter);

        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            QiniuContent qiniuContent;
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
                QueryWrapper<QiniuContent> contentQueryWrapper = new QueryWrapper<>();
                contentQueryWrapper.eq(QiniuContent.COL_NAME, FileUtil.getFileNameNoEx(item.key));
                contentQueryWrapper.eq(QiniuContent.COL_SUFFIX, FileUtil.getExtensionName(item.key));
                QiniuContent content = qiniuContentMapper.selectOne(contentQueryWrapper);
                if(content == null) {
                    qiniuContent = new QiniuContent();
                    qiniuContent.setSize(FileUtil.getSize(Integer.parseInt(item.fsize+"")));
                    qiniuContent.setSuffix(FileUtil.getExtensionName(item.key));
                    qiniuContent.setName(FileUtil.getFileNameNoEx(item.key));
                    qiniuContent.setType(config.getType());
                    qiniuContent.setBucket(config.getBucket());
                    qiniuContent.setUrl(config.getHost()+"/"+item.key);
                    qiniuContent.setUpdateTime(new Date());
                    qiniuContentMapper.insert(qiniuContent);
                }
            }
        }
    }

    @Override
    public void delete(QiniuContent content, QiniuConfig config) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(QiNiuUtil.getRegion(config.getZone()));
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(content.getBucket(), content.getName() + "." + content.getSuffix());
            qiniuContentMapper.deleteById(content.getId());
        } catch (QiniuException ex) {
            qiniuContentMapper.deleteById(content.getId());
        }
    }

}
