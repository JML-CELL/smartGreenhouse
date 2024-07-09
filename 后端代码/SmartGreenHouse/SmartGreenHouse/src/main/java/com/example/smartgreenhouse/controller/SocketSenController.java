package com.example.smartgreenhouse.controller;

import com.example.smartgreenhouse.entity.Solution;
import com.example.smartgreenhouse.service.SocketSenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :Kr
 * @date :2024/3/28 22:21
 */
@RestController
@RequestMapping("/socket")
@Api(tags = "前端发送预测相关接口")
public class SocketSenController {

    @Autowired
    SocketSenService socketSenService;
    @GetMapping("/send")
    @ApiOperation("发送预测指令 1番茄，2草莓，3玫瑰，4杂草")
    public Solution send(Integer command)
    {
        return socketSenService.send(command);
    }
}
