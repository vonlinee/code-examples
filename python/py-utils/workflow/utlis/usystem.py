# -*- coding: utf-8 -*-
import os # os为内置模块，是一定存在的
import sys
# 运行时自动检测外部模块是否安装，没有则自动安装
from io import StringIO

def run_system_command(command: str):
    """
    用于系统命令执行
    如果对输入没有做好过滤，就是出现命令执行（命令注入）漏洞
    """
    result = None
    try:
        result = os.system(command)
    except BaseException as e:
        print(e.args)
    return result


def pip_install_package(package_name: str):
    try:
        run_system_command('pip install ' + package_name)
    except:
        print("Failed to install package " + package_name)


try:
    import requests
except ImportError as e:
    pip_install_package('requests')
    import requests


class RedirectStdout:
    """
    输出重定向
    """
    def __init__(self):
        self.content = ''
        self.savedStdout = sys.stdout
        self.memObj, self.fileObj, self.nulObj = None, None, None

    #外部的print语句将执行本write()方法，并由当前sys.stdout输出
    def write(self, outStr):
        #self.content.append(outStr)
        self.content += outStr

    def toConsole(self):  #标准输出重定向至控制台
        sys.stdout = self.savedStdout #sys.__stdout__

    def toMemory(self):  #标准输出重定向至内存
        self.memObj = StringIO()
        sys.stdout = self.memObj

    def toFile(self, file='out.txt'):  #标准输出重定向至文件
        self.fileObj = open(file, 'a+', 1) #改为行缓冲
        sys.stdout = self.fileObj
    
    def toMute(self):  #抑制输出
        self.nulObj = open(os.devnull, 'w')
        sys.stdout = self.nulObj
        
    def restore(self):
        self.content = ''
        if self.memObj.closed != True:
            self.memObj.close()
        if self.fileObj.closed != True:
            self.fileObj.close()
        if self.nulObj.closed != True:
            self.nulObj.close()
        sys.stdout = self.savedStdout #sys.__stdout__


# syslog的官方说明
# https://docs.python.org/2/library/syslog.html#module-syslog
import platform # 获取操作系统的信息

os_platform = platform.system()         # string：Windows/Linux
system_platform = platform.platform()   # 获取操作系统名称及版本号，'Linux-3.13.0-46-generic-i686-with-Deepin-2014.2-trusty'
platform_version = platform.version()   # 获取操作系统版本号，'#76-Ubuntu SMP Thu Feb 26 18:52:49 UTC 2015'
architecture = platform.architecture()  # 获取操作系统的位数，('32bit', 'ELF')，tuple
machine = platform.machine()   		    # 计算机类型，'i686'
node = platform.node()       		    # 计算机的网络名称，'XF654'
processor = platform.processor()  		# 计算机处理器信息，''i686'
uname = platform.uname()      		    # 包含上面所有的信息汇总，('Linux', 'XF654', '3.13.0-46-generic', '#76-Ubuntu SMP Thu 

# dist() and linux_distribution() functions are deprecated in Python 3.5
linux_distribution = platform.linux_distribution()

# 获取设备中Python的信息
python_build = platform.python_build()
python_compiler = platform.python_compiler()
python_branch = platform.python_branch()
python_implementation = platform.python_implementation()
python_revision = platform.python_revision()
python_version = platform.python_version()
python_version_tuple = platform.python_version_tuple()


"""

import sysconfig
import syslog

class mysyslog(object):
    # level
    # LOG_EMERG, LOG_ALERT, LOG_CRIT, LOG_ERR, LOG_WARNING, LOG_NOTICE, LOG_INFO, LOG_DEBUG
    debug = syslog.LOG_DEBUG
    info = syslog.LOG_INFO
    notice = syslog.LOG_NOTICE
    warning = syslog.LOG_WARNING
    err = syslog.LOG_ERR
    crit = syslog.LOG_CRIT
    alert = syslog.LOG_ALERT
    emerg = syslog.LOG_EMERG

    # logoption
    # LOG_PID, LOG_CONS, LOG_NDELAY, LOG_NOWAIT, LOG_PERROR
    # LOG_CONS：如果将信息发送给守护进程时发生错误，直接将相关信息输入到相关信息输出到终端。
    # LOG_NDELAY：立即打开与系统日志的连接（通常情况下，只有在产生第一条日志信息的情况下才会打开与日志系统的连接）
    # LOG_NOWAIT：在记录日志信息时，不等待可能的子进程的创建
    # LOG_ODELAY：类似于LOG_NDELAY参数，与系统日志的连接只有在syslog函数调用时才会创建
    # LOG_PID：每条日志信息中都包括进程号
    cons = syslog.LOG_CONS
    ndelay = syslog.LOG_NDELAY
    nowait = syslog.LOG_NOWAIT
    pid = syslog.LOG_PID

    # facility
    # LOG_KERN, LOG_USER, LOG_MAIL, LOG_DAEMON, LOG_AUTH, LOG_LPR, LOG_NEWS, LOG_UUCP, LOG_CRON, LOG_SYSLOG, LOG_LOCAL0 to LOG_LOCAL7
    # kern = syslog.LOG_KERN
    # user = syslog.LOG_USER
    # mail = syslog.LOG_MAIL
    # daemon = syslog.LOG_DAEMON
    # auth = syslog.LOG_AUTH
    # lpr = syslog.LOG_LPR
    # news = syslog.LOG_NEWS
    # uucp = syslog.LOG_UUCP
    # cron = syslog.LOG_CRON
    # _syslog = syslog.LOG_SYSLOG

    @classmethod
    def __init__(self):
        pass

    @staticmethod
    def basicConfig(name, logoption):
        facility = syslog.LOG_USER
        syslog.openlog(name, logoption, facility)

    @staticmethod
    def tosyslog(level, ip, messages):
        newmessages = "[" + ip + "]" + " " + messages
        syslog.syslog(level, newmessages)






"""







