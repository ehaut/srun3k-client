package client.util;

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
