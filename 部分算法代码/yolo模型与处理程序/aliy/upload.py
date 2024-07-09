import os
import urllib

import oss2


def upload_file_to_oss(local_file_path):
    # 配置阿里云OSS账号的AccessKey ID和AccessKey Secret
    ACCESS_KEY_ID = 'LTAI5tQy8uwY3qaD2Yhp3cNR'
    ACCESS_KEY_SECRET = 'AJbUzvpgFfkJjfbhxPIo4AKq45m22N'
    # 配置Bucket名称和Endpoint
    BUCKET_NAME = 'smart-green-home'
    ENDPOINT = 'https://oss-cn-hangzhou.aliyuncs.com'

    # 初始化认证信息和Bucket对象
    auth = oss2.Auth(ACCESS_KEY_ID, ACCESS_KEY_SECRET)
    bucket = oss2.Bucket(auth, ENDPOINT, BUCKET_NAME)

    """
    将本地文件上传到阿里云OSS，并保持原有文件名。

    :param local_file_path: 本地文件的路径。
    """
    try:
        # 提取本地文件的文件名
        # file_name ='/'
        file_name = os.path.basename(local_file_path)
        print("file_name",file_name)
        # 以二进制读取方式打开文件
        with open(local_file_path, 'rb') as fileobj:
            # 直接上传文件，并保持原有文件名
            decoded_file_name = urllib.parse.unquote("/")
            bucket.put_object("video/"+file_name, fileobj)
        print(f"文件 {local_file_path} 上传到 OSS 成功，Object名称为：{file_name}。")
    except Exception as e:
        print(f"文件上传失败: {e}")

# 如果这个文件是被直接运行，而不是被导入，则运行上传函数
if __name__ == "__main__":
    # 使用示例
    local_file_path = 'G:\\pycharmPro\\PyCharm 2023.1.3\\jbr\\bin\\yolo\\controller\\video\\20240330_120602.mp4'
    upload_file_to_oss(local_file_path)
