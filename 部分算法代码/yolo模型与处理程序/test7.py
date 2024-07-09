import base64
import json
from datetime import datetime

import find_video


def read2byte(path):
    '''
    图片转二进制
    path：图片路径
    byte_data：二进制数据
    '''
    with open(path, "rb") as f:
        byte_data = f.read()
    return byte_data


# 假设这是您接收到的 JSON 数据
json_data = '{"time":"20240327_194555"}'

# 解析 JSON 数据
data = json.loads(json_data)

# 检查第一个属性名是否为 'content'
first_key = list(data.keys())[0]
time = data['time']
# 初始化
target_datetime = datetime.strptime(time, "%Y%m%d_%H%M%S")
video_folder = "video"

video_path = find_video.find_video_within_range(target_datetime, video_folder)

# # 处理成流
# f = read2byte(video_path)  # 转二进制
# 读取视频文件并转换成 base64 编码的字符串
if video_path:
    video_byte_data = read2byte(video_path)
    content_base64 = base64.b64encode(video_byte_data).decode()

    # 将 base64 编码的内容保存到 txt 文件中
    with open("content_base64.txt", "w") as f:
        f.write(content_base64)

    print("内容已保存到 content_base64.txt 文件中")
else:
    print("未找到对应的视频文件")
print("结果", content_base64)
