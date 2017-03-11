from PyQt5.QtWidgets import QApplication, QMainWindow, \
     QSystemTrayIcon, QStyle, QAction, QMenu, qApp, \
     QLabel, QLineEdit, QPushButton, QMessageBox

import sys
import json
import requests

# import requests

config_str = '''{
    "accounts": {
        "default": null,
        "storage": {}
    },
    "options": {
        "start_on_boot": false,
        "secret": "1234567890",
        "acid": 1,
        "mac": "00:00:00:00:00:00"
    },
    "server": {
        "url": {
            "portal": "http://172.16.154.130:69/cgi-bin/srun_portal",
            "info": "http://172.16.154.130/cgi-bin/rad_user_info",
            "getmsg": "http://172.16.154.130/get_msg.php",
            "detect_acid": "http://172.16.154.130",
            "account_settings": "http://172.16.154.130:8800"
        },
        "udp": {
            "ip": "172.16.154.130",
            "port": 3338
        }
    }
}'''


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

    def __del__(self):
        self.tray_icon.hide()  # hide tray icon before exit

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

        quit = QPushButton('退出', self)
        quit.resize(quit.sizeHint())
        quit.clicked.connect(qApp.quit)
        quit.move(96, 190)

        self.setGeometry(300, 300, 350, 230)
        self.setWindowTitle('Srun3k PyQt5')
        self.show()

    def init_tray(self):
        self.tray_icon = QSystemTrayIcon(self)
        self.tray_icon.setIcon(
            self.style().standardIcon(QStyle.SP_ComputerIcon))

        show_action = QAction('显示', self)
        quit_action = QAction('退出', self)
        show_action.triggered.connect(self.show)
        quit_action.triggered.connect(qApp.quit)

        tray_menu = QMenu()
        tray_menu.addAction(show_action)
        tray_menu.addAction(quit_action)

        self.tray_icon.setToolTip('Srun3k Client PyQt5')
        self.tray_icon.setContextMenu(tray_menu)
        self.tray_icon.show()

    def closeEvent(self, event):
        """覆盖关闭窗口事件，点击关闭时最小化到托盘"""
        event.ignore()
        self.hide()

    def login(self):
        password = self.password_edit.text()
        password_enc = self.password_encrypt(password,
                                             self.config['options']['secret'])
        payload = {
            "action": "login",
            "username": self.username_encrypt(self.username_edit.text()),
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
        cf = open('config.json')
    except IOError:
        QMessageBox.information(None, 'Error', '无法打开配置文件 config.json')
        config = json.loads(config_str)
    else:
        config = json.loads(cf.read())
        cf.close()

    srun3k = MainWindow(config)
    sys.exit(app.exec_())
