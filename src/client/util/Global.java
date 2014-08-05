package client.util;

public class Global {
	public static final String version = " v0.1.1";
	
	/*
	 * 类似C语言中的条件编译,如果forEXE为真表明当前编译目的是为了用exe4j包装成exe文件, 为假则不是.
	 * 区别在于client.util.ProPertyRW中对file路径的设置不同
	 */
	public static final boolean forEXE = false;
	
	public static final String PROPERTYFILE = "date.properties";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String KEY = "key";
	public static final String SCHEME = "scheme";
	public static final String HOST = "host";
	public static final String LOGINPATH = "loginpath";
	public static final String LOGOUTPATH = "logoutpath";
	public static final String GETMSGPATH = "getmsgpath";
	public static final String READUSERINFO = "readuserinfo";
	public static final String PASSWORDKEY = "passwordkey";
	public static final String UDPPORT = "udpport";
	public static final String SERVICEPORT = "serviceport";

	// 消息类型
	public static final int loginokMSG = 1;
	public static final int loginfailMSG = 2;
	public static final int tipMSG = 3;
	public static final int userinfoMSG = 4;
	public static final int logoutokMSG = 5;
	public static final int cancelokMSG = 5;
	public static final int systemMSG = 6;

	// 客户端状态
	public static final int offline = 0;
	public static final int online = 1;
	public static final int login = 2;
	public static final int logout = 3;

}