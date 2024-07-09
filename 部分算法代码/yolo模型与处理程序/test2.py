import cv2
import json
import os
import time
from flask import Flask, request, jsonify, Response
from ultralytics import YOLO

app = Flask(__name__)

# 设置保存视频的时间为20秒
VIDEO_DURATION_SECONDS = 20


def generate_frames(source):
    # 通过cv2中的类获取视频流操作对象cap
    cap = cv2.VideoCapture(source)
    fps = cap.get(cv2.CAP_PROP_FPS)
    size = (int(cap.get(cv2.CAP_PROP_FRAME_WIDTH)), int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT)))

    # 定义编码格式
    fourcc = cv2.VideoWriter_fourcc(*'XVID')

    start_time = time.time()
    elapsed_time = 0
    # 循环使用cv2的read()方法读取视频帧，直到超过VIDEO_DURATION_SECONDS
    while elapsed_time < VIDEO_DURATION_SECONDS:
        ret, frame = cap.read()
        if not ret:
            break
        # 将帧转换为JPEG格式
        ret, buffer = cv2.imencode('.jpg', frame)
        frame_bytes = buffer.tobytes()
        yield (b'--frame\r\n'
               b'Content-Type: image/jpeg\r\n\r\n' + frame_bytes + b'\r\n')
        elapsed_time = time.time() - start_time

    # 释放视频流
    cap.release()


def tomato_disease(source):
    # 初始化YOLO模型
    model = YOLO("./model/tomato-ripe-best.pt")

    # 运行YOLO模型预测
    results = model(source=source, save=True, save_txt=True)

    # 获取labels文件夹路径
    labels_dir = os.path.join(results[0].save_dir, 'labels')

    # 使用results[0].names做匹配
    class_names = results[0].names

    # 存储所有结果的列表
    all_results = []

    # 遍历每个txt文件
    for label_file in os.listdir(labels_dir):
        label_file_path = os.path.join(labels_dir, label_file)
        with open(label_file_path, 'r') as f:
            lines = f.readlines()
            for line in lines:
                class_index, x_center, y_center, width, height, confidence = map(float, line.strip().split())
                class_name = class_names[int(class_index)]
                result = {"class": class_name, "confidence": confidence}
                all_results.append(result)

    # 将结果输出为JSON格式
    output_json = json.dumps(all_results, indent=4)

    return output_json


# 处理视频流请求
@app.route('/yolo/tomato/ripe', methods=['POST'])
def tomato_disease_yolo():
    data = request.json
    source = data.get('source')

    # 对视频进行分析
    predict_report = tomato_disease(source)

    # 返回结果
    return Response(generate_frames(source), mimetype='multipart/x-mixed-replace; boundary=frame')


if __name__ == '__main__':
    app.run(debug=True)
