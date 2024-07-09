package com.example.smartgreenhouse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author :Kr
 * @date :2024/1/27 17:03
 */
@Data
@TableName("agricultural_records")
public class AgriculturalRecordsDao {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String action;
    private String cropper;
    private Date time;
}
