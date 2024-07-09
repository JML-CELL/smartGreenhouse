package com.example.smartgreenhouse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author :Kr
 * @date :2024/3/29 17:36
 */
@Data
@TableName("predict")
public class PredictDao {
    private Long id;
    private String predict;
    private String img;

}
