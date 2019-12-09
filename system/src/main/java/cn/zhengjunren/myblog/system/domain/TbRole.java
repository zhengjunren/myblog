package cn.zhengjunren.myblog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ZhengJunren
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_role")
public class TbRole implements Serializable {
    private static final long serialVersionUID = 760436251174120407L;
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 父角色
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 角色名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 角色英文名称
     */
    @Column(name = "enname")
    private String enname;

    /**
     * 备注
     */
    @Column(name = "description")
    private String description;

    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "created")
    private Date created;

    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated")
    private Date updated;
}
