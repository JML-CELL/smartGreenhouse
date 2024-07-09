package com.example.smartgreenhouse.controller;

import com.example.smartgreenhouse.service.WaterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

/**
 * @author :Kr
 * @date :2024/3/9 21:09
 */
@RestController
@RequestMapping("/water")
@Api(tags = "水泵相关接口")
public class WaterController {

    @Autowired
    WaterService waterService;
    @GetMapping("/on")
    @ApiOperation("打开水泵")
    public void on(Long id) throws IOException, ParseException {
        waterService.waterOn(id);
    }
    @GetMapping("/off")
    @ApiOperation("关闭水泵")
    public void off() throws IOException {
        waterService.waterOff();
    }
}
