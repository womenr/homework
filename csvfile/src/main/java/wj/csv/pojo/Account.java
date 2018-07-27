package wj.csv.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class Account implements Serializable{ //为了在页面可以通过session来提取该对象的数据信息，必须使用序列化的对象
	
    private String account;

    private Integer password;

    private BigDecimal balance;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

	@Override
	public String toString() {
		return "Account [account=" + account + ", password=" + password + ", balance=" + balance + "]";
	}
}