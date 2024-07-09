import sys
import pandas as pd
import numpy as np


# 定义Farquhar模型函数
def farquhar_model(I: float, C_c: float, alpha: float = 0.5, beta: float = 0.3,
                   theta: float = 0.2, Gamma_star: float = 0.05, K_C: float = 0.1,
                   K_O: float = 0.01, R_d: float = 0.01) -> float:
    """
    计算净光合速率的Farquhar模型

    参数：
    I (float): 光强度（瓦特/平方米）
    C_c (float): CO2浓度（微摩尔/千帕）
    alpha (float): 用于模型拟合的参数，默认为0.5
    beta (float): 用于模型拟合的参数，默认为0.3
    theta (float): 用于模型拟合的参数，默认为0.2
    Gamma_star (float): 用于模型拟合的参数，默认为0.05
    K_C (float): 用于模型拟合的参数，默认为0.1
    K_O (float): 用于模型拟合的参数，默认为0.01
    R_d (float): 叶片暗呼吸速率，默认为0.01

    返回：
    float: 净光合速率（微摩尔/平方米/秒）
    """
    # 计算光合速率与光强度I的关系
    A_I = (alpha * I - beta * (I - R_d) - np.sqrt(
        (alpha * I - beta * (I - R_d)) ** 2 - 4 * theta * alpha * beta * (I - R_d))) / (2 * theta)
    # 计算光合速率与CO2浓度C_c的关系
    A_C_c = (C_c - Gamma_star) / (1 + (C_c + K_C * (1 + C_c / K_O)) / K_C)
    # 返回两种情况下的光合速率，取较小值
    return min(A_I, A_C_c)


def main():
    if len(sys.argv) != 5:
        print("使用方法（命令行方式）: python farquhar_model.py (light_intensity CO2_concentration)")
        return

    # 获取命令行参数并转换为浮点数
    light_intensity = float(sys.argv[1])  # 光强度（瓦特/平方米）
    CO2_concentration = float(sys.argv[2])  # CO2浓度（微摩尔/千帕）

    # 使用模型计算净光合速率
    net_photosynthesis_rate = farquhar_model(light_intensity, CO2_concentration)

    # 输出结果
    print("估计净光合速率:", net_photosynthesis_rate)


if __name__ == "__main__":
    main()
