package com.example.smartgreenhouse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/**
 * @author :Kr
 * @date :2024/1/26 0:08
 */
@Data
@TableName("cropper")
public class CropperDao {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private String house;
    private Date plantTime;
    private String picUrl;
}
