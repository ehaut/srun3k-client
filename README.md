# Srun3k Client
Srun3k Client written in HTML/JavsScript for haut network authorization

河南工业大学校园网客户端（HTML/JavaScript版）

纯 HTML + JavaScript 实现，仅需任意浏览器一枚即可运行，无需安装任何运行时。

+ 网页版 [https://samuelts.github.io/srun3k-client/](https://samuelts.github.io/srun3k-client/)
+ 离线版(将网页保存至本地使用浏览器打开)

## 使用说明

### 登录
`username` 栏输入用户名，`password`栏输入密码。点击`login`，如果出现
```
login ok......
```
这样的消息，表示登录成功。

### 注销登录
点击`logout`，如果出现
```
logout ok
```
表示注销成功。

### 查看目前登录状态
点击`Read User Info`，如果出现
```
not online
```
表示目前没有登录校园网。已经登录校园网的会出现校园网使用信息（几个数字）。

### 查看校园网广播信息
点击`Get Message`，可以查看校园网广播的消息。

如果有什么情况可能会断网，一般会提前在这里通知。
