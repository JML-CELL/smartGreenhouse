import json
import socket


def send_data(server_socket, content, encoding="utf-8"):
    try:
        # 假设 content 是一个字典，我们将它转换为 JSON 字符串
        sendmsg = json.dumps(content)

        # 等待客户端连接
        clientsocket, addr = server_socket.accept()
        print(f"客户端连接地址: {addr}")

        # 发送字符串数据给客户端
        clientsocket.send(sendmsg.encode(encoding))
    except Exception as e:
        print(e)
    finally:
        clientsocket.close()


def main():
    # 创建服务器套接字
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # 获取本地主机名称
    host = "172.20.10.6"
    # 设置端口号
    port = 12345
    # 将套接字与本地主机和端口绑定
    server_socket.bind((host, port))
    # 设置监听最大连接数
    server_socket.listen(5)

    print("服务器地址:%s" % str(server_socket.getsockname()))

    # 调用其他函数发送数据
    send_data(server_socket, {"message": "Hello, client!"})

    # 关闭服务器套接字
    server_socket.close()


if __name__ == "__main__":
    main()
