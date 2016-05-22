
package client.bean;

import java.util.Vector;

import client.exception.GetMACFailException;
import client.util.PropertyRW;

public class LoginBean {
	private String action;
	private String username;
	private String password;
	private String drop;
	private String pop;
	private String type;
	private String n;
	private String ip;
	private String mbytes;
	private String minutes;
	private String ac_id;
	private String mac;

	private String cguid;
	private Vector<String> keys;
	
	public LoginBean(String _username, String _password, String mac, String ip)
			throws GetMACFailException{
		setAction("login");
		setUsername(_username);
		setPassword(_password);
		setDrop("0");
		setPop("1");
		setType("2");
		setN("117");
		// setIp(ip);
		setMbytes("0");
		setMinutes("0");
		setAc_id("2");
		setMac(mac);
		// setCguid("");
		// setKey();
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setDrop(String drop) {
		this.drop = drop;
	}
	public void setPop(String pop) {
		this.pop = pop;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setN(String n) {
		this.n = n;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setMbytes(String mbytes) {
		this.mbytes = mbytes;
	}
	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}
	public void setAc_id(String ac_id) {
		this.ac_id = ac_id;
	}
	public void setMac(String mac) throws GetMACFailException {
		this.mac = mac;
	}
	public void setCguid(String cguid) {
		this.cguid = cguid;
	}
	public void setKey() {
		keys = new Vector<>();
		String[] temp = PropertyRW.getInstance()
			.getProperty("key").split(",");
		for (String t : temp){
			keys.add(t);
		}
	}
	public String getNewKey(){

		String key = keys.firstElement();
		keys.remove(0);
		return key;
	}
	public String toEntityString(){
		return toString();
	}

	@Override
	public String toString() {
		String temp = "";
		temp += "action=" 	+ action 	+ "&";
		temp += "username=" + username	+ "&";
		temp += "password=" + password	+ "&";
		temp += "drop=" 	+ drop		+ "&";
		temp += "pop=" 		+ pop		+ "&";
		temp += "type=" 	+ type		+ "&";
		temp += "n=" 		+ n			+ "&";
		// temp += "ip=" 		+ ip		+ "&";
		temp += "mbytes=" 	+ mbytes	+ "&";
		temp += "minutes=" 	+ minutes	+ "&";
		temp += "ac_id="	+ ac_id		+ "&";
		temp += "mac=" 		+ mac		+ "&";
		// temp += "cguid=" 	+ cguid		+ "&";
		// temp += "key=" 		+ getNewKey();
		return temp;
	}
}