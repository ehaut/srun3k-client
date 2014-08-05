Client
=======

srun300 client for Henan University of Techonlogy<br>

![](https://github.com/hauter/client/blob/master/view/view.png)
![](https://github.com/hauter/client/blob/master/view/view1.png)

安装
---------------------------
windows用户下载client.zip,解压至任意位置即可.
linux用户下载client.tar.gz,解压至任意位置.

运行
------------------------------
windows用户请直接运行client.exe.
注意: 无法运行的请自行安装jre或jdk. 也可以直接将jre解压至安装目录.

linux用户请使用如下命令: 
```
java -jar client.jar
```
主要: 无法运行请自行安装jre或jdk.

建立快捷方式
--------------------------------
windows用户请直接将client.exe发送至桌面快捷方式即可.
linux用户请用vi / vim / gedit 等编辑器打开client.desktop文件进行编辑。
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


简单协议介绍
------------
经抓包发现, srun3000 使用了两种协议: htpp udp.<br>
服务器对用户的http请求分为: login, logout, get_msg, read_userinfo <br>
而udp协议的用处是，在登陆过后，每隔一定时间（srun3000是50s)，向服务器发送一次udp报文，保证用户持续在线。

#http请求简单介绍
http请求较为简单，请求头部分无所谓，服务器不去校验。而校验的是数据部分。应为所以的请求都是以post的方式提交的。
action=login&username=201110000000&password=7cc%3e%3fcc%3c%3dcc%3a&drop=0&pop=1&type=2&n=117&ip=1947734188&mbytes=0&minutes=0&ac_id=6&mac=60%3aeb%3a69%3adf%3a60%3aa8&cguid=%7b21ACD077-2EB8-227D-1309-395B4FC2EB45%7d&key=2294EC2A39F14D9C41F50085C35707C0
可以看出只是简单的以&分割，没用什么难处。很多也是没用用处的，服务器并没见进行校验。如minutes, guid, ac_id等，暂时也不清楚含义。其中type有1和2两种，1代表以web方式登陆，2代表以客户端方式登陆。区别在于，web方式不对password加密，而客户端进行加密。经过测试两种方式都是可以成功登陆的，但鉴于web方式可能会被放弃，所以项目中实现了以客户端加密的方式提交。<br>




如有疑问请邮件:szq921@gmail.com
