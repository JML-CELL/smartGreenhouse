package com.example.smartgreenhouse.controller;

import com.example.smartgreenhouse.entity.CropperDao;
import com.example.smartgreenhouse.service.CropperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author :Kr
 * @date :2024/1/26 0:08
 */
@RestController
@RequestMapping("/cropper")
@Api(tags = "种植物相关接口")
public class CropperController {

    @Autowired
    private CropperService cropperService;

    @GetMapping("/addCropper")
    @ApiOperation("添加种植物，成功返回1,否则返回0 ")
    public Integer addCropper(@ApiParam(name = "name",value = "作物名") String name, @ApiParam(name = "house",value = "种植温室") String house, @ApiParam(name = "plantTime",value = "种植时间") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date plantTime, @ApiParam(name = "picUrl",value = "图片地址") String picUrl)
    {
        return cropperService.addCropper(name,house,plantTime,picUrl);
    }
    @GetMapping("/cropperInfo")
    @ApiOperation("返回种植物信息")
    public CropperDao cropperDaoInfo(@ApiParam(name = "name",value = "作物名") String name)
    {
        return cropperService.cropperDaoInfo(name);
    }
    @GetMapping("/deleteCropper")
    @ApiOperation("删除种植物，成功返回1 否则返回0")
    public Integer deleteCropper(@ApiParam(name = "id",value = "id") Long id)
    {
        return cropperService.deleteCropper(id);
    }
}
