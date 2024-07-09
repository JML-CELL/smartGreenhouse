import oss2
print("1")
# 配置阿里云OSS账号的AccessKey ID和AccessKey Secret
ACCESS_KEY_ID = 'LTAI5tQy8uwY3qaD2Yhp3cNR'
ACCESS_KEY_SECRET = 'AJbUzvpgFfkJjfbhxPIo4AKq45m22N'
# 配置Bucket名称和Endpoint
BUCKET_NAME = 'smart-green-home'
ENDPOINT = 'https://oss-cn-hangzhou.aliyuncs.com'
print(1)
# 初始化认证信息和Bucket对象
auth = oss2.Auth(ACCESS_KEY_ID, ACCESS_KEY_SECRET)
bucket = oss2.Bucket(auth, ENDPOINT, BUCKET_NAME)

local_file_path = 'G:\\pycharmPro\\PyCharm 2023.1.3\\jbr\\bin\\yolo\\controller\\video\\20240327_194611.avi'
bucket.put_object("video/video.avi",local_file_path)