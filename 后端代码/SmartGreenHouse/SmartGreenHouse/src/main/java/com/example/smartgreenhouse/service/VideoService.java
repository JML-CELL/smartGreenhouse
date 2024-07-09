package com.example.smartgreenhouse.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deepoove.poi.XWPFTemplate;
import com.example.smartgreenhouse.entity.VideoDao;
import com.example.smartgreenhouse.mapper.VideoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;


/**
 * @author :Kr
 * @date :2024/1/26 0:08
 */
@Service
public class VideoService {

    @Resource
    VideoMapper videoMapper;
    public List<VideoDao> showTop10()
    {
        QueryWrapper<VideoDao> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 0,10");
        List<VideoDao> list=videoMapper.selectList(queryWrapper);
        return list;
    }
}
