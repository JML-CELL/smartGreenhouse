package com.example.smartgreenhouse.controller;

import com.example.smartgreenhouse.entity.AgriculturalRecordsDao;
import com.example.smartgreenhouse.entity.FertilizerRecordsDao;
import com.example.smartgreenhouse.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author :Kr
 * @date :2024/1/27 17:16
 */
@RestController
@RequestMapping("/record")
@Api(tags = "记录相关接口")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping("/recordFertilizer")
    @ApiOperation("添加肥药记录 成功返回1 否则返回0")
    public Integer recordFertilizer(@ApiParam(value = "用药行为") String action, @ApiParam(value = "使用的农药") String fertilizer,@ApiParam(value = "用药的设备") String device, @ApiParam(value = "被用药的农作物") String cropper,@ApiParam(value = "用药时间") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date time)
    {
        return recordService.recordFertilizer(action,fertilizer,device,cropper,time);
    }

    @GetMapping("/showFertilizerRecords")
    @ApiOperation("根据作物名来查询该作物的用药记录 返回List")
    public List<FertilizerRecordsDao> showFertilizerRecords(@ApiParam(value = "被用药的农作物") String cropper)
    {
        return recordService.showFertilizerRecords(cropper);
    }

    @GetMapping("/recordAgricultural")
    @ApiOperation("添加农事记录 成功返回1 否则返回0")
    public Integer recordAgricultural(@ApiParam(value = "行为") String action,@ApiParam(value = "农作物") String cropper,@ApiParam(value = "记录时间") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date time)
    {
        return recordService.recordAgricultural(action,cropper,time);
    }

    @GetMapping("/showAgriculturalRecords")
    @ApiOperation("根据作物名来查询该作物的农事记录 返回List")
    public List<AgriculturalRecordsDao> showAgriculturalRecords(String cropper)
    {
        return recordService.showAgriculturalRecords(cropper);
    }
}
