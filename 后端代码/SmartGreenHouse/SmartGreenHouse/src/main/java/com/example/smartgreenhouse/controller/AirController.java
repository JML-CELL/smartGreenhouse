package com.example.smartgreenhouse.controller;

import com.example.smartgreenhouse.service.AirService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author :Kr
 * @date :2024/3/9 21:15
 */
@RestController
@RequestMapping("/air")
@Api(tags = "空调相关接口")
public class AirController {

    @Autowired
    AirService airService;
    @GetMapping("/on")
    @ApiOperation("打开空调")
    public void on(@ApiParam(value = "温度") Integer temperature,@ApiParam(value = "风速") Integer speed,@ApiParam(value = "模式，只有COOL和HOT") String mood) throws IOException {
        airService.on(temperature, speed, mood);
    }
    @GetMapping("/off")
    @ApiOperation("关闭风扇")
    public void off() throws IOException {
        airService.off();
    }
}
