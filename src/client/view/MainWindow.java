package client.view;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.http.client.utils.URIBuilder;

import client.bean.User;
import client.service.Server;
import client.util.Global;
import client.util.PasswordMaker;
import client.util.PropertyRW;

public class MainWindow extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String FONT = "微软雅黑";
	private final int STYLE = Font.PLAIN;
	private final int SIZE = 17;
	private Server server;
	private User user;
	private String systemInfo;
    
    private JPanel imgPanel;
    private JPanel mainPanel;
    private JPanel logbtPanel;
    private JLabel userLabel;
    private JLabel passLabel;
    private JPasswordField passField;
    private JButton logBut;
    private JLabel logoLab;
    private JCheckBox checkBox;
    private JLabel titleLab;
    private JTextField userField;
    private JLabel msgArea;
    private JLabel linkLabel;
    private JLabel msgLabel;
    private SystemTray tray;
    
    public MainWindow() {
        initComponents();
    }

    private void initComponents() {
    	user = new User();
    	
        imgPanel = new JPanel();
        logbtPanel = new JPanel();
        logoLab = new JLabel();
        titleLab = new JLabel();
        mainPanel = new JPanel();
        userField = new JTextField();
        logBut = new JButton();
        checkBox = new JCheckBox();
        passField = new JPasswordField();
        userLabel = new JLabel();
        passLabel = new JLabel();
        msgArea = new JLabel();
        linkLabel = new JLabel();
        msgLabel = new JLabel();

        setTitle("校园网客户端" + Global.version);
        setSize(450, 350);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screen.width / 2 - 200, screen.height / 2 - 200);
        
        try {
			setIconImage(ImageIO.read(getClass().getResource("/img/logo32.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
        setResizable(false);
        
        logoLab.setIcon(new ImageIcon(getClass().getResource("/img/pic.png")));
        logoLab.setBounds(5, -15, 180, 180);
        titleLab.setForeground(new Color(255, 255, 255));
        titleLab.setText("校园网客户端");
        titleLab.setFont(new Font(FONT, STYLE, 30));
        titleLab.setBounds(200, 50, 200, 50);
        imgPanel.setLayout(null);
        imgPanel.setBackground(new Color(76,181,73));
        imgPanel.add(logoLab);
        imgPanel.add(titleLab);
        imgPanel.setBounds(0, 0, 450, 150);
        userLabel.setText("用户名");
        userLabel.setFont(new Font(FONT, STYLE, SIZE));
        userLabel.setBounds(50, 20, 100, 35);
        passLabel.setText("密  码");
        passLabel.setFont(new Font(FONT, STYLE, SIZE));
        passLabel.setBounds(50, 55, 100, 35);
 
        userField.setBorder(BorderFactory.createEtchedBorder());
        userField.setSelectionColor(new Color(76,181,73));
        userField.setFont(new Font(FONT, STYLE, SIZE));
        userField.setBounds(120, 20, 210, 35);
        PropertyRW reader = PropertyRW.getInstance();
        userField.setText(reader.getProperty(Global.USERNAME));
        
        passField.setBorder(BorderFactory.createEtchedBorder());
        passField.setSelectionColor(new Color(76,181,73));
        passField.setFont(new Font(FONT, STYLE, SIZE));
        passField.setBounds(120, 55, 210, 35);
        passField.setText(reader.getProperty(Global.PASSWORD));
        
        checkBox.setText("记住密码");
        checkBox.setFont(new Font(FONT, STYLE, SIZE));
        checkBox.setBounds(350, 20, 100, 35);
        checkBox.setOpaque(false);
        checkBox.setSelected(true);
        
        //登录后显示
        msgArea.setBounds(5, 5, 440, 110);
        msgArea.setFont(new Font(FONT, STYLE, SIZE+10));
        msgArea.setHorizontalAlignment(SwingConstants.CENTER);
        msgArea.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        mainPanel.setBackground(new Color(235, 242, 249));
        mainPanel.setBounds(0,150, 450, 120);
        mainPanel.setLayout(null);
        mainPanel.add(userLabel);
        mainPanel.add(passLabel);
        mainPanel.add(userField);
        mainPanel.add(passField);
        mainPanel.add(checkBox);
        
        msgLabel.setText("<html><u>通知</u></html>");
        msgLabel.setBounds(30, 10, 100, 20);
        msgLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        msgLabel.setFont(new Font(FONT, STYLE, SIZE - 5));
        
        logBut.setBackground(new Color(50, 79, 115));
        logBut.setForeground(Color.WHITE);
        logBut.setText("登录");
        logBut.setBorder(null);
        logBut.setFont(new Font(FONT, STYLE, SIZE));
        logBut.setBounds(120, 0, 210, 40);
        
        linkLabel.setText("<html><u>自服务</u></html>");
        linkLabel.setBounds(380, 10, 100, 20);
        linkLabel.setFont(new Font(FONT, STYLE, SIZE - 5));
        linkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        logbtPanel.setLayout(null);
        logbtPanel.setBackground(new Color(235, 242, 249));
        logbtPanel.setBounds(0, 270, 450, 80);
        //logbtPanel.add(msgLabel);
        logbtPanel.add(logBut);
        logbtPanel.add(linkLabel);
        
        Container c = getContentPane();
        c.setLayout(null);
        c.add(imgPanel);
        c.add(mainPanel);
        c.add(logbtPanel);
        
        msgArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(logBut.getText().equals("登录")){
					initLoginArea();
				}
				if(logBut.getText().equals("下线")){
					displayUserInfo();
				}
			}
		});
        
        msgLabel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e){
        		if(logBut.getText().equals("登录")){
        			updateMSG(Global.tipMSG, "请先登录");
        			return;
        		}
        		if(systemInfo == null || systemInfo.trim().equals("")){
        			updateMSG(Global.tipMSG, "未获取到通知");
        			return;
        		}
        		msgArea.setFont(new Font(FONT, STYLE, SIZE-2));
        		msgArea.setText(systemInfo);
        	}
		});
        
        //监听器
        logBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(logBut.getText().equals("登录")){
					login();
				}
				if(logBut.getText().equals("下线")){
					logout();
				}
				if(logBut.getText().equals("取消")){
					cancel();
				}
				//logBut.setEnabled(false);
			}	
		});
        linkLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PropertyRW reader = PropertyRW.getInstance();
				String scheme = reader.getProperty(Global.SCHEME);
				String host = reader.getProperty(Global.HOST);
				String p = reader.getProperty(Global.SERVICEPORT);
				int port = Integer.valueOf(p);
				URI uri = null;
				try {
					uri = new URIBuilder()
						.setScheme(scheme)
						.setHost(host)
						.setPort(port).build();
				} catch (URISyntaxException e2) {
					updateMSG(Global.tipMSG, "哎呀，出错了");
				}
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.browse(uri);
				} catch (IOException e1) {
					updateMSG(Global.tipMSG, "哎呀，出错了");
				}
			}
		});
       
        setTray();
        setVisible(true);
    }

public void setTray() {
		
		if(SystemTray.isSupported()){//判断当前平台是否支持托盘功能
			tray = SystemTray.getSystemTray();
			Image image = null;
			try {
				image = ImageIO.read(getClass().getResource("/img/logo16.png"));
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			String text = "校园网客户端";
			//3.弹出菜单popupMenu
			PopupMenu popMenu = new PopupMenu();
			MenuItem itmHide = new MenuItem("显示");
			itmHide.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					showWindow();
				}
			});
			MenuItem itmExit = new MenuItem("退出");
			itmExit.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					exitWindow();
				}
			});
			popMenu.add(itmHide);
			popMenu.add(itmExit);
			TrayIcon trayIcon = new TrayIcon(image,text,popMenu);
			trayIcon.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getButton() == e.BUTTON1)
						showWindow();
				}
			});
			try {
				tray.add(trayIcon);
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void showWindow() {
		this.setVisible(true);
	}

	public void exitWindow() {
		System.exit(0);
	}
    

   
    
    public void initMSGArea(){
    	msgArea.setFont(new Font(FONT, STYLE, SIZE+10));
    	mainPanel.removeAll();
    	mainPanel.add(msgArea);
    	msgArea.repaint();	
    }
    private void initLoginArea(){
    	mainPanel.removeAll();
    	mainPanel.add(userLabel);
    	mainPanel.add(userField);
    	mainPanel.add(passLabel);
    	mainPanel.add(passField);
    	mainPanel.add(checkBox);
    	mainPanel.repaint();
    }

    public void updateMSG(int type, String msg){
    	initMSGArea();
    	if (type == Global.loginokMSG){
    		msgArea.setText(msg);
    		logBut.setText("下线");
    		PropertyRW writer = PropertyRW.getInstance();
    		writer.setProperty(Global.USERNAME, user.getUsername());
    		if(checkBox.isSelected())
    			writer.setProperty(Global.PASSWORD, user.getPassword());
    		else
    			writer.setProperty(Global.PASSWORD, "");
    	}else if(type == Global.loginfailMSG){
    		msgArea.setText(msg);
    		logBut.setText("登录");
    	}else if(type == Global.userinfoMSG){
    		initUserInfo(msg);
    		msgArea.setText("欢迎");
    	}else if(type == Global.logoutokMSG){
    		msgArea.setText(msg);
    		logBut.setText("登录");
    	}else if(type == Global.cancelokMSG){
    		msgArea.setText(msg);
    		logBut.setText("登录");
    	}else if(type == Global.tipMSG){
    		if(msg.contains("MAC"))
    			logBut.setText("登录");
    		msgArea.setText(msg);
    	}else if(type == Global.systemMSG){
    		logbtPanel.add(msgLabel);
    		logbtPanel.repaint();
    		systemInfo = "<html>" + msg + "</html>";
    	}
    }
    private void initUserInfo(String msg){
    	String [] infos = msg.split(",");
    	user.setLoginTime(new Date(Long.valueOf(infos[1] + "000")) );
		user.setFlow(Float.valueOf(infos[6]) / 1024 / 1024 / 1024 );
		user.setIp(infos[8] );
		user.setMoney(Double.valueOf(infos[11]) );
	}
    private void displayUserInfo(){
    	msgArea.setFont(new Font(FONT, STYLE, SIZE - 2));
    	msgArea.setText(user.toString());
    }
    private void login() {
		if (server != null && server.isAlive() ){
			updateMSG(Global.loginokMSG,"已经登录");
			return;
		}
		String username = userField.getText();
		String password = new String( passField.getPassword() );
		if(username == null || username.trim().equals("")){
			updateMSG(Global.tipMSG, "请输入用户名");
			return;
		}
		if(password == null || password.trim().equals("")){
			updateMSG(Global.tipMSG, "请输入密码");
			return;
		}
		user.setUsername(username);
		user.setPassword(password);
		String passKey = PropertyRW.getInstance().getProperty(Global.PASSWORDKEY);
		password = PasswordMaker.encrypt(password, passKey);
		server = new Server(this);
		server.setRun(true);
		server.setClientState(Global.login);
		server.setUsername(username);
		server.setPassword(password);
		server.start();
		logBut.setText("取消");
	}

	private void logout() {
		if(server == null){
			updateMSG(Global.tipMSG, "哎呀，出错了");
			return;
		}
		server.doLogou();
	}

	private void cancel() {
		if(server == null){
			updateMSG(Global.tipMSG, "哎呀，出错了");
			return;
		}
		server.cancel();
	}
	
	 public static void main(String args[]) {
	    	MainWindow window = new MainWindow();
	    }

}
