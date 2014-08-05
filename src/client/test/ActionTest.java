package client.test;

import client.exception.GetMACFailException;
import client.service.Action;
import client.util.PropertyRW;

public class ActionTest {

    public static void main(String[] args) {
        readUserInfo();
    }

    public static void login() throws GetMACFailException {
        String username = PropertyRW.getInstance()
                .getProperty("username");
        String password = PropertyRW.getInstance()
                .getProperty("password");
        String mac = "60:eb:69:df:60:a8";
        String ip = "172.16.100.1";
        String res = Action.login(username, password, mac, ip);
        System.out.println(res);
    }

    public static void logout() {
        String username = PropertyRW.getInstance().getProperty("username");
        String res = "";
        String mac = "60:eb:69:df:60:a8";

		try {
			res = Action.logout(username, mac);
		} catch (GetMACFailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(res);
    }
    public static void getMSG(){
        String username = PropertyRW.getInstance().getProperty("username");
        String res = Action.getMSG(username);
        System.out.println(res);
    }
    public static void readUserInfo(){
        String res = Action.readUserInfo();
        System.out.println(res);
    }
}
