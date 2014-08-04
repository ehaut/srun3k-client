client
======

srun300 client for Henan University of Techonlogy

半年期，河南工业大学将校园网义务移交给了河南联通. 开始使用一种叫做srun3000的客户端.
本来提供了linux客户端，但经此时无法使用,于是动手自己实现.使用wireshark反复抓包,最终弄清楚了其认证原理。并通过查找资料与探索破解了其密码加密算法.

安装：解压即可。windows用户请直接运行client.exe. linux用户请使用如下命令:
  java -jar client.jar
注意：无法运行的请自行安装jre或jdk. 也可以直接将jre解压至安装目录.
