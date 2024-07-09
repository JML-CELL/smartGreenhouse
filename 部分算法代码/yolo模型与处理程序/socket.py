import base64
import os

import cv2

import socket
import threading
import json

import test
from controller.check.strawberry_check import strawberry_ripe, strawberry_disease
from controller.check.tomato_check import tomato_ripe, tomato_disease
from controller.check.week_check import weed_check


def get_first_medium_ripe_class(file_path):

    # 打开JSON文件并读取内容
    with open(file_path, 'r') as file:
        data = json.load(file)
        # 检查是否有数据
        if data:
            # 返回第一个class的值
            return data[0]['class']


# def check_grow():
#


def process_video_frame(video_path, frame_index=0, save_image=True, delete_image_after_processing=False, type1=1,
                        type2=2):
    """
    从视频中提取指定帧，并将其保存为图片。然后调用tomato_ripe函数处理图片。

    :param type:
    :param video_path: 视频文件的路径。
    :param frame_index: 要提取的帧的索引号，默认为0（第一帧）。
    :param save_image: 是否保存提取的帧为图片，默认为True。
    :param delete_image_after_processing: 在处理完成后是否删除图片，默认为False。
    """

    # 创建一个VideoCapture对象并读取视频文件
    cap = cv2.VideoCapture(video_path)

    # 检查视频是否成功打开
    if not cap.isOpened():
        print("Error: Could not open video.")
        return

    # 跳转到指定的帧
    cap.set(cv2.CAP_PROP_POS_FRAMES, frame_index)

    # 读取视频的一帧
    ret, frame = cap.read()

    # 如果正确读取帧，ret为True
    if ret:
        if save_image:
            # 将帧保存为图片
            img_path = "./runs/tomato/image0.jpg"
            cv2.imwrite(img_path, frame)
            if type1 == 1:
                if type2 == 1:
                    print("11")
                    # 调用您的函数处理图片
                    out, path = tomato_ripe(img_path)
                if type2 == 2:
                    print("12")
                    # 调用您的函数处理图片
                    out, path = tomato_disease(img_path)
            elif type1 == 2:
                if type2 == 1:
                    # 调用您的函数处理图片
                    out, path = strawberry_ripe(img_path)
                if type2 == 2:
                    # 调用您的函数处理图片
                    out, path = strawberry_disease(img_path)
            elif type1 == 4:
                if type2 == 1:
                    # 调用您的函数处理图片
                    out, path = weed_check(img_path)
                if type2 == 2:
                    # 调用您的函数处理图片
                    out, path = weed_check(img_path)

            # 在处理完后删除图片
            os.remove(img_path)

    # 释放VideoCapture对象
    cap.release()

    return out, path


def read2byte(path):
    '''
    图片转二进制
    path：图片路径
    byte_data：二进制数据
    '''
    with open(path, "rb") as f:
        byte_data = f.read()
    return byte_data


class ServerThreading(threading.Thread):

    def __init__(self, clientsocket, recvsize=1024 * 1024, encoding="utf-8"):
        threading.Thread.__init__(self)
        self._socket = clientsocket
        self._recvsize = recvsize
        self._encoding = encoding

    def run(self):
        flow = "http://88kh323541.vicp.fun/mjpeg/1"
        print("开启线程.....")
        try:
            # 接受数据
            msg = ''
            while True:
                rec = self._socket.recv(self._recvsize)

                # 解码成字符串
                msg += rec.decode(self._encoding)
                print("解码后数据：")
                print(msg)

                # 文本接受是否完毕，因为python socket不能自己判断接收数据是否完毕
                # 所以需要自定义协议标志数据接受完毕
                if msg.strip().endswith('over'):
                    msg = msg[:-4]
                    break

            # f = read2byte(
            #     "G:\\pycharmPro\\PyCharm 2023.1.3\\jbr\\bin\\yolo\\controller\\runs\\detect\\predict2\\IMG20230812124054_jpg.rf.1ad106488bcda7aa001a0eb7f15d4f75.jpg")

            # 将字符串解析成JSON格式数据
            re = json.loads(msg)
            print("解析成JSON数据：")
            print(re)

            # # 将 base64 编码的字符串添加到 JSON 对象中
            # re["img"] = content_base64

            thing = re["content"]
            if thing == 11:
                print("11")
                out,path = process_video_frame(flow,type1=1,type2=1)
                f = read2byte(path)
                # 将二进制数据转换成 base64 编码的字符串
                content_base64 = base64.b64encode(f).decode()
                message = {
                    "res": get_first_medium_ripe_class(out),
                    "img": content_base64,
                    "path": ''
                }
                # 将 JSON 对象转换成字符串
                sendmsg = json.dumps(message)

                # 发送字符串数据给Java端
                self._socket.send(sendmsg.encode(self._encoding))
            elif thing == 12:
                print("12")
                out,path = process_video_frame(flow,type1=1,type2=2)
                f = read2byte(path)
                # 将二进制数据转换成 base64 编码的字符串
                content_base64 = base64.b64encode(f).decode()
                message = {
                    "res": get_first_medium_ripe_class(out),
                    "img": content_base64,
                    "path": ''
                }
                # 将 JSON 对象转换成字符串
                sendmsg = json.dumps(message)
                # 发送字符串数据给Java端
                self._socket.send(sendmsg.encode(self._encoding))
            elif thing == 21:
                out,path = process_video_frame(flow)
                f = read2byte(path)
                # 将二进制数据转换成 base64 编码的字符串
                content_base64 = base64.b64encode(f).decode()
                message = {
                    "res": get_first_medium_ripe_class(out),
                    "img": content_base64,
                    "path": ''
                }
                # 将 JSON 对象转换成字符串
                sendmsg = json.dumps(message)
                # 发送字符串数据给Java端
                self._socket.send(sendmsg.encode(self._encoding))
            elif thing == 22:
                out,path = process_video_frame(flow)
                f = read2byte(path)
                # 将二进制数据转换成 base64 编码的字符串
                content_base64 = base64.b64encode(f).decode()
                message = {
                    "res": get_first_medium_ripe_class(out),
                    "img": "flow",
                    "path": ''
                }
                # 将 JSON 对象转换成字符串
                sendmsg = json.dumps(message)
                # 发送字符串数据给Java端
                self._socket.send(sendmsg.encode(self._encoding))
            elif thing == 32:
                # out,path = process_video_frame(flow)
                # f = read2byte(path)
                # 将二进制数据转换成 base64 编码的字符串
                content_base64 = base64.b64encode(f).decode()
                message = {
                    "res": "rose-disease",
                    "img": "flow",
                    "path": ''
                }
                # 将 JSON 对象转换成字符串
                sendmsg = json.dumps(message)
                # 发送字符串数据给Java端
                self._socket.send(sendmsg.encode(self._encoding))
            elif thing == 41:
                out,path = process_video_frame(flow,type1=4,type2=2)
                f = read2byte(path)
                # 将二进制数据转换成 base64 编码的字符串
                content_base64 = base64.b64encode(f).decode()
                message = {
                    "res": get_first_medium_ripe_class(out),
                    "img": content_base64,
                    "path": ''
                }
                # 将 JSON 对象转换成字符串
                sendmsg = json.dumps(message)
                # 发送字符串数据给Java端
                self._socket.send(sendmsg.encode(self._encoding))




        except Exception as identifier:
            print(identifier)
        finally:
            self._socket.close()
            print("任务结束.....")


def main():
    # 创建服务器套接字
    serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # 获取本地主机名称
    host = "192.168.10.87"

    # 设置端口号
    port = 12345

    # 将套接字与本地主机和端口绑定
    serversocket.bind((host, port))

    # 设置监听最大连接数
    serversocket.listen(5)

    # 获取本地服务器的连接信息
    myaddr = serversocket.getsockname()

    print("服务器地址:%s" % str(myaddr))

    # 循环等待接受客户端信息
    while True:
        # 获取一个客户端连接
        clientsocket, addr = serversocket.accept()
        print("连接地址:%s" % str(addr))
        try:
            # 为每一个请求开启一个处理线程
            t = ServerThreading(clientsocket)
            t.start()
        except Exception as identifier:
            print(identifier)
    serversocket.close()


if __name__ == "__main__":
    main()
