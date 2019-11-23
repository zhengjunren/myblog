package cn.zhengjunren.myblog.security.domain;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "tb_role")
public class TbRole {
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

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "updated")
    private LocalDateTime updated;
}