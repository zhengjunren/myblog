package cn.zhengjunren.myblog.log.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * <p>ClassName: InfoLogDto</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/23 15:18
 */
@Data
public class InfoLogDto {

    @TableField(value = "description")
    @ExcelProperty("描述")
    private String description;

    @TableField(value = "method")
    @ExcelProperty("请求方法")
    private String method;

    @TableField(value = "params")
    @ExcelProperty("参数")
    private String params;

    @TableField(value = "request_ip")
    @ExcelProperty("ip")
    private String requestIp;

    @TableField(value = "time")
    @ExcelProperty("耗时")
    private Long time;

    @TableField(value = "username")
    @ExcelProperty("操作者")
    private String username;

    @TableField(value = "address")
    @ExcelProperty("IP来源")
    private String address;

    @TableField(value = "browser")
    @ExcelProperty("浏览器")
    private String browser;

    @TableField(value = "create_time")
    @ExcelProperty("操作时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
