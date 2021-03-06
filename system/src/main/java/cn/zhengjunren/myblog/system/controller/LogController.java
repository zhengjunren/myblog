package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.commons.dto.ListInfo;
import cn.zhengjunren.myblog.commons.dto.ResponseResult;
import cn.zhengjunren.myblog.commons.log.domain.TbLog;
import cn.zhengjunren.myblog.commons.utils.DataTypeUtils;
import cn.zhengjunren.myblog.commons.utils.ParamTypeUtils;
import cn.zhengjunren.myblog.system.service.TbLogSystemService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

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
@Api(tags = "日志管理")
public class LogController {

    private final TbLogSystemService tbLogSystemService;

    public LogController(TbLogSystemService tbLogSystemService) {
        this.tbLogSystemService = tbLogSystemService;
    }

    @GetMapping("list")
    @ApiOperation(value = "获取日志列表", notes="根据页码、笔数查询日志列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = DataTypeUtils.INT, paramType = ParamTypeUtils.QUERY),
            @ApiImplicitParam(name = "limit", value = "笔数", required = true, dataType = DataTypeUtils.INT, paramType = ParamTypeUtils.QUERY),
    })
    public ResponseResult<ListInfo<TbLog>> page(int page, int limit, Timestamp start, Timestamp end) {
        PageInfo<TbLog> pageInfo = tbLogSystemService.page(page, limit, start, end);
        ListInfo<TbLog> listInfo = new ListInfo<>(pageInfo.getList(), pageInfo.getTotal());
        return new ResponseResult<>(ResponseResult.CodeStatus.OK,"分页获取用户列表", listInfo);
    }

    @GetMapping("count")
    @ApiOperation(value = "获取操作日志次数")
    public ResponseResult<Integer> count() {
        Integer count = tbLogSystemService.count();
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取操作日志次数", count);
    }
}
