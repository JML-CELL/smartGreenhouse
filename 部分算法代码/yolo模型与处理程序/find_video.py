from datetime import datetime, timedelta
import os


def find_video_within_range(target_time, folder, range_seconds=60):
    # 计算时间范围
    start_time = target_time
    end_time = target_time + timedelta(seconds=range_seconds)

    # 将目标时间转换为字符串
    start_time_formatted = start_time.strftime("%Y%m%d_%H%M%S")
    end_time_formatted = end_time.strftime("%Y%m%d_%H%M%S")

    # 遍历文件夹中的所有文件
    for filename in os.listdir(folder):
        # 检查文件名中的时间是否在指定范围内
        if start_time_formatted <= filename <= end_time_formatted:
            return os.path.join(folder, filename)
    return "未找到指定时间范围内的录像"


# 使用示例
target_datetime = datetime.strptime("20240327_194550", "%Y%m%d_%H%M%S")
video_folder = "video"
video_path = find_video_within_range(target_datetime, video_folder)
# print(video_path)
