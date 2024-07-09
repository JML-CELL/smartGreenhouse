package com.example.smartgreenhouse.controller;

import com.example.smartgreenhouse.service.PesticideService;
import com.example.smartgreenhouse.service.RecordService;
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
 * @date :2024/3/9 21:11
 */
@RestController
@RequestMapping("/pesticide")
@Api(tags = "农药喷洒相关接口")
public class PesticideController {

    @Autowired
    PesticideService pesticideService;

    @GetMapping("/on")
    @ApiOperation("打开农药喷洒")
    public void on(Long id) throws IOException, ParseException {
        pesticideService.pesticideOn(id);

    }
    @GetMapping("/off")
    @ApiOperation("关闭农药喷洒")
    public void off() throws IOException {
        pesticideService.pesticideOff();
    }
}
