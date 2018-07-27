package wj.csv.mapper;

import java.util.List;

public interface BaseMapper {
	<T> T findByPrimaryKey(T primaryKey);
	<T> List<T> selectByExample(T example);
    <T> int deleteByPrimaryKey(T primaryKey);
    <T> int insert(T bean);
    List<String> showTables();
}
