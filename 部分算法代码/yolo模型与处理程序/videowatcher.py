import json
import socket
import threading

import cv2
import time
import datetime
import os
from aliy import upload
from controller.aliy.socket_sent import send_data


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

def save_video(stream_url, save_folder, segment_duration):
    # 初始化视频写入器为None
    out = None

    # 打开视频流
    cap = cv2.VideoCapture(stream_url)
    if not cap.isOpened():
        print("无法打开视频流")
        return

    try:
        # 确保保存文件夹存在
        if not os.path.exists(save_folder):
            os.makedirs(save_folder)

        # 初始化开始时间
        start_time = time.time()
        segment_count = 1

        while True:
            # 读取帧
            ret, frame = cap.read()
            if not ret:
                print("无法从流中读取帧，稍等片刻...")
                time.sleep(1)  # 等待一段时间再尝试
                continue

            # 显示帧
            cv2.imshow('Video Stream', frame)

            # 检查是否需要开始一个新的视频片段
            current_time = time.time()
            elapsed_time = current_time - start_time
            if elapsed_time > segment_duration:
                # 关闭当前的视频写入器
                if out is not None:
                    out.release()
                    # 上传至服务器
                    upload.upload_file_to_oss(save_path)
                    print("success")
                    pa = "https://smart-green-home.oss-cn-hangzhou.aliyuncs.com/";

                    res = {
                        "Json": '1',
                        "img": '1',
                        "path": pa + save_path
                    }

                    # 获取本目标主机名称
                    target_host = "172.16.56.148"
                    # 设置端口号
                    target_port = 12347
                    # 将套接字与本地主机和端口绑定
                    send_message(target_host, target_port, res)
                # 创建新的保存路径
                current_datetime = datetime.datetime.now().strftime("%Y%m%d_%H%M%S")
                save_path = f"{save_folder}/{current_datetime}.mp4"
                # 创建新的视频写入器
                frame_width = frame.shape[1]
                frame_height = frame.shape[0]
                fourcc = cv2.VideoWriter_fourcc(*'XVID')
                out = cv2.VideoWriter(save_path, fourcc, 5, (frame_width, frame_height))

                # 增加片段计数
                segment_count += 1

                # 更新开始时间
                start_time = current_time
                print(f"开始新的视频片段：{save_path}")

            # 写入当前帧到视频
            if out is not None:
                out.write(frame)

            # 按 'q' 退出循环
            if cv2.waitKey(1) & 0xFF == ord('q'):
                break
    except Exception as e:
        print(f"发生异常：{e}")
    finally:
        # 释放资源
        if out is not None:
            out.release()
        cap.release()
        cv2.destroyAllWindows()
# def save_video(stream_url, save_folder, segment_duration):
#     # 初始化视频写入器为None
#     out = None
#
#     # 打开视频流
#     cap = cv2.VideoCapture(stream_url)
#     if not cap.isOpened():
#         print("无法打开视频流")
#         return
#
#     try:
#         # 获取视频参数
#         fps = int(cap.get(cv2.CAP_PROP_FPS))
#         frame_width = int(cap.get(3))
#         frame_height = int(cap.get(4))
#         fourcc = cv2.VideoWriter_fourcc(*'XVID')
#
#         # 确保保存文件夹存在
#         if not os.path.exists(save_folder):
#             os.makedirs(save_folder)
#
#         # 初始化开始时间
#         start_time = time.time()
#         segment_count = 1
#
#         while True:
#             # 读取帧
#             ret, frame = cap.read()
#             if not ret:
#                 print("无法从流中读取帧，稍等片刻...")
#                 time.sleep(1)  # 等待一段时间再尝试
#                 continue
#
#             # 显示帧
#             cv2.imshow('Video Stream', frame)
#
#             # 检查是否需要开始一个新的视频片段
#             current_time = time.time()
#             elapsed_time = current_time - start_time
#             if elapsed_time > segment_duration:
#                 # 关闭当前的视频写入器
#                 if out is not None:
#                     out.release()
#                     # 上传至服务器
#                     upload.upload_file_to_oss(save_path)
#                     print("success")
#                     pa = "https://smart-green-home.oss-cn-hangzhou.aliyuncs.com/";
#
#                     res = {
#                         "Json": '1',
#                         "img": '1',
#                         "path": pa + save_path
#                     }
#
#                     # 获取本目标主机名称
#                     target_host = "192.168.9.164"
#                     # 设置端口号
#                     target_port = 12347
#                     # 将套接字与本地主机和端口绑定
#                     send_message(target_host, target_port, res)
#                 # 创建新的保存路径
#                 current_datetime = datetime.datetime.now().strftime("%Y%m%d_%H%M%S")
#                 save_path = f"{save_folder}/{current_datetime}.mp4"
#                 # 创建新的视频写入器
#                 out = cv2.VideoWriter(save_path, fourcc, fps, (frame_width, frame_height))
#
#                 # 增加片段计数
#                 segment_count += 1
#
#                 # 更新开始时间
#                 start_time = current_time
#                 print(f"开始新的视频片段：{save_path}")
#
#             # 写入当前帧到视频
#             if out is not None:
#                 out.write(frame)
#
#             # 按 'q' 退出循环
#             if cv2.waitKey(1) & 0xFF == ord('q'):
#                 break
#     except Exception as e:
#         print(f"发生异常：{e}")
#     finally:
#         # 释放资源
#         if out is not None:
#             out.release()
#         cap.release()
#         cv2.destroyAllWindows()



if __name__ == '__main__':
    stream_url = "http://172.16.56.211/mjpeg/1"
    # stream_url = "https://assets.how-living.com/2015/06/shutterstock_206088451_resized.jpg"
    segment_duration = 10  # 每个视频片段的时长，单位为秒
    save_folder = "video"  # 视频保存的文件夹

    # process_video_files(save_folder) # 这个也要一直执行
    save_video(stream_url, save_folder, segment_duration) # 这个也要一直执行
