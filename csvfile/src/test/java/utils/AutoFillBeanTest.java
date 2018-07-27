package utils;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


import org.junit.Before;
import org.junit.Test;

import wj.csv.pojo.Account;
import wj.csv.pojo.Items;
import wj.csv.pojo.User;
import wj.csv.utils.AutoFillBean;

public class AutoFillBeanTest {

	@Before
	public void setUp() throws Exception {
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public <T> void testFillBean() {
        //Class<User> clazz = User.class;
		 Class<Account> clazz = Account.class;
		// Class<Items> clazz = Items.class;
    	List<T> beanList = null;
    	T bean = null;
        
        try {
            FileInputStream file = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\Account.csv")); 
            //获取指定的类中的所有属性
            Field[] fields = clazz.getDeclaredFields();
            //从文件中获取属性值的map
            Map<Integer, List<String>> fieldMap = AutoFillBean.readFile(file);
            beanList = new ArrayList<T>();
            Set<Integer> keySet = fieldMap.keySet(); //得到所有key的集合
            for (Integer lineNumber : keySet) { //遍历map，获取每一行的属性值的list
            	List<String> fieldList = fieldMap.get(lineNumber);
            	//new一个bean
            	bean = (T) clazz.newInstance();
            	for(int i = 0; i < fields.length; i++) {
            		fields[i].setAccessible(true); 
                    String fieldName = fields[i].getName();
                    //获取当前属性的类型
                    Class type = (Class) fields[i].getGenericType();
                    
                    //获取指定类中的相应属性的set方法，然后把获取到的值封装到对应的属性里面(指定属性名和属性类型)
                    Method method = clazz.getDeclaredMethod("set" 
                            + fieldName.substring(0, 1).toUpperCase()
                            + fieldName.substring(1), type);
                    
                    //判断属性类型是Integer，还是string，还是BigDecimal,然后把从文件中获取的String类型数据进行相应的类型转换
                    if (type.toString().endsWith("Integer")) {
                    	Integer value = Integer.parseInt(fieldList.get(i));
                        //调用set方法把值存入类中
                        method.invoke(bean, value);
                    } else if (type.toString().endsWith("String")) {
                    	String value = fieldList.get(i);
                        //调用set方法把值存入类中
                        method.invoke(bean, value);
                    } else if (type.toString().endsWith("BigDecimal")) {
                    	BigDecimal value = BigDecimal.valueOf(Long.valueOf(fieldList.get(i)));
                        //调用set方法把值存入类中
                        method.invoke(bean, value);
                    }
            	}
              //在bean的所有属性赋值完毕后，将这个bean加到beanList里面去，然后进行下一个bean的属性赋值
                beanList.add(bean);
            }
            List<Account> accountList = (List<Account>) beanList;
            for (Account account : accountList) {
            	System.out.println(account);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
	}

	@Test
	public void testReadFile() {
		//用bufferedReader来解析文件，将一行一行的内容输入到list里面然后传给AutoFillBean这个方法
    	BufferedReader bReader = null;
		List<String> fieldList = null;
		//用map来装属性值，key是读取的属性值的行数（从0开始）value是装着对应行数属性值的list
		Map<Integer, List<String>> fieldMap = null;
		//读取的属性值的行数，从0开始计数
		int lineNumber = 0;
		
		try {
			FileInputStream file = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\User.csv")); 
			bReader = new BufferedReader(new InputStreamReader(file,"utf-8"));
			
			//CSVファイルの2行目からデータをDBへ格納する．ファイルの1行目が必要の時、このコードを削除してください
			//从第二行开始读
			bReader.readLine();
			
			String line = null;
			//用map来装，并且为了保证存储的顺序不乱，选用linkedHashMap
			fieldMap = new LinkedHashMap<Integer, List<String>>();
			fieldList = null;
		
			//一行ずつデータを読み込んで、リストに格納する
			while ((line = bReader.readLine()) != null) {
				fieldList =  new ArrayList<String>();
				if (line.trim() != "") {
					String fields[] = line.trim().split(",");
					for(int i = 0; i < fields.length; i++) {
						fieldList.add(fields[i]);
					}
					fieldMap.put(lineNumber++, fieldList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				bReader.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
    }
	
	@Test
	public void getClassPath() {
		Properties prop = new Properties();
		try {
			prop.load(ClassLoader.getSystemResourceAsStream("projectInfo.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(prop.getProperty("classPath"));
	}
}
