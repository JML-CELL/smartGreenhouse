package com.example.smartgreenhouse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
 * @author :Kr
 * @date :2024/1/26 0:08
 */
@Data
@TableName("house")
public class HouseDao {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer length;
    private Integer wide;
    private Integer high;
    private String cropper;
    private String housePic;
}
