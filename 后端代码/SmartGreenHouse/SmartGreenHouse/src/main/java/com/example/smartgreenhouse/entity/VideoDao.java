package com.example.smartgreenhouse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
/**
 * @author :Kr
 * @date :2024/1/26 0:08
 */
@Data
@TableName("video")
public class VideoDao {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String url;
    private Date time;
}
