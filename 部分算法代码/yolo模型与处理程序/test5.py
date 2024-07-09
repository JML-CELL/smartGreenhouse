def read2byte(path):
    '''
    图片转二进制
    path：图片路径
    byte_data：二进制数据
    '''
    with open(path, "rb") as f:
        byte_data = f.read()
    return byte_data


f = read2byte("G:\\pycharmPro\\PyCharm 2023.1.3\\jbr\\bin\\yolo\\datasets\\tomato\\test\\images\\IMG20230812122225_jpg.rf.4f2d95688aaf3e96352fb3f58bb3091a.jpg")
print(f)