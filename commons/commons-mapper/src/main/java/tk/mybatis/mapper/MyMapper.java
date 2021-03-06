package tk.mybatis.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * <p>ClassName: MyMapper</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/18 10:23
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
