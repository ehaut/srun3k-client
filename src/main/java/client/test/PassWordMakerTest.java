package client.test;

import client.util.PasswordMaker;

public class PassWordMakerTest {
	public static void main(String [] args){
		String t = PasswordMaker.encryptPassword("sdfsdf4651sdfsdfHBJSqwertyuiopnm", "1234567890");
		System.out.println(t);
	}
}
