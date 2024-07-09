import base64
import json
import socket
from controller.aliy.socket_sent import send_data

from controller.socket import read2byte

def send_message(host, port, message):
    """
    向指定主机和端口发送消息。

    参数：
        host (str): 目标主机的 IP 地址或主机名。
        port (int): 目标主机的端口号。
        message (dict): 要发送的消息，必须是一个字典对象。
    """
    try:
        # 创建客户端套接字
        client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

        # 连接到目标主机
        client_socket.connect((host, port))

        # 将消息转换为 JSON 格式
        message_json = json.dumps(message)

        # 发送消息
        client_socket.send(message_json.encode())

        print("消息发送成功！")

        # 关闭套接字
        client_socket.close()
    except Exception as e:
        print("发送消息时发生错误：", e)




img_path = "G:\\pycharmPro\\PyCharm 2023.1.3\\jbr\\bin\\yolo\\controller\\runs\\detect\\predict306\\image0.jpg"

byte = read2byte(img_path)
# 将二进制数据转换成 base64 编码的字符串
content_base64 = base64.b64encode(byte).decode()

# 读取json文件
res = {
    'Json': json.dumps([
    {
        "class": "Septoria",
        "confidence": 0.904275
    },
    {
        "class": "Septoria",
        "confidence": 0.896912
    }]
    ),
    'img': content_base64
}
sendmsg = json.dumps(res)
# # 获取本目标主机名称
target_host = "192.168.9.164"
# 设置端口号
target_port = 12347
# 将套接字与本地主机和端口绑定
send_message(target_host, target_port, res)