package com.example.smartgreenhouse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author :Kr
 * @date :2024/3/12 16:43
 */
@Data
@TableName("device")
public class DeviceDao {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private String content;
    private String house;
}
