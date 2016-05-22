package client.test;

import java.net.UnknownHostException;

import client.util.IPConverter;

public class IPConverterTest {

	public static void main(String[] args) throws UnknownHostException {
		System.out.println(IPConverter.ipToLong("172.16.24.116"));
		System.out.println(IPConverter.longToIP(1947734188));	
	}
}
