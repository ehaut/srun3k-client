package client.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;

import client.bean.LoginBean;
import client.bean.LogoutBean;
import client.core.HttpOperator;
import client.core.UDPOperator;
import client.exception.GetMACFailException;
import client.exception.OperatorCloseException;
import client.util.Global;
import client.util.MACGetter;
import client.util.PropertyRW;
import client.util.URIMaker;

public class Action {
	private static HttpOperator ho;

	public static String login(String username, String password) throws GetMACFailException {
		URI uri = URIMaker.makeURI(Global.LOGINPATH);

		ho = new HttpOperator(uri);
		LoginBean bean = new LoginBean(username, password);
		ho.setEntity(bean.toEntityString());
		return ho.post();
	}

	public static String logout(String username) throws GetMACFailException {
		URI uri = URIMaker.makeURI(Global.LOGOUTPATH);
		LogoutBean bean = new LogoutBean(username);

		ho = new HttpOperator(uri);
		ho.setEntity(bean.toEntityString());
		return ho.post();
	}

	public static void keepAlive(String username) throws GetMACFailException {
		PropertyRW pr = PropertyRW.getInstance();
		String a = pr.getProperty(Global.HOST);
		int p = Integer.parseInt(pr.getProperty(Global.UDPPORT));
		String m = MACGetter.getLocalMac();
		UDPOperator.send(a, p, username, m);
		System.out.println("keep alive");
	}

	public static String getMSG(String username) {
		URI uri;
		try {
			uri = new URIBuilder(URIMaker.makeURI(Global.GETMSGPATH))
					.setParameter("user_login_name", username).build();
		} catch (URISyntaxException ex) {
			throw new RuntimeException();
		}
		System.out.println(uri);

		ho = new HttpOperator(uri);
		return ho.post();
	}

	public static String readUserInfo() {
		URI uri = URIMaker.makeURI(Global.READUSERINFO);

		ho = new HttpOperator(uri);
		return ho.post();
	}

	public static void interruptOperator() throws OperatorCloseException {
		if (ho != null) {
			ho.interrupt();
		}
	}

}
