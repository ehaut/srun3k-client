package client.service;

import java.net.NetworkInterface;

import client.exception.GetIPFailException;
import client.exception.GetMACFailException;
import client.exception.OperatorCloseException;
import client.util.Global;
import client.util.InterInfoGetter;
import client.view.MainWindow;

public class Server extends Thread {
	private boolean run;
	private int clientState;
	private String mac;
	private String ip;
	private String username;
	private String password;
	private String MSG;
	private String userInfo;
	private MainWindow window;

	public void setInter(NetworkInterface inter) {
		try {
			mac = InterInfoGetter.getLocalMac(inter);
		} catch (GetMACFailException e) {
			sendMSG(Global.tipMSG, "获取网卡MAC失败");
		}
		try {
			ip = InterInfoGetter.getLocalIP(inter);
		} catch (GetIPFailException e) {
			sendMSG(Global.tipMSG, "获取网卡IP失败");
		}
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public boolean getRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}
	
	public int getClientState() {
		return clientState;
	}

	public void setClientState(int clientState) {
		this.clientState = clientState;
	}

	
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

	public Server(MainWindow window) {
		this.window = window;
		run = false;
		clientState = Global.offline;
	}
	private void dealMSG() {
		// 登录后的消息
		System.out.println("MSG:" + MSG);
		if (clientState == Global.login) {
			if (MSG.contains("login_ok")) {
				clientState = Global.online;
				sendMSG(Global.loginokMSG, "登录成功");
			} else if (MSG.contains("username_error")) {
				run = false;
				clientState = Global.offline;
				sendMSG(Global.loginfailMSG, "用户名不存在");
			} else if (MSG.contains("password_error")) {
				run = false;
				clientState = Global.offline;
				sendMSG(Global.loginfailMSG, "密码错误");
			} else if (MSG.contains("MAC")) {
				run = false;
				clientState = Global.offline;
				sendMSG(Global.loginfailMSG, "MAC地址错误");
			} else if (MSG.contains("online_num_error")){
				run = false;
				clientState = Global.offline;
				sendMSG(Global.loginfailMSG, "已注销在线用户，请稍后重试登录");
			}else {
				run = false;
				clientState = Global.offline;
				sendMSG(Global.loginfailMSG, "未知错误");
			}
			return;
		}
		if (clientState == Global.online) {
			MSG = MSG.replaceAll("&nbsp;", "");
			sendMSG(Global.systemMSG, MSG);
			sendMSG(Global.userinfoMSG, userInfo);
			return;
		}
		if (clientState == Global.logout) {
			if (MSG.contains("logout_ok")) {
				clientState = Global.offline;
				sendMSG(Global.logoutokMSG, "下线成功");
			} else {
				sendMSG(Global.logoutokMSG, "已经离线");
			}
			return;
		}
	}

	public void doLogou() {
		System.out.println("logout");
		clientState = Global.logout;
		if (run == false){
			try {
				MSG = Action.logout(username, mac);
			} catch (GetMACFailException e) {
				sendMSG(Global.tipMSG, "MAC获取失败，请检查是否插好网线");
			}
			dealMSG();
		}else{
			run = false;
			this.interrupt();
		}
			
	}

	public void cancel() {
		if (clientState == Global.login || clientState == Global.logout) {
			try {
				System.out.println("to inter");
				Action.interruptOperator();
			} catch (OperatorCloseException e) {
				sendMSG(Global.cancelokMSG, "已取消");
			}
		}
	}

	private void sendMSG(int type, String msg) {
		System.out.println(msg);
		window.updateMSG(type, msg);
	}

	@Override
	public void run() {
		while (run) {
			// 登录
			if (clientState == Global.login) {
				try {
					MSG = Action.login(username, password, mac, ip);
				} catch (GetMACFailException e) {
					sendMSG(Global.tipMSG, "MAC获取失败，请检查是否插好网线");
				} catch (Exception e){
					sendMSG(Global.tipMSG, "认证服务器无响应");
				}
				dealMSG();
			}
			// 登录过后
			if (clientState == Global.online) {
				if (userInfo == null) {
					MSG = Action.getMSG(username);
					userInfo = Action.readUserInfo();
					dealMSG();
				}
				try {
					sleep(60 * 1000);
					//sleep(2000);
				} catch (InterruptedException e) {
					// 收到终止异常，唤醒线程。异常来自注销方法
					System.out.println("interrupt");
					if (clientState == Global.logout) {
						try {
							MSG = Action.logout(username, mac);
						} catch (GetMACFailException e1) {
							sendMSG(Global.tipMSG, "MAC获取失败，请检查是否插好网线");
						}
						dealMSG();
					}
				}
				try {
					Action.keepAlive(username, mac);
				} catch (GetMACFailException e) {
					sendMSG(Global.tipMSG, "MAC获取失败，请检查是否插好网线");
				}
			}
		}
	}
}
