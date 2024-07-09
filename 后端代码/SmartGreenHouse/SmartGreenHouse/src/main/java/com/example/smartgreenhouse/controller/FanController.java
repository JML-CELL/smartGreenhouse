package com.example.smartgreenhouse.controller;

import com.example.smartgreenhouse.service.FanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author :Kr
 * @date :2024/3/9 21:07
 */
@RestController
@RequestMapping("/fan")
@Api(tags = "风扇相关接口")
public class FanController {

    @Autowired
    FanService fanService;
    @GetMapping("/on")
    @ApiOperation("打开风扇")
    public void on() throws IOException {
        fanService.fanOn();
    }
    @GetMapping("/off")
    @ApiOperation("关闭风扇")
    public void off() throws IOException {
        fanService.fanOff();
    }
}
