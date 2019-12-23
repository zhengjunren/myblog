package cn.zhengjunren.myblog.common.controller;

import cn.hutool.core.util.StrUtil;
import cn.zhengjunren.myblog.common.annotation.MyLog;
import cn.zhengjunren.myblog.common.domain.BaseDomain;
import cn.zhengjunren.myblog.common.dto.ListInfo;
import cn.zhengjunren.myblog.common.exception.BadRequestException;
import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.common.staus.Status;
import cn.zhengjunren.myblog.common.utils.ParamTypeUtils;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

/**
 * <p>ClassName: BaseController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/20 12:02
 */
@RestController
public abstract class BaseController<T extends BaseDomain, S extends IService<T>> {
    protected final S service;

    public BaseController(S service) {
        this.service = service;
    }

    @GetMapping("all")
    protected ApiResponse get() {
        List<T> list = service.list(null);
        return ApiResponse.ofSuccess(list);
    }

    @PostMapping
    @ApiOperation(value = "创建")
    @ApiImplicitParam(name = "entity", value = "实体", required = true, dataType = "T", paramType = ParamTypeUtils.BODY)
    public ApiResponse create(@RequestBody T entity) {
        if (entity.getId() != null) {
            throw new BadRequestException(Status.ENTITY_CANNOT_HAVE_AN_ID);
        }
        entity.setCreateTime(new Date());
        service.save(entity);
        return ApiResponse.ofSuccess();
    }

    @GetMapping
    @MyLog("分页查询")
    public ApiResponse page(long page, long limit) {
        IPage<T> iPage = service.page(new Page<>(page, limit));
        return ApiResponse.ofSuccess(new ListInfo(iPage.getRecords(), iPage.getTotal()));
    }

    @GetMapping("excel")
    @MyLog("导出excel数据")
    @ApiOperation(value = "导出Excel")
    public <T> void  exportExcel(HttpServletResponse response) throws IOException, ClassNotFoundException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Class<?> aClass = Class.forName(parameterizedType.getActualTypeArguments()[0].getTypeName());
        String[] split = StrUtil.split(parameterizedType.getActualTypeArguments()[0].getTypeName(), ".");
        response.setHeader("Content-disposition", "attachment;filename=" + split[split.length-1] + ".xlsx");
        EasyExcel.write(response.getOutputStream(), aClass).sheet("sheet1").doWrite(service.list());
    }

}
