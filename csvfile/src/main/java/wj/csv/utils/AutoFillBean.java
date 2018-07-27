package wj.csv.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.junit.Test;


public class AutoFillBean {
    public static <T> List<T> fillBean(InputStream file, String fileName) {
        
    	List<T> beanList = null;
    	T bean = null;
		//调用getClassFromFileName方法，获取该文件对应要储存的类名
		Class<?> clazz;
		try {
			clazz = getClassFromFileName(fileName);

            //获取指定的类中的所有属性
            Field[] fields = clazz.getDeclaredFields();
            //调用readFile方法，从文件中获取属性值的map
            Map<Integer, List<String>> fieldMap = readFile(file);
            
            Set<Integer> keySet = fieldMap.keySet(); //得到所有key的集合
            //用来装bean
            beanList = new ArrayList<T>();
            for (Integer lineNumber : keySet) { //遍历map，获取每一行的属性值的list
            	List<String> fieldList = fieldMap.get(lineNumber);
                //new一个bean用来封装数据
				bean = (T) clazz.newInstance();
				
            	for(int i = 0; i < fields.length; i++) {
                	fields[i].setAccessible(true); 
                    String fieldName = fields[i].getName();
                    //获取当前属性的类型
                    Class type = (Class) fields[i].getGenericType();

/** 这里存在疑问，从文件名中反射对应的类，这一点ok了，但是从文件中获取到的数据全都是String类型的，如果想要调用类的set方法，把数据存到
 *  类里面的话，还要考虑类里面该属性设置的类型是什么，然后把读取出来的string类型的数据转换成相应的类型再存进类里。这个处理也是有方法可以
 *  更简单地反射操作的吗?还是像我下面写的这样通过属性的type来判断类型然后对数据进行类型强转再存进去？ 用判断的话，感觉可扩展性比较差。                     
 */
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
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
  /**
   * 
   * 关于异常的处理:什么时候该抛什么时候该处理，原本这里有6-7个异常，太长了。我嫌麻烦就直接用Exception代替了，正确的处理方式是什么？ 
   */
        return beanList;
    }

    public static Map<Integer, List<String>> readFile(InputStream file) {
    	//用bufferedReader来解析文件，将一行一行的内容输入到list里面然后传给AutoFillBean这个方法
    	BufferedReader bReader = null;
		List<String> fieldList = null;
		//用map来装属性值，key是读取的属性值的行数（从0开始）value是装着对应行数属性值的list
		Map<Integer, List<String>> fieldMap = null;
		//读取的属性值的行数，从0开始计数
		int lineNumber = 0;
		
		try {
			bReader = new BufferedReader(new InputStreamReader(file,"utf-8"));
			
			bReader.readLine();
			
			String line = null;
			//用map来装，并且为了保证存储的顺序不乱，选用linkedHashMap
			fieldMap = new LinkedHashMap<Integer, List<String>>();
			fieldList = null;
		
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
			throw new RuntimeException(e);
		} finally {
			try {
				bReader.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return fieldMap;
    }
    
    //通过配置文件获取pojo类的路径
	public static String getClassPath() throws IOException {
		return PropertiesUtil.getPropery("classPath");
	}
	
    public static Class<?> getClassFromFileName(String fileName) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
    	//文件名的格式指定为：类名_xxxx.csv
    	String pojoPath = getClassPath();
    	String className = pojoPath + fileName.substring(0, fileName.indexOf("_"));
		Class<?> clazz = Class.forName(className);
		return clazz;	//该方法可以通过文件名和指定的pojo路径返回一个该文件名指定的class类
    }
}
