package wj.csv.pojo;

import java.util.HashMap;
import java.util.Map;

public class Msg {
    private int code;    //返回100表示成功,200表示失败
    private String msg;    //返回提示信息
    private Map<String,Object> extend = new HashMap<String,Object>();    //用户返回给浏览器的数据

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Map<String, Object> getExtend() {
		return extend;
	}
	public void setExtend(Map<String, Object> extend) {
		this.extend = extend;
	}
    
    public static Msg success() {
	    Msg result = new Msg();
	    result.setCode(100);
	    result.setMsg("处理成功");
	    return result;
    }
	public static Msg fail() {
	    Msg result = new Msg();
	    result.setCode(200);
	    result.setMsg("处理失败");
	    return result;
	}
	
	public Msg add(String key,Object value) {
	    this.getExtend().put(key, value);
	    return this;
	}

	
}
