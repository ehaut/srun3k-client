/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.test;

import client.bean.LogoutBean;
import client.exception.GetMACFailException;

/**
 *
 * @author Administrator
 */
public class LogoutBeanTest {
    public static void main(String [] args) throws GetMACFailException{
    	String mac = "60:eb:69:df:60:a8";

        LogoutBean bean = new LogoutBean("201116040117", mac);
        System.out.println(bean.toEntityString());
    }
    
}
