
package client.bean;

import client.exception.GetMACFailException;

public class LogoutBean {

    private String action;
    private String ac_id;
    private String username;
    private String mac;
    private String type;
    

    public LogoutBean(String username, String mac) throws GetMACFailException {
        setAction("logout");
        setAc_id("2");
        setUsername(username);
        setMac(mac);
        setType("2");
    }

    public void setAction(String action) {
        this.action = action;
    }
    public void setAc_id(String ac_id) {
        this.ac_id = ac_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setMac(String mac) throws GetMACFailException {
        this.mac = mac;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String toEntityString() {
        return toString();
    }

    @Override
    public String toString() {
        String temp = "";
        temp += "action=" + action + "&";
        temp += "ac_id=" + ac_id + "&";
        temp += "username=" + username + "&";
        temp += "mac=" + mac + "&";
        temp += "type=" + type;
        return temp;
    }

}
