import json
import os

import torch
from ultralytics import YOLO


def strawberry_video_ripe(source, save_dir="../runs/tomato"):
    # 初始化YOLO模型
    model = YOLO("../model/strawberry-ripe-best.pt")

    # 将设备设置为CPU
    # model.to(torch.device('cpu'))

    # 运行YOLO模型预测
    results = model(source=source, save=True, save_txt=True, save_conf=True, save_dir=save_dir, conf=0.4)

    # 获取labels文件夹路径
    labels_dir = os.path.join(results[0].save_dir, 'labels')

    # 获取labels文件夹中的txt文件列表
    label_files = [f for f in os.listdir(labels_dir) if f.endswith('.txt')]

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

    # 将JSON字符串写入到文件中
    output_file_path = os.path.abspath(os.path.join(results[0].save_dir, "results.json"))
    img_file_path = os.path.abspath(os.path.join(results[0].save_dir, "video.avi"))
    with open(output_file_path, 'w') as json_file:
        json_file.write(output_json)

    # 返回最后两个保存路径的JSON格式结果
    return output_file_path, img_file_path


def strawberry_video_disease(source, save_dir="../runs/tomato"):
    # 初始化YOLO模型
    model = YOLO("../model/strawberry-disease-best.pt")

    # 将设备设置为CPU
    # model.to(torch.device('cpu'))

    # 运行YOLO模型预测
    results = model(source=source, save=True, save_txt=True, save_conf=True, save_dir=save_dir, conf=0.6)

    # 获取labels文件夹路径
    labels_dir = os.path.join(results[0].save_dir, 'labels')

    # 获取labels文件夹中的txt文件列表
    label_files = [f for f in os.listdir(labels_dir) if f.endswith('.txt')]

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

    # 将JSON字符串写入到文件中
    output_file_path = os.path.abspath(os.path.join(results[0].save_dir, "results.json"))
    img_file_path = os.path.abspath(os.path.join(results[0].save_dir, "video.avi"))
    with open(output_file_path, 'w') as json_file:
        json_file.write(output_json)

    # 返回最后两个保存路径的JSON格式结果
    return output_file_path, img_file_path


def strawberry_ripe(source, save_dir="../runs/tomato"):
    # 初始化YOLO模型
    model = YOLO("../model/strawberry-ripe-best.pt")

    # 将设备设置为CPU
    # model.to(torch.device('cpu'))

    # 运行YOLO模型预测
    results = model(source=source, save=True, save_txt=True, save_conf=True, save_dir=save_dir)

    # 获取labels文件夹路径
    labels_dir = os.path.join(results[0].save_dir, 'labels')

    # 获取labels文件夹中的txt文件列表
    label_files = [f for f in os.listdir(labels_dir) if f.endswith('.txt')]

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

    # 将JSON字符串写入到文件中
    output_file_path = os.path.abspath(os.path.join(results[0].save_dir, "results.json"))
    img_file_path = os.path.abspath(os.path.join(results[0].save_dir, "image0.jpg"))
    with open(output_file_path, 'w') as json_file:
        json_file.write(output_json)

    # 返回最后两个保存路径的JSON格式结果
    return output_file_path, img_file_path


def strawberry_disease(source, save_dir="../runs/tomato"):
    # 初始化YOLO模型
    model = YOLO("../model/strawberry-disease-best.pt")

    # 将设备设置为CPU
    # model.to(torch.device('cpu'))

    # 运行YOLO模型预测
    results = model(source=source, save=True, save_txt=True, save_conf=True, save_dir=save_dir)

    # 获取labels文件夹路径
    labels_dir = os.path.join(results[0].save_dir, 'labels')

    # 获取labels文件夹中的txt文件列表
    label_files = [f for f in os.listdir(labels_dir) if f.endswith('.txt')]

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

    # 将JSON字符串写入到文件中
    output_file_path = os.path.abspath(os.path.join(results[0].save_dir, "results.json"))
    img_file_path = os.path.abspath(os.path.join(results[0].save_dir, "image0.jpg"))
    with open(output_file_path, 'w') as json_file:
        json_file.write(output_json)

    # 返回最后两个保存路径的JSON格式结果
    return output_file_path, img_file_path