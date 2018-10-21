package wj.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TableInfoMapper {
	//所有方法都验证完毕，一次性输出所有表头的时候按照table名的输入顺序以及各表字段的顺序依次输出
	
	//一次性通过table名的list获取table的所有表头数据
	List<String> getAllHeadersByTableNames(List<String> tableNames);
	
	//一次仅获取一个table的表头数据
	List<String> getHeaderByTableName(String tableName);
	
	//获取table的所有数据（无表头） 因为使用表名作为参数且sql语句是from 表名参数  的时候，要注意，xml文件中用${}而不是#{} 
	//（其他场合特别是传入的是where条件的参数时要用#{}防注入），二者区别在于预编译的时候#{}的内容会变成？${}原原本本输入。
	//所以传入参数为表名时要如下处理，否则sql预编译会失败。 用@Param("xxx")指定在xml里面的形参
	//因为并没有指定查询出的结果存放在哪个实体类，所以这时候的resultType是map
	List<Map<String, Object>> getDataByTableName(@Param("tableName") String tableName);
	
	//获取table的数据类型
	List<String> getDataTypeByTableName(@Param("tableName") String tableName);
}
