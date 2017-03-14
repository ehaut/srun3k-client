# Srun3k PyQt5

[![GitHub release](https://img.shields.io/github/release/Matsuz/srun3k-client.svg?style=flat)]()

河南工业大学校园网客户端 desktop 版，基于 [Python][1] 3 与 [PyQt5][2]。

> 网页版 ([gh-pages 分支][5]) 登录：
> + [简约版][6] (English)
> + [界面美化版][7] (Chinese)

[1]: https://www.python.org/
[2]: https://www.riverbankcomputing.com/software/pyqt/download5
[5]: https://github.com/Matsuz/srun3k-client/tree/gh-pages
[6]: http://matsuz.github.io/srun3k-client/
[7]: http://matsuz.github.io/srun3k-client/index_zh.html

## Todo

+ [x] 开机启动 (Windows)
+ [ ] 自动检测 ACID
+ [ ] UDP 心跳包
+ [ ] 界面美化

## 如何安装

macOS 与 linux 版未经测试。如有问题，可[提交 issue][3].

[3]: https://github.com/Matsuz/srun3k-client/issues/new

### Windows

从 [Releases](https://github.com/Matsuz/srun3k-client/releases/latest) 页面下载。

### macOS

1. 安装 Python 3 与 pip.

2. [下载此 repo](https://github.com/Matsuz/srun3k-client/archive/pyqt5.tar.gz) 并解压。

3. 安装相关依赖

   `pip install requirements/common.txt`

4. 执行 `srun3k.py`

### GNU/Linux

linux 可按 macOS 的安装步骤安装。

但如果你使用的是 Ubuntu 或其他系统提供了 PyQt5 包的发行版。
亦可参考如下步骤安装：

~~~
sudo apt install python3-pyqt5 python3-requests
mkdir srun3k-pyqt5 && cd srun3k-pyqt5
curl -sSL https://github.com/Matsuz/srun3k-client/archive/pyqt5.tar.gz | tar -xz --strip=1
python3 srun3k.py
~~~

## FAQ

+ **Q:** 这个项目有什么用?

  **A:** 没什么用

+ **Q:** 为何不提供 macOS 版可执行程序?

  **A:** 穷逼买不起 Mac

+ **Q:** 为何不提供 linux 版可执行程序?

  **A:** 因为懒

+ **Q:** 为什么写这个 FAQ?

  **A:** 因为闲的蛋疼

## License

本项目基于 [DWTPL][4] 开放源代码协议发布。

[4]: http://www.wtfpl.net/about/

以下是协议全文：

~~~
        DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE 
                    Version 2, December 2004 

 Copyright (C) 2017 Matsuz <xiangsong.zeng@gmail.com> 

 Everyone is permitted to copy and distribute verbatim or modified 
 copies of this license document, and changing it is allowed as long 
 as the name is changed. 

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE 
   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION 

  0. You just DO WHAT THE FUCK YOU WANT TO.
~~~
