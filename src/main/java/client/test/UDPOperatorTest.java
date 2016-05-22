package client.test;

import client.core.UDPOperator;
import client.exception.GetMACFailException;
import client.util.Global;
import client.util.PropertyRW;

public class UDPOperatorTest {

    public static void main(String[] args) throws GetMACFailException {
        PropertyRW pr = PropertyRW.getInstance();
        String a = pr.getProperty(Global.HOST);
        int p = Integer.parseInt(pr.getProperty(Global.UDPPORT));
        String u = pr.getProperty(Global.USERNAME);
        String mac = "60:eb:69:df:60:a8";
        UDPOperator.send(a, p, u, mac);
    }
}
