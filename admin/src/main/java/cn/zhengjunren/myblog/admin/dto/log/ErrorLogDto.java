package cn.zhengjunren.myblog.admin.dto.log;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.util.Date;

/**
 * <p>ClassName: ErrorLogDto</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/23 15:39
 */
@Data
public class ErrorLogDto {
    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("请求方法")
    private String method;

    @ExcelProperty("异常信息")
    private String exceptionDetail;

    @ExcelProperty("参数")
    private String params;

    @ExcelProperty("ip")
    private String requestIp;

    @ExcelProperty("耗时")
    private Long time;

    @ExcelProperty("操作者")
    private String username;

    @ExcelProperty("IP来源")
    private String address;

    @ExcelProperty("浏览器")
    private String browser;

    @ExcelProperty("操作时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
