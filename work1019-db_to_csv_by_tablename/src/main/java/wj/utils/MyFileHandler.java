package wj.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MyFileHandler {
	/*
	 * 需求：给一个装有表名的list，从数据库中找到指定的表并输出到相应的csv文件中，文件名为表名
	 */
	
	//这个方法返回指定表格的所有数据，包括这个表的表头,如果数据库是postgresql的话sql指令如下：
	/*  需要对pg的系统表知识有一定的掌握  下面的pg_attribute表是保存了数据库中所有attribute（字段，所有的表都在这）的信息。
	 * 但是因为pg_attribute表中只有字段所属的表的id而没有name，所以要和pg_class表关联查询。
	 * 
	 *  SELECT a.attname（attname就是字段名，也就是我们需要的表的表头） AS name
		FROM pg_class AS c, pg_attribute AS a 
		WHERE c.relname = '表名' AND a.attrelid = c.oid (这里的attrelid指的就是这个字段所属的表的id) AND a.attnum>0 （a.attnum表示的是该表的字段数量，也就是表头列数）
	 *  oid代表着object identifier (对象标识号)，是内部表作为标识使用的，每个系统表的每一行数据oid都是唯一的，class表的oid就表示某个table的id，
	 *  所以通过c.relname = '表名' AND a.attrelid = c.oid锁定所需要的表（以防不同的schema有同名的表存在）
	 *  
	 */

	
	public static void writeCsvByList(String directory, String fileName, Charset charset, List<String> dataList ) {
		/*
		 * jdk8的新方法，用来写入文件，读取文件也有更简单的方法，直接用Files.readAllLines等方法
		 * Get the file reference  
			Path path = Paths.get("c:/output.txt");  
   
			//Use try-with-resource to get auto-closeable writer instance  
			try (BufferedWriter writer = Files.newBufferedWriter(path))   
			{  
			    writer.write("Hello World !!");  
			}  
		 * 
		 */
		Path path = Paths.get(directory, fileName);
		try {
			//如果文件已存在，创建失败
			Files.write(path, dataList, charset, StandardOpenOption.CREATE_NEW);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
/*	public static void pathFilterAndDelete(String filePath, String regex) {
		 参考资料：https://blog.csdn.net/qasrc6/article/details/51282185
		 * 使用Files.walkFileTree的方法遍历指定文件下下面的所有文件，方法参数一个是Path，一个是FileVisitor（接口 相当于文件访问器）
		 * 这里使用SimpleFileVisitor内部匿名类的方式实现接口
		 
		Path path = Paths.get(filePath); 
		Pattern pattern = Pattern.compile(regex); 
		try {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				
				//访问文件时触发该方法  override该方法  返回FileVisitResult（包含4个访问目录时的操作）
			        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			        	File target = file.toFile();
			        	Matcher matcher = pattern.matcher(target.getName());  
			            if (matcher.matches()) {
			            	target.delete();
			            	return FileVisitResult.CONTINUE;
			            }
			            return FileVisitResult.CONTINUE;
			        }
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
/*	public static List<String> beansToString(List<Object> objectList, String objectType, List<String> fields) {
		try {
			Class<?> clazz = Class.forName(objectType);
			//获取这个pojo的所有属性
			
			for (Object object: objectList) {
				
			}
			List<String> dataList = new ArrayList<String>();
			return dataList;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
/*	public static String getMethodName(String field) {
		StringBuffer sb = new StringBuffer(field);
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		sb.insert(0, "get");
		return sb.toString();
	}
	
	public static String[] getMethodNames(String[] fields){
		String[] methodNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			String methodName = getMethodName(fields[i]);
			methodNames[i] = methodName;
		}
		return methodNames;
	}*/
	
	//获取一个object的所有指定属性值，并以list形式输出
/*	public static Object getValues(Object object, String[] fields){
		String[] values = new String[fields.length];
		Class<? extends Object> clazz = object.getClass();
		String[] methodNames = getMethodNames(fields);
		for (int i = 0; i < methodNames.length; i++) {
			Object bean = null;
			try {
				//通过方法名获取方法
				Method method = clazz.getMethod(methodNames[i]);
				method.setAccessible(true);
				bean = method.invoke(object);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

			return bean;
		}
		
		return null;
	}*/
	
/*	public static List<Path> pathFilterAndCollect(String filePath, String regex) { 
		
		 * 用jdk8.0的新方法，直接过滤指定文件夹下面的所有子文件夹，并collect起来一list的形式返回
		 
		Path path = Paths.get(filePath); 
		Pattern pattern = Pattern.compile(regex); 
		List<Path> paths;
		try {
			paths = Files.walk(path).filter(p -> { //这里的p是在works这个目录下面的所有文件路径的其中一个（自带遍历效果）
				//lambda表达式，filter（p -> 后面是判断语句，true则选中并在后面collect起来，false则过滤掉
				 //如果不是普通的文件，则过滤掉    
				 if(!Files.isRegularFile(p)) { 
				  return false; 
				 } 
				 File file = p.toFile(); 
				 //将过滤出来的路径转成File，然后用pattern来match文件名，
				 Matcher matcher = pattern.matcher(file.getName()); 
				 return matcher.matches(); 
				}).collect(Collectors.toList());
			return paths;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}*/
	

}