package com.example.smartgreenhouse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author :Kr
 * @date :2024/1/27 16:17
 */
@Data
@TableName("fertilizer_records")
public class FertilizerRecordsDao {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String action;
    private String fertilizer;
    private String device;
    private String cropper;
    private Date time;
}
