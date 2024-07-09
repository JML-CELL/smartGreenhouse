package com.example.smartgreenhouse.controller;

import com.example.smartgreenhouse.service.LightService;
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
 * @date :2024/3/9 20:58
 */
@RestController
@RequestMapping("/light")
@Api(tags = "灯的相关接口")
public class LightController {

    @Autowired
    LightService lightService;

    @GetMapping("/on")
    @ApiOperation("打开灯")
    public void on(@ApiParam(value = "rgb红的色调") Integer r,@ApiParam(value = "rgb绿的色调") Integer g,@ApiParam(value = "rgb蓝的色调") Integer b) throws IOException {
        lightService.lightOn(r, g, b);
    }
    @GetMapping("/off")
    @ApiOperation("关闭灯")
    public void off() throws IOException {
        lightService.lightOff();
    }
}
