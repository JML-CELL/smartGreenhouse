package com.example.smartgreenhouse.controller;

import com.example.smartgreenhouse.service.MusicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author :Kr
 * @date :2024/3/9 21:18
 */
@RestController
@RequestMapping("/music")
@Api(tags = "音乐开关相关接口")
public class MusicController {

    @Autowired
    MusicService musicService;
    @GetMapping("/on")
    @ApiOperation("打开音乐")
    public void on() throws IOException {
        musicService.on();
    }
    @GetMapping("/off")
    @ApiOperation("关闭音乐")
    public void off() throws IOException {
        musicService.off();
    }
    @GetMapping("/next")
    @ApiOperation("播放下一首音乐")
    public void next() throws IOException {
        musicService.next();
    }
}
