import base64
import json
import os
import random

import cv2

import socket
import time

from controller.aliy.socket_sent import send_data
from controller.check.rose_check import rose_video_disease
from controller.check.strawberry_check import strawberry_disease, strawberry_video_disease
from controller.check.tomato_check import tomato_disease, tomato_video_disease
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

def extract_random_frame(video_path, save_path):
    # 打开视频文件
    video = cv2.VideoCapture(video_path)
    if not video.isOpened():
        print("无法打开视频文件")
        return

    # 获取视频帧数
    frame_count = int(video.get(cv2.CAP_PROP_FRAME_COUNT))

    # 随机选择一帧
    random_frame_idx = random.randint(1, frame_count - 1)
    video.set(cv2.CAP_PROP_POS_FRAMES, frame_count-2)

    # 读取随机帧
    ret, frame = video.read()
    if not ret:
        print("无法读取视频帧")
        return

    # 保存图片
    cv2.imwrite(save_path, frame)
    print(f"已保存随机帧到{save_path}")

def read2byte(path):
    '''
    图片转二进制
    path：图片路径
    byte_data：二进制数据
    '''
    with open(path, "rb") as f:
        byte_data = f.read()
    return byte_data


def process_video_files(folder_path):
    """
    检查给定文件夹中是否存在视频文件，如果存在，则遍历读取文件并执行特定函数。

    参数：
        folder_path (str): 文件夹路径。
    """
    while True:
        if os.path.exists(folder_path):
            # 检查文件夹是否存在视频文件
            video_files = [f for f in os.listdir(folder_path) if f.endswith(('.mp4', '.avi', '.mov'))]

            if video_files:
                # 存在视频文件，遍历读取文件并执行函数
                for video_file in video_files:
                    video_path = os.path.join(folder_path, video_file)
                    # 执行你的函数
                    json_path, img_path = process_video(video_path)
                    if img_path and json_path:

                        extract_random_frame(img_path,"./video2/vid.jpg")
                        byte = read2byte("./video2/vid.jpg")
                        # 将二进制数据转换成 base64 编码的字符串
                        content_base64 = base64.b64encode(byte).decode()
                        # 在处理完后删除图片
                        os.remove(img_path)
                        # 读取json文件
                        with open(json_path, 'r') as file:
                            content = json.load(file)
                        content = json.dumps(content)
                        res = {
                            'Json': content,
                            'img': content_base64
                        }
                        # 定义目标主机和端口
                        target_host = "192.168.9.164"
                        target_port = 12347
                        # sendmsg = json.dumps(res)
                        # 创建服务器套接字
                        send_message(target_host, target_port, res)
                        # send_data(server_socket, res)
                        return

        else:
            print(f"文件夹 '{folder_path}' 不存在。")

        # 等待一段时间后重新检查文件夹
        time.sleep(10)  # 每60秒检查一次


def process_video(video_path):
    """
    处理视频文件的函数。这里只是一个示例，你需要根据实际需求来编写这个函数。

    参数：
        video_path (str): 视频文件的路径。
    """
    # 定义处理函数列表
    processing_functions = [
        # strawberry_video_disease,
        tomato_video_disease
        # rose_video_disease
    ]

    # 依次执行每个处理函数
    for func in processing_functions:
        json_report_path, img_path = func(video_path)
        # 检查JSON报告是否非空
        if json_report_path:
            with open(json_report_path, 'r') as file:
                report = json.load(file)
            if report and report != []:  # 如果JSON报告非空且不为空列表
                print(f"找到非空JSON报告：{json_report_path}")
                return json_report_path, video_path

    print(f"处理视频文件：{video_path}")
    return None, None  # 返回默认值

# 调用函数，传入文件夹路径
folder_path = "./video"
process_video_files(folder_path)
