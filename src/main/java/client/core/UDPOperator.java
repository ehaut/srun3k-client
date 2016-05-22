package client.core;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPOperator {

    private static byte[] getDataByte(String username, String mac) {
        byte[] u = username.getBytes();
        byte[] m = mac.getBytes();
        if (username.length() > 32 || mac.length() > 24) {
            throw new RuntimeException();
        }
        byte[] data = new byte[56];
        for (int i = 0; i < u.length; ++i) {
            data[i] = u[i];
        }
        for (int i = 32, j = 0; j < m.length; ++i, ++j) {
            data[i] = m[j];
        }
        return data;
    }

    public static void send(String address, int port, String username, String mac) {
        byte[] data = getDataByte(username, mac);
        DatagramSocket client = null;
        try {
            client = new DatagramSocket();
            InetAddress addr = InetAddress.getByName(address);
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, addr, port);
            client.send(sendPacket);
        } catch (IOException ex) {
                throw new RuntimeException();
        }finally{
            try{
                client.close();
            }catch (Exception e){
                throw new RuntimeException();
            }
        }
    }
}
