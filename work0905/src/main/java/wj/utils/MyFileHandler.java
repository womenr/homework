package wj.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import wj.pojo.AgentBasicInfo;

public class MyFileHandler {
	
	public static void writeCsvByList(String directory, String fileName, List<AgentBasicInfo> dataList ) {
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
		List<String> lines = new ArrayList<String>();
		for(AgentBasicInfo abi : dataList) {
			lines.add(abi.toCsvString());
		}
		
		try {
			Files.write(path, lines, StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
	
	public static void pathFilterAndDelete(String filePath, String regex) { // F:/works  regex = "^agent_\\d+\\.csv"
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
			  
			//将过滤出来的指定文件删除
			MyFileHandler.deleteFileList(paths);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static void deleteFileList(List<Path> paths) {
		for (Path path : paths) {
			File file = path.toFile();
			file.delete();
		}
	}
	
}
