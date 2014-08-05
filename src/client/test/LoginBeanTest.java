package client.test;

import client.bean.LoginBean;
import client.exception.GetMACFailException;
import client.util.PropertyRW;

public class LoginBeanTest {

	public static void main(String[] args) {
		String username = PropertyRW.getInstance()
			.getProperty("username");
		String password = PropertyRW.getInstance()
		.getProperty("password");
		String mac = "60:eb:69:df:60:a8";
        String ip = "172.16.100.1";
		LoginBean bean = null;
		try {
			bean = new LoginBean(username, password, mac, ip);
		} catch (GetMACFailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(bean.toEntityString());
	}
}
