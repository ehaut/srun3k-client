from PyQt5.QtWidgets import QApplication, QMainWindow, \
     QSystemTrayIcon, QStyle, QAction, QMenu, qApp, \
     QLabel, QLineEdit, QPushButton, QMessageBox

import sys
import json
import os
import requests

default_config = {
    "accounts": {
        "default": None,
        "storage": {},
    },
    "options": {
        "autostartup": False,
        "secret": "1234567890",
        "acid": 1,
        "mac": "00:00:00:00:00:00",
    },
    "server": {
        "url": {
            "portal": "http://172.16.154.130:69/cgi-bin/srun_portal",
            "info": "http://172.16.154.130/cgi-bin/rad_user_info",
            "getmsg": "http://172.16.154.130/get_msg.php",
            "detect_acid": "http://172.16.154.130",
            "account_settings": "http://172.16.154.130:8800",
        },
        "udp": {
            "ip": "172.16.154.130",
            "port": 3338,
        }
    },
}


class MainWindow(QMainWindow):
    """
        srun3k client pyqt5 edition
        main window
    """

    def __init__(self, config):
        super().__init__()
        self.config = config
        self.session = requests.Session()

        self.init_window()
        self.init_tray()

        self.init_account()

    def __del__(self):
        self.tray_icon.hide()  # hide tray icon before exit

    def icon_activated(self, reason):
        if reason == QSystemTrayIcon.Trigger or \
           reason == QSystemTrayIcon.DoubleClick:
            if self.isHidden():
                self.show()
            else:
                self.hide()

    def setAutoStartup(self):
        try:
            import winreg
            from zlib import crc32  # for hash
        except ImportError:
            return

        path = os.path.realpath(sys.executable)

        key_name = 'Srun3kClient_' + str(crc32(path.encode()))
        hkey = winreg.OpenKey(
            winreg.HKEY_CURRENT_USER,
            r'Software\Microsoft\Windows\CurrentVersion\Run',
            access=winreg.KEY_SET_VALUE)
        if self.autostartup_action.isChecked():
            winreg.SetValueEx(hkey, key_name, 0, winreg.REG_SZ, path)
        else:
            try:
                winreg.DeleteValue(hkey, key_name)
            except FileNotFoundError:
                pass  # 该键不存在，不进行处理
        winreg.CloseKey(hkey)
        self.config['options'][
            'autostartup'] = self.autostartup_action.isChecked()
        self.save_config()

    def init_account(self):
        default = self.config['accounts']['default']
        if default in self.config['accounts']['storage']:
            password = self.config['accounts']['storage'][default]
            self.username_edit.setText(default)
            self.password_edit.setText(password)

    def init_window(self):
        username = QLabel('用户名:', self)
        username.move(15, 10)

        self.username_edit = QLineEdit(self)
        self.username_edit.move(75, 10)

        password = QLabel('密码:', self)
        password.move(15, 45)

        self.password_edit = QLineEdit(self)
        self.password_edit.setEchoMode(QLineEdit.Password)
        self.password_edit.move(75, 45)

        login = QPushButton('登录', self)
        login.resize(login.sizeHint())
        login.clicked.connect(self.login)
        login.move(15, 96)

        logout = QPushButton('注销', self)
        logout.resize(logout.sizeHint())
        logout.clicked.connect(self.logout)
        logout.move(120, 96)

        status = QPushButton('显示状态', self)
        status.resize(status.sizeHint())
        status.clicked.connect(self.show_status)
        status.move(15, 120)

        message = QPushButton('显示公告', self)
        message.resize(status.sizeHint())
        message.clicked.connect(self.show_message)
        message.move(120, 120)

        self.status = QLabel('', self)
        self.status.move(15, 150)

        hide = QPushButton('隐藏到后台', self)
        hide.resize(hide.sizeHint())
        hide.clicked.connect(self.hide)
        hide.move(15, 190)

        quit = QPushButton('退出', self)
        quit.resize(quit.sizeHint())
        quit.clicked.connect(qApp.quit)
        quit.move(96, 190)

        about = QLabel('关于', self)
        about_link = 'https://github.com/Matsuz/srun3k-client/tree/pyqt5'
        about.setText("<a href='%s'>关于</a>" % about_link)
        about.setOpenExternalLinks(True)
        about.move(190, 190)

        version = QLabel('version 0.1.0.1', self)
        version.setStyleSheet('QLabel { color: grey; }')
        version.resize(version.sizeHint())
        version.move(240, 215)

        self.setGeometry(300, 300, 350, 230)
        self.setWindowTitle('Srun3k PyQt5')
        self.show()

    def init_tray(self):
        self.tray_icon = QSystemTrayIcon(self)
        self.tray_icon.setIcon(
            self.style().standardIcon(QStyle.SP_ComputerIcon))

        show_action = QAction('显示', self)
        quit_action = QAction('退出', self)
        self.autostartup_action = QAction('开机启动', self, checkable=True)
        show_action.triggered.connect(self.show)
        quit_action.triggered.connect(qApp.quit)
        self.autostartup_action.triggered.connect(self.setAutoStartup)

        self.autostartup_action.setChecked(
            self.config['options']['autostartup'])

        tray_menu = QMenu()
        tray_menu.addAction(show_action)
        tray_menu.addAction(self.autostartup_action)
        tray_menu.addAction(quit_action)

        self.tray_icon.activated.connect(self.icon_activated)

        self.tray_icon.setToolTip('Srun3k Client PyQt5')
        self.tray_icon.setContextMenu(tray_menu)
        self.tray_icon.show()

    def closeEvent(self, event):
        """覆盖关闭窗口事件，点击关闭时最小化到托盘"""
        event.ignore()
        self.hide()

    def save_config(self):
        """保存配置文件"""
        current_username = self.username_edit.text()
        current_password = self.password_edit.text()
        if len(current_username) > 0:
            self.config['accounts']['default'] = current_username
            self.config['accounts']['storage'][
                current_username] = current_password

        path = 'config.json'
        if os.name == 'nt':
            path = os.path.join(
                os.path.dirname(os.path.realpath(sys.executable)), path)
        with open(path, mode='w') as cf:
            cf.write(json.dumps(self.config, indent=4))

    def login(self):
        username = self.username_edit.text()
        password = self.password_edit.text()
        if len(username) == 0 or len(password) == 0:
            QMessageBox.information(self, 'Error', '用户名或密码为空!')
            return

        self.save_config()

        password_enc = self.password_encrypt(password,
                                             self.config['options']['secret'])
        payload = {
            "action": "login",
            "username": self.username_encrypt(username),
            "password": password_enc,
            "mac": self.config['options']['mac'],
            "ac_id": self.config['options']['acid'],
            "drop": 0,
            "pop": 1,
            "type": 2,
            "n": 117,
            "mbytes": 0,
            "minutes": 0,
        }
        r = self.session.post(
            self.config['server']['url']['portal'], data=payload)
        self.status.setText(r.text)
        self.status.resize(self.status.sizeHint())

    def logout(self):
        payload = {
            "action": "logout",
            "username": self.username_edit.text(),
            "ac_id": self.config['options']['acid'],
            "type": 2,
        }
        r = self.session.post(
            self.config['server']['url']['portal'], data=payload)
        self.status.setText(r.text)
        self.status.resize(self.status.sizeHint())

    def show_status(self):
        r = self.session.get(self.config['server']['url']['info'])
        self.status.setText(r.text)
        self.status.resize(self.status.sizeHint())

    def show_message(self):
        r = self.session.get(self.config['server']['url']['getmsg'])
        self.status.setText(r.text)
        self.status.resize(self.status.sizeHint())

    @staticmethod
    def username_encrypt(username):
        result = '{SRUN3}\r\n'
        return result + ''.join([chr(ord(x) + 4) for x in username])

    @staticmethod
    def password_encrypt(password, key='1234567890'):
        result = ''
        for i in range(len(password)):
            ki = ord(password[i]) ^ ord(key[len(key) - i % len(key) - 1])
            _l = chr((ki & 0x0F) + 0x36)
            _h = chr((ki >> 4 & 0x0F) + 0x63)

            if i % 2 == 0:
                result += _l + _h
            else:
                result += _h + _l
        return result


if __name__ == '__main__':
    app = QApplication(sys.argv)

    try:
        dirname = os.path.dirname(os.path.realpath(sys.executable))
        path = os.path.join(dirname, 'config.json')
        cf = open(path)
    except IOError:
        QMessageBox.information(None, 'Error', '无法打开配置文件 %s' % path)
        config = default_config
    else:
        config = json.loads(cf.read())
        cf.close()

    srun3k = MainWindow(config)
    sys.exit(app.exec_())
