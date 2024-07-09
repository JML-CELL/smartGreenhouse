import base64
import os

# 确保已存在包含 base64 编码的视频内容的 txt 文件
txt_file_path = 'content_base64.txt'
if not os.path.exists(txt_file_path):
    print(f"文件不存在：{txt_file_path}")
    exit()

# 从 txt 文件中读取 base64 编码的视频数据
with open(txt_file_path, 'r') as f:
    base64_data = f.read()

# 将 base64 编码的数据解码为二进制数据
binary_data = base64.b64decode(base64_data)

# 创建视频文件的输出路径
output_video_path = 'output_video.avi'

# 将二进制数据写入新的视频文件
with open(output_video_path, 'wb') as f:
    f.write(binary_data)

print(f"视频已保存到：{output_video_path}")
