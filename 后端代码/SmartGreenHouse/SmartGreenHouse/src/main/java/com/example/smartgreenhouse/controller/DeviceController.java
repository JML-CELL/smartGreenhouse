package com.example.smartgreenhouse.controller;

import com.example.smartgreenhouse.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :Kr
 * @date :2024/3/12 16:57
 */
@RestController
@RequestMapping("/device")
@Api(tags = "设备相关接口")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;
    @GetMapping("/add")
    @ApiOperation("增加设备")
    public void add(String name,String content,String house)
    {
        deviceService.add(name, content, house);
    }
    @GetMapping("/delete")
    @ApiOperation("删除设备")
    public void delete(Long id)
    {
        deviceService.delete(id);
    }
    @GetMapping("/returnId")
    @ApiOperation("返回设备id")
    public Long returnId(String name,String house)
    {
        return deviceService.returnId(name, house);
    }

}
