package com.example.smartgreenhouse.controller;

import com.example.smartgreenhouse.service.CurtainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author :Kr
 * @date :2024/3/22 17:00
 */
@RestController
@RequestMapping("/curtain")
@Api(tags = "窗帘相关接口")
public class CurtainController {
    @Autowired
    CurtainService curtainService;
    @GetMapping("/on")
    @ApiOperation("打开窗帘")
    public void on() throws IOException {
        curtainService.on();
    }
    @GetMapping("/off")
    @ApiOperation("关闭窗帘")
    public void off() throws IOException {
        curtainService.off();
    }
}
