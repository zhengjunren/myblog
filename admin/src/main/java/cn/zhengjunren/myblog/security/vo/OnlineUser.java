package cn.zhengjunren.myblog.security.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>ClassName: OnlineUser</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/27 14:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineUser implements Serializable {

    @ExcelIgnore
    private static final long serialVersionUID = -2323686001153682026L;

    @ExcelProperty("用户名")
    private String username;

    @ExcelProperty("昵称")
    private String nickname;

    @ExcelProperty("浏览器")
    private String browser;

    @ExcelProperty("IP")
    private String ip;

    @ExcelProperty("IP来源")
    private String address;

    @ExcelIgnore
    private String key;

    @ExcelProperty("登录时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date loginTime;


}

