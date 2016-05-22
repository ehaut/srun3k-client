import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import client.service.Server;


public class ServerTest {
	private Server ser;
	public static void main(String[] args) {
		final ServerTest t = new ServerTest();
		JFrame frame = new JFrame();
		JButton loginBut = new JButton("登录");
		JButton logoutBut = new JButton("下线");
		loginBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				t.login();
			}
		});
		logoutBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				t.logout();
				
			}
		});
		
		frame.setSize(400, 300);
		Container c = frame.getContentPane();
		c.setLayout(new FlowLayout());
		c.add(loginBut);
		c.add(logoutBut);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	private void login(){
		if(ser != null && ser.isAlive()){
			System.out.println("已登录");
			return;
		}
		/*ser = new Server();
		ser.setRun(true);
		ser.setClientState(2);
		String username = "201116040117";
		String password = "111111";
		String passKey = PropertyReader.getInstance().getProperty(Global.PASSWORDKEY);
		ser.setUsername(username);
		ser.setPassword(PasswordMaker.encrypt(password, passKey));
		ser.start();*/
	}
	private void logout(){
		ser.doLogou();
	}

}
