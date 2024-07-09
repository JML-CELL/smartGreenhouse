import sys


# 定义蒸腾速率计算函数
def calculate_transpiration(temperature, relative_humidity, light_intensity, k=1):
    return k * (temperature - (relative_humidity / 100)) * light_intensity


# 从命令行参数中获取数据
def get_data_from_command_line():
    temperature = float(sys.argv[1])  # 温度（摄氏度）
    relative_humidity = float(sys.argv[2])  # 空气相对湿度（百分比）
    light_intensity = float(sys.argv[3])  # 光强度（瓦特/平方米）
    return temperature, relative_humidity, light_intensity


# 从文件中读取数据
def get_data_from_file(file_path):
    with open(file_path, 'r') as file:
        lines = file.readlines()
        temperature = float(lines[0].strip())  # 温度（摄氏度）
        relative_humidity = float(lines[1].strip())  # 空气相对湿度（百分比）
        light_intensity = float(lines[2].strip())  # 光强度（瓦特/平方米）
    return temperature, relative_humidity, light_intensity


def main():
    if len(sys.argv) == 4:  # 通过命令行参数传递数据
        temperature, relative_humidity, light_intensity = get_data_from_command_line()
    elif len(sys.argv) == 2:  # 通过文件输入传递数据
        temperature, relative_humidity, light_intensity = get_data_from_file(sys.argv[1])
    else:
        print("用法（命令行方式）: python transpiration_model.py <temperature> <relative_humidity> <light_intensity>")
        print("       或者")
        print("       python transpiration_model.py <input_file>")
        return

    # 使用模型计算蒸腾速率
    transpiration_rate = calculate_transpiration(temperature, relative_humidity, light_intensity)

    # 打印结果
    print("估计蒸腾速率:", transpiration_rate)


if __name__ == "__main__":
    main()
