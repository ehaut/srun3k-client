Client
=======

srun300 client for Henan University of Techonlogy<br>

![](https://github.com/hauter/client/blob/master/view/view.png)
![](https://github.com/hauter/client/blob/master/view/view1.png)

更新日志
---

2014-8-8 v0.1.2

* 修复密码加密算法bug

2014-8-6 v0.1.1

* 修复mac地址获取获取算法错误
* 加入网卡选择功能

2014-8-4 v0.1.0

* 项目发布

---

安装
-----
windows用户下载client.zip,解压至任意位置即可.<br>
linux用户下载client.tar.gz,解压至任意位置.

运行
-----
windows用户请直接运行client.exe.<br>
注意: 无法运行的请自行安装jre或jdk. 也可以直接将jre解压至安装目录.<br>

linux用户请使用如下命令: 
```
java -jar client.jar
```
主要: 无法运行请自行安装jre或jdk.

建立快捷方式
-------------
windows用户请直接将client.exe发送至桌面快捷方式即可.<br>
linux用户请用vi / vim / gedit 等编辑器打开client.desktop文件进行编辑。<br>
例如：client的安装目录为/home/abc/client

1.将Exec=行修改为:
```
Exec=java -jar /home/abc/client/client.jar
```

2.将Icon=行修改为:
```
Icon=/home/abc/client/logo128.png
```

3.将编辑后的文件复制至自己的桌面

注意: 所以的修改必须是用户自己的情况而定


写给开发者
-----------
编译此项目，你需要加入的包有commons-logging-1.1.3.jar  httpclient-4.3.4.jar  httpcore-4.3.2.jar


简单协议介绍
------------
经抓包发现, srun3000 使用了两种协议: htpp udp.<br>
服务器对用户的http请求分为: login, logout, get_msg, read_userinfo <br>
而udp协议的用处是，在登陆过后，每隔一定时间（srun3000是50s)，向服务器发送一次udp报文，保证用户持续在线。

#http请求简单介绍
http请求较为简单，请求头部分无所谓，服务器不去校验。而校验的是数据部分。应为所以的请求都是以post的方式提交的。<br>
```
action=login&username=201110000000&password=7cc%3e%3fcc%3c%3dcc%3a&drop=0&pop=1&type=2&n=117&ip=1947734188&mbytes=0&minutes=0&ac_id=6&mac=60%3aeb%3a69%3adf%3a60%3aa8&cguid=%7b21ACD077-2EB8-227D-1309-395B4FC2EB45%7d&key=2294EC2A39F14D9C41F50085C35707C0
```
可以看出只是简单的以&分割，没用什么难处。很多也是没用用处的，服务器并没见进行校验。如minutes, guid, ac_id等，暂时也不清楚含义。其中type有1和2两种，1代表以web方式登陆，2代表以客户端方式登陆。区别在于，web方式不对password加密，而客户端进行加密。经过测试两种方式都是可以成功登陆的，但鉴于web方式可能会被放弃，所以项目中实现了以客户端加密的方式提交。<br>
另外，虽然上述数据对一些符号进行了unicode编码，但也不是完全unicode编码，经测试不许进行编码也是可以。<br>

##加密
需要深入探索的是其加密过程，网上有文章说是，客户端获取服务器时间，根据获得的时间生成key进行加密。但经过测试发现任何时间的密文都是一样的，于是排除此种猜测。但文中给出的算法却是有意义的。经过反复实验，推算，最终发现其加密用的key竟然是1234567890，也已经被写进了项目的data.properties文件中，防止更改后需要重新修改代码。看来key实际是已经编译进了程序中。这样脑残的做法，只能呵呵。<br>

```java
public class PasswordMaker {
    public static String encrypt(String str, String key) {
        String res = "";
        for (int i = 0; i < str.length(); i++) {
            int ki = (key.charAt(key.length() - i % key.length() - 1));
            int pi = (str.charAt(i));
            ki = ki ^ pi;
            res += buildkey(ki, i % 2);
        }
        try {
            res = URLEncoder.encode(res, "UTF-8").toLowerCase();
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException();
        }
        return res;
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

```



udp协议简单介绍
--------------
```
真正数据部分为最后56B

客户 --> 服务器
估计前32B 为用户名部分，后24B 为mac
0000   32 30 31 31 31 30 30 30 	30 30 30 30 00 00 00 00  201110000000....
0010   00 00 00 00 00 00 00 00 	00 00 00 00 00 00 00 00  ................
0020   36 30 3a 65 62 3a 36 39 	3a 64 66 3a 36 30 3a 61  60:eb:69:df:60:a
0030   38 00 00 00 00 00 00 00         	                 8.......

服务器 --> 客户
48B 用户名 + 8 B填充
0000   32 30 31 31 31 30 30 30 	30 30 30 30 00 00 00 00  201110000000....
0010   00 00 00 00 00 00 00 00 	00 00 00 00 00 00 00 00  ................
0020   00 00 00 00 00 00 00 00 	00 00 00 00 00 00 00 00  ................
0030   02 00 00 00 ff ff ff ff 	                         ........
```
udp协议为无连接的，不保证一定可达。于是项目中只是每隔60s发一次报文，但并不去接受。



如有疑问请邮件:szq921@gmail.com
