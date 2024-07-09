from ultralytics import YOLO
from PIL import Image
import cv2

model = YOLO("./best.pt")
# 接受各种格式 - 图像/目录/路径/URL/视频/PIL/ndarray。0表示网络摄像头
# results = model.predict(source="0",show=True)
# results = model.predict(source="folder", show=True) # 显示预测结果。接受所有YOLO预测参数

# 使用PIL库
# im1 = Image.open("bus.jpg")
# results = model.predict(source=im1, save=True)  # 保存绘制的图像

# 使用ndarray
im2 = cv2.imread("./tomatounripe.jpeg")
results = model.predict(source=im2, save=True)  # 将预测结果保存为标签

print(results)
# 使用PIL/ndarray列表
# results = model.predict(source=[im1, im2])