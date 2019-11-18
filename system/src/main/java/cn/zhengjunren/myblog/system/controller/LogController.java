package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.commons.dto.ResponseResult;
import cn.zhengjunren.myblog.system.domain.TbLog;
import cn.zhengjunren.myblog.system.dto.LogListInfo;
import cn.zhengjunren.myblog.system.service.TbLogSystemService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>ClassName: LogController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/18 20:26
 */
@RestController
@RequestMapping("log")
@CrossOrigin(origins = "*", maxAge = 3600L)
public class LogController {

    @Autowired
    TbLogSystemService tbLogSystemService;

    @GetMapping("list")
    public ResponseResult<LogListInfo> page(Integer page, Integer limit) {
        PageInfo<TbLog> pageInfo = tbLogSystemService.page(page, limit);
        LogListInfo logListInfo = new LogListInfo();
        logListInfo.setItems(pageInfo.getList());
        logListInfo.setTotal(pageInfo.getTotal());
        return new ResponseResult<>(ResponseResult.CodeStatus.OK,"分页获取用户列表", logListInfo);
    }
}
