package cn.zhengjunren.myblog.admin.common;

import java.util.List;

/**
 * <p>ClassName: MyMapper</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/17 12:59
 */
public interface MyMapper<D, E> {
    /**
     * DTO转Entity
     */
    E toEntity(D dto);

    /**
     * Entity转DTO
     */
    D toDto(E entity);

    /**
     * DTO集合转Entity集合
     */
    List<E> toEntity(List<D> dtoList);

    /**
     * Entity集合转DTO集合
     */
    List <D> toDto(List<E> entityList);
}
