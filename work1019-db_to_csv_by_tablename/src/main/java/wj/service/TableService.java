package wj.service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import wj.mapper.TableInfoMapper;
import wj.utils.MyFileHandler;


@Service
public class TableService {

	@Autowired
	private TableInfoMapper tableInfoMapper;
	
	@Value("${directory}")
	private String directory;
	
	@Value("${charset}")
	private String charset;
	
	//通过表名list获取数据库指定表的数据并保存到相应的csv文件中
	public void toCsvByTableNames(List<String> tableNames) {
		
		for (String tableName : tableNames) {
			List<Map<String, Object>> dataMapList = tableInfoMapper.getDataByTableName(tableName);
			
			//通过数据库获取数据类型
			List<String> dataTypes = tableInfoMapper.getDataTypeByTableName(tableName);
			
			//通过数据库获取表头
			List<String> header = tableInfoMapper.getHeaderByTableName(tableName);
			
			StringBuffer sb = new StringBuffer();
			//表头导出时默认全是文本类型，所以一律加“”
			for (String head : header) {
				sb.append("\"" + head + "\"" + ",");
			}
			//删掉最后一个逗号
			sb.deleteCharAt(sb.length() - 1);
			List<String> dataList = new ArrayList<String>();
			dataList.add(sb.toString());
			sb.setLength(0);
			for (Map<String, Object> dataMap : dataMapList) { 
				for (String key : dataMap.keySet()) {
					sb.append("\"" + dataMap.get(key) + "\"");
					sb.append(",");
				}
				sb.deleteCharAt(sb.length() - 1);
				dataList.add(sb.toString());
				sb.setLength(0);
			}
			
			Charset encoding = Charset.forName(charset);
			MyFileHandler.writeCsvByList(directory, tableName + ".csv", encoding, dataList);
		}
		
	}
}
