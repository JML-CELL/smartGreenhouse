package com.example.smartgreenhouse.controller;

import com.example.smartgreenhouse.service.HumidifierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author :Kr
 * @date :2024/3/9 21:13
 */
@RestController
@RequestMapping("/humidifier")
@Api(tags = "加湿器的相关接口")
public class HumidifierController {

    @Autowired
    HumidifierService humidifierService;
    @GetMapping("/on")
    @ApiOperation("打开加湿器")
    public void on() throws IOException {
        humidifierService.on();
    }
    @GetMapping("/off")
    @ApiOperation("关闭加湿器")
    public void off() throws IOException {
        humidifierService.off();
    }
}
