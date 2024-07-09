package com.example.smartgreenhouse.controller;

import com.example.smartgreenhouse.entity.PredictResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhipu.oapi.demo.GptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :Kr
 * @date :2024/3/17 21:22
 */
@RestController
@RequestMapping("/gpt")
@Api(tags = "Gpt相关接口")
public class GptController {




    @GetMapping("/predict")
    @ApiOperation("推荐参数")
    public PredictResult predict(String cropper) throws JsonProcessingException {
       String content=" 按照格式回答问题只需要回答数字不需要进行描述，回答格式为：回答的格式为{\"temperature\":,\"soil_moisture\":，\"carbon\":,\"illumination\":,\"ph\":,\"air_humidity\":},问题为：种植在温室的"+cropper+"幼苗，适宜的温度为多少？土壤湿度为多少？二氧化碳浓度为多少？光照强度为多少？水质ph值为多少？空气湿度为多少？只需要按照格式回答和只回答数字";
       return GptService.testInvoke(content);
    }

    @GetMapping("/robot")
    @ApiOperation("机器人问答")
    public String robot(String content) throws JsonProcessingException {
        return GptService.robot(content);
    }
}
