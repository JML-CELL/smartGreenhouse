package com.example.smartgreenhouse.controller;

import com.example.smartgreenhouse.service.SmokeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author :Kr
 * @date :2024/3/22 16:58
 */
@RestController
@RequestMapping("/smoke")
@Api(tags = "烟雾相关接口")
public class SmokeController {

    @Autowired
    SmokeService  smokeService;
    @GetMapping("/on")
    @ApiOperation("烟雾开")
    public void on() throws IOException {
        smokeService.on();
    }
    @GetMapping("/off")
    @ApiOperation("烟雾关")
    public void off() throws IOException {
        smokeService.off();
    }
}
