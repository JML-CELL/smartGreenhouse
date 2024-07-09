# 创建服务器套接字
import socket
from controller.aliy.socket_sent import send_data

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

res = {
    "res": '',
    "img": '',
    "path": '123123'
}

# 获取本地主机名称
host = "172.20.10.2"
# 设置端口号
port = 12347
# 将套接字与本地主机和端口绑定
server_socket.bind((host, port))
# 设置监听最大连接数
server_socket.listen(5)

send_data(server_socket,res)