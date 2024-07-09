import cv2
import os
import random

def extract_random_frame(video_path, save_path):
    # 打开视频文件
    video = cv2.VideoCapture(video_path)
    if not video.isOpened():
        print("无法打开视频文件")
        return

    # 获取视频帧数
    frame_count = int(video.get(cv2.CAP_PROP_FRAME_COUNT))

    # 随机选择一帧
    random_frame_idx = random.randint(0, frame_count - 1)
    video.set(cv2.CAP_PROP_POS_FRAMES, random_frame_idx)

    # 读取随机帧
    ret, frame = video.read()
    if not ret:
        print("无法读取视频帧")
        return

    # 保存图片
    cv2.imwrite(save_path, frame)
    print(f"已保存随机帧到{save_path}")

# 使用示例
video_path = "G:\\pycharmPro\\PyCharm 2023.1.3\\jbr\\bin\\yolo\\controller\\runs\\detect\\predict257\\20240330_192702.avi"  # 替换为您的视频文件路径
save_path = "./video2/vid.jpg"  # 替换为您希望保存图片的路径
extract_random_frame(video_path, save_path)
