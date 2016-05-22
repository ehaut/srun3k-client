package client.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PasswordMaker {
    public static String encryptUsername(String username) {
        String result = "{SRUN3}\r\n";
        for (int i = 0; i < username.length(); ++i) {
            result += (char)((int)username.charAt(i) + 4);
        }
        try {
            result = URLEncoder.encode(result, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String encryptPassword(String str, String key) {
        String res = "";
        for (int i = 0; i < str.length(); i++) {
            int ki = (key.charAt(key.length() - i % key.length() - 1));
            int pi = (str.charAt(i));
            ki = ki ^ pi;
            res += buildkey(ki, i % 2);
        }
        /*try {
            res = URLEncoder.encode(res, "UTF-8").toLowerCase();
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException();
        }*/
        String pass = "";
        for(int i = 0; i < res.length(); ++i){
        	char c = res.charAt(i);
        	if( c >= ':' && c <= '@'){
        		try {
					pass += URLEncoder.encode(c + "", "UTF-8").toLowerCase();
				} catch (UnsupportedEncodingException e) {
					 throw new RuntimeException();
				}
        	}else{
        		pass += c;
        	}
        		
        }

        return pass;
    }

    public static String buildkey(int num, int reverse) {
        String ret = "";
        int _low = num & 0x0f;
        int _high = num >> 4;
        _high = _high & 0x0f;

        if (reverse == 0) {
            char temp1 = (char) (_low + 0x36);
            char temp2 = (char) (_high + 0x63);

            ret = temp1 + "" + temp2;
        } else {
            char temp1 = (char) (_high + 0x63);
            char temp2 = (char) (_low + 0x36);

            ret = temp1 + "" + temp2;
        }
        return ret;
    }
}
