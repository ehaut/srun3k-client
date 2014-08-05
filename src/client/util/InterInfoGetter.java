package client.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Vector;

import client.exception.GetIPFailException;
import client.exception.GetMACFailException;

public class InterInfoGetter {

	public static Vector<NetworkInterface> getInterfaces() throws SocketException {
		Vector<NetworkInterface> interfaces = new Vector<>();
		Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
		while (nets.hasMoreElements()){
			NetworkInterface inter = nets.nextElement();
			if(inter.isUp() && !inter.isLoopback() && !inter.isPointToPoint()){
				//System.out.println(inter.getDisplayName());
				interfaces.add(inter);
			}
		}
		return interfaces;
	}
	
	public static String getLocalMac(NetworkInterface inter) throws GetMACFailException{
		
		byte[] m;
		try {
			m = inter.getHardwareAddress();
		} catch (SocketException e) {
			throw new GetMACFailException();
		}
		return makeMAC(m);
	}
	public static String getLocalIP(NetworkInterface inter) throws GetIPFailException{
		Enumeration<InetAddress> adds = inter.getInetAddresses();
		InetAddress add = null;
		if(adds.hasMoreElements())
			add = adds.nextElement();
		if(add == null) throw new GetIPFailException();
		return add.getHostAddress();
	}
	
	
	private static String makeMAC(byte [] m){
		String mac = "";
		for (byte b : m) {
			int temp = b & 0xff;
			if (temp < 16)
				mac += "0" + Integer.toHexString(temp) + ":";
			else
				mac += Integer.toHexString(temp) + ":";
		}
		mac = mac.substring(0, mac.length() - 1);
		return mac;
	}
	
	
}


/*package client.util;

import java.net.InetAddress;
import java.net.NetworkInterface;

import client.exception.GetMACFailException;

public class MACGetter {
	public static String getLocalMac() throws GetMACFailException {
		byte[] m = null;
		try {
			InetAddress add = InetAddress.getLocalHost();
			m = NetworkInterface.getByInetAddress(add).getHardwareAddress();
		} catch (Exception e) {
			throw new RuntimeException();
		}
		if (m == null || m.length == 0)
			throw new GetMACFailException();
		String mac = "";

		for (byte b : m) {
			int temp = b & 0xff;
			if (temp < 16)
				mac += "0" + Integer.toHexString(temp) + ":";
			else
				mac += Integer.toHexString(temp) + ":";
		}
		mac = mac.substring(0, mac.length() - 1);
		return mac;
	}
}
*/