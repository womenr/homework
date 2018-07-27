package wj.csv.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//用来加载配置文件的类。提供加载配置文件内容的方法
public class PropertiesUtil {
    private static final String properiesName = "config.properties";  
    private static Properties prop = new Properties();
    static {  
        InputStream is = null;  
        try {  
        	is = PropertiesUtil.class.getClassLoader().getResourceAsStream(properiesName);  
            prop.load(is);  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
            	is.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    public static String getPropery(String key) {  
        return prop.getProperty(key);  
    }  
  
} 

