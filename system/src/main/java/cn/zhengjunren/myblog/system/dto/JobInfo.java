package cn.zhengjunren.myblog.system.dto;

import cn.zhengjunren.myblog.system.domain.JobAndTrigger;
import lombok.Data;

import java.util.List;

/**
 * <p>ClassName: JobInfo</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/16 23:53
 */
@Data
public class JobInfo {

    List<JobAndTrigger> item;
    long total;
}
