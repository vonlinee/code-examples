
from scapy.all import *
def tcp_test(ip, port, data):

    # 第一次握手，发送SYN包
    # 请求端口和初始序列号随机生成
    # 使用sr1发送而不用send发送，因为sr1会接收返回的内容
    ans = sr1(IP(dst=ip) / TCP(dport=port, sport=RandShort(), seq=RandInt(), flags='S'), verbose=False)

    # 假定此刻对方已经发来了第二次握手包：ACK+SYN

    # 对方回复的目标端口，就是我方使用的请求端口（上面随机生成的那个）
    sport = ans[TCP].dport
    s_seq = ans[TCP].ack
    d_seq = ans[TCP].seq + 1

    # 第三次握手，发送ACK确认包
    send(IP(dst=ip) / TCP(dport=port, sport=sport, ack=d_seq, seq=s_seq, flags='A'), verbose=False)

    # 发起GET请求
    send(IP(dst=ip)/TCP(dport=port, sport=sport, seq=s_seq, ack=d_seq, flags=24)/data, verbose=False)
  
  
if __name__ == '__main__':


  
    data = 'GET / HTTP/1.1\n'
    data += 'Host: www.chengtu.com\n'
    data += 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36\n'
    data += 'Accept: text/html'
    data += '\r\n\r\n'

    tcp_test("150.138.151.65", 80, data)
















