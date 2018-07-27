package wj.csv.pojo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable{ //为了在页面可以通过session来提取该对象的数据信息，必须使用序列化的对象
	
    private Integer uid;

    private String username;

    private String password;

    private String email;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", password=" + password + ", email=" + email + "]";
	}
}