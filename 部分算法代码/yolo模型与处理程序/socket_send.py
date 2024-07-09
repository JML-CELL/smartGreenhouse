import socket
import json
import time

import os
import time





def process_video(video_path):
    """
    处理视频文件的函数。这里只是一个示例，你需要根据实际需求来编写这个函数。

    参数：
        video_path (str): 视频文件的路径。
    """
    print(f"处理视频文件：{video_path}")
    # 在这里执行你的逻辑
    # 例如，可以调用 OpenCV 或其他视频处理库来处理视频文件


# 调用函数，传入文件夹路径
# folder_path = "/path/to/your/folder"
# process_video_files(folder_path)


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


def is_condition_satisfied():
    """
    判断条件是否满足。

    返回值：
        bool: 如果条件满足返回True，否则返回False。
    """
    # 这里编写判断条件的逻辑
    return True  # 示例条件，假设始终为True


def main():
    # 定义目标主机和端口
    target_host = "172.20.10.2"
    target_port = 12347

    # 定义要发送的消息
    message = {
        "Json": json.dumps([
            {
                "class": "MediumRipe",
                "confidence": 0.953207
            },
            {
                "class": "Unripe",
                "confidence": 0.946769
            },
            {
                "class": "MediumRipe",
                "confidence": 0.678864
            },
            {
                "class": "Unripe",
                "confidence": 0.625469
            },
            {
                "class": "Unripe",
                "confidence": 0.410259
            }
        ]),
        "img": "OK"

        # 其他字段...
    }

    # 循环检查条件并发送消息


    while True:
        if is_condition_satisfied():
            # 条件满足时发送消息
            send_message(target_host, target_port, message)

        # 等待一段时间后再次检查条件
        time.sleep(10)  # 每10秒检查一次


if __name__ == "__main__":
    main()
