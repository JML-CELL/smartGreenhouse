package com.example.smartgreenhouse.controller;

import com.example.smartgreenhouse.service.*;
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
 * @date :2024/3/9 22:11
 */
@RestController
@RequestMapping("/auto")
@Api(tags = "自动控制相关接口")
public class AutoController {

    @Autowired
    AutoTemperatureService autoTemperatureService;
    @Autowired
    AutoAirHumidityService autoAirHumidityService;
    @Autowired
    AutoSoilHumidityService autoSoilHumidityService;
    @Autowired
    AutoLightIntensityService autoLightIntensityService;
    @Autowired
    AutoCarbonDioxideService autoCarbonDioxideService;

    @GetMapping("/temperature")
    @ApiOperation("自动控制设置温度")
    public void setTemperature(@ApiParam(value = "温度") Integer temperature) throws IOException {
        autoTemperatureService.setTemperature(temperature);
    }
    @GetMapping("/airHumidity")
    @ApiOperation("自动控制空气湿度")
    public void setAirHumidity(@ApiParam(value = "上限") Integer top,@ApiParam(value = "下限")Integer floor) throws IOException {
        autoAirHumidityService.setAirHumidity(top, floor);
    }
    @GetMapping("/soilhumidity")
    @ApiOperation("自动控制土壤湿度")
    public void setSoilHumidity(@ApiParam(value = "上限") Integer top,@ApiParam(value = "下限")Integer floor) throws IOException {
        autoSoilHumidityService.setSoilHumidity(top, floor);
    }
    @GetMapping("/carbonDioxide")
    @ApiOperation("自动控制二氧化碳")
    public void setCarbonDioxide(@ApiParam(value = "上限") Integer top,@ApiParam(value = "下限")Integer floor) throws IOException {
        autoCarbonDioxideService.setCarbonDioxide(top, floor);
    }
    @GetMapping("/lightIntensity")
    @ApiOperation("自动控制光照强度")
    public void setLightIntensity(@ApiParam(value = "上限") Integer top,@ApiParam(value = "下限")Integer floor) throws IOException {
        autoLightIntensityService.setLightIntensity(top, floor);
    }
}
