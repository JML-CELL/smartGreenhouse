package com.example.smartgreenhouse.controller;

import com.example.smartgreenhouse.entity.JsonRootBean;
import com.example.smartgreenhouse.entity.Token;
import com.example.smartgreenhouse.entity.VideoDao;
import com.example.smartgreenhouse.service.VideoService;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author :Kr
 * @date :2024/1/26 0:08
 */
@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    VideoService videoService;
    @GetMapping("/top10")
    @ApiOperation("返回前十条数据")
    public List<VideoDao> showTop10()
    {
        return videoService.showTop10();
    }

}
