import random

import cv2


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
    video.set(cv2.CAP_PROP_POS_FRAMES, frame_count/2)

    # 读取随机帧
    ret, frame = video.read()
    if not ret:
        print("无法读取视频帧")
        return

    # 保存图片
    cv2.imwrite(save_path, frame)
    print(f"已保存随机帧到{save_path}")



extract_random_frame("G:\\pycharmPro\\PyCharm 2023.1.3\\jbr\\bin\\yolo\\controller\\runs\\detect\\predict404\\20240409_101312.avi","G:\\pycharmPro\\PyCharm 2023.1.3\\jbr\\bin\\yolo\\controller\\video2\\vid.jpg")