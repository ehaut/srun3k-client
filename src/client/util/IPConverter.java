package client.util;

public class IPConverter {	
	// 将127.0.0.1形式的IP地址转换成十进制整数，这里没有进行任何错误处理
		public static long ipToLong(String strIp) {
			long[] ip = new long[4];
			// 先找到IP地址字符串中.的位置
			int position1 = strIp.indexOf(".");
			int position2 = strIp.indexOf(".", position1 + 1);
			int position3 = strIp.indexOf(".", position2 + 1);
			// 将每个.之间的字符串转换成整型
			ip[3] = Long.parseLong(strIp.substring(0, position1));
			ip[2] = Long.parseLong(strIp.substring(position1 + 1, position2));
			ip[1] = Long.parseLong(strIp.substring(position2 + 1, position3));
			ip[0] = Long.parseLong(strIp.substring(position3 + 1));
			return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
		}

		// 将十进制整数形式转换成127.0.0.1形式的ip地址
		public static String longToIP(long longIp) {
			StringBuffer sb = new StringBuffer("");
			// 将高24位置0
			sb.append(String.valueOf((longIp & 0x000000FF)));
			sb.append(".");
			
			// 将高16位置0，然后右移8位
			sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
			sb.append(".");
			
			// 将高8位置0，然后右移16位
			sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
			sb.append(".");			
			// 直接右移24位
			sb.append(String.valueOf((longIp >>> 24)));
			return sb.toString();
		}
}
