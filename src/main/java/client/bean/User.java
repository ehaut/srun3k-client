package client.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
201116040117
1406795031 上线时间
7255040
2336768
34413193216	流量
707771
172.16.24.116
34608550
20.00
*/

public class User {
	private String username;
	private String password;
	private Date loginTime;
	private float flow;
	private String ip;
	private double money;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public float getFlow() {
		return flow;
	}
	public void setFlow(float flow) {
		this.flow = flow;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	@Override
	public String toString(){
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
		String time = f.format(getLoginTime());
		
		return "<html>用户名: " 	+ getUsername() + "<br>"
				+ "IP: "		+ getIp()		+ "<br>"
				+ "上线时间: "		+ time			+ "<br>"
				+ "流量: "		+ getFlow()		+ "GB<br>"
				+ "金额: "		+ getMoney()	+ "</html>";
	}
	
}
