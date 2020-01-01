package cn.zhengjunren.myblog.system.service;

import cn.zhengjunren.myblog.common.dto.ListInfo;
import cn.zhengjunren.myblog.system.domain.QiniuConfig;
import cn.zhengjunren.myblog.system.domain.QiniuContent;
import cn.zhengjunren.myblog.system.dto.condition.QiniuQueryCondition;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>ClassName: QiNiuService</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/30 14:36
 */
public interface QiNiuService {

    /**
     * 分页查询
     * @param condition 条件
     * @return 通用结果
     */
    ListInfo page(QiniuQueryCondition condition);

    /**
     * 查询所有
     * @return /
     */
    List<QiniuContent> selectAll();

    /**
     * 根据id查询
     * @return 结果
     */
    QiniuContent selectById(long id);


    /**
     * 上传文件
     * @param file 文件
     * @param qiniuConfig 配置
     * @return QiniuContent
     */
    QiniuContent upload(MultipartFile file, QiniuConfig qiniuConfig) throws Exception;

    /**
     * 查配置
     * @return QiniuConfig
     */
    QiniuConfig find();

    /**
     * 同步数据
     * @param config 配置
     */
    void synchronize(QiniuConfig config);

    /**
     * 删除文件
     * @param content 文件
     * @param config 配置
     */
    void delete(QiniuContent content, QiniuConfig config);
}
