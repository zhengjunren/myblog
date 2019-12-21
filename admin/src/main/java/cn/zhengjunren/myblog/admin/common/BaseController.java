package cn.zhengjunren.myblog.admin.common;

import cn.zhengjunren.myblog.admin.dto.ListInfo;
import cn.zhengjunren.myblog.common.exception.BadRequestException;
import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.common.staus.Status;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ApiResponse create(@RequestBody T entity) {
        if (entity.getId() != null) {
            throw new BadRequestException(Status.ENTITY_CANNOT_HAVE_AN_ID);
        }
        service.save(entity);
        return ApiResponse.ofSuccess();
    }

    @GetMapping
    public ApiResponse page(long page, long limit) {
        IPage<T> iPage = service.page(new Page<>(page, limit));
        return ApiResponse.ofSuccess(new ListInfo(iPage.getRecords(), iPage.getTotal()));
    }

}
