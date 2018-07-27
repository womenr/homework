package utils;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import wj.csv.mapper.UserMapper;

public class MapperTest {

	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void test1() {
		List<String> tableList = userMapper.showTables();
		for (String table : tableList) {
			System.out.println(table);
		}
	}
}
