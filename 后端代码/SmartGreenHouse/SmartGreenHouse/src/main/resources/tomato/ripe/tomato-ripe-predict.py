# -*- coding: UTF-8 -*-
import json
import os

from ultralytics import YOLO
from PIL import Image
import cv2


model = YOLO("C:\\Users\\86189\\Desktop\\智能温室\\SmartGreenHouse\\src\\main\\resources\\tomato\\ripe\\best.pt")

# im2 = cv2.imread("../../datasets/tomato/test/images/IMG20230812125517_jpg.rf.f10bbd41d22f8a2a98b60fabc12f15c4.jpg")
# im2 = cv2.imread("src/main/resources/tomato/IMG20230812124054_jpg.rf.1ad106488bcda7aa001a0eb7f15d4f75.jpg")
results = model(source="http://88kh323541.vicp.fun/mjpeg/1", save=True,save_txt=True,save_conf=True, save_dir="./runs")


print(results[0].save_dir)

labels_dir = os.path.join(results[0].save_dir, 'labels')


label_files = [f for f in os.listdir(labels_dir) if f.endswith('.txt')]


class_names = results[0].names

all_results = []


for label_file in os.listdir(labels_dir):
    label_file_path = os.path.join(labels_dir, label_file)
    with open(label_file_path, 'r') as f:
        lines = f.readlines()
        for line in lines:
            class_index, x_center, y_center, width, height, confidence = map(float, line.strip().split())
            class_name = class_names[int(class_index)]
            result = {"class": class_name, "confidence": confidence}
            all_results.append(result)


output_json = json.dumps(all_results, indent=4)

output_file_path = os.path.abspath(os.path.join(results[0].save_dir, "results.json"))
img_file_path = os.path.abspath(os.path.join(results[0].save_dir, "image0.jpg"))
with open(output_file_path, 'w') as json_file:
    json_file.write(output_json)

print({output_file_path})
print({img_file_path})