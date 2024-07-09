package com.example.smartgreenhouse.controller;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.example.smartgreenhouse.entity.*;
import com.example.smartgreenhouse.mapper.CropperMapper;
import com.example.smartgreenhouse.service.CropperService;
import com.example.smartgreenhouse.service.RecordService;
import com.example.smartgreenhouse.service.WordService;
import com.example.smartgreenhouse.utils.DateTime;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author :Kr
 * @date :2024/1/27 18:43
 */
@RestController
@Api(tags = "文档报告接口")
@RequestMapping("/word")
public class WordController {


    @Autowired
    WordService wordService;

    @GetMapping("/output")
    @ApiOperation("生成word")
    public void outputWord(String cropper, HttpServletResponse response) throws IOException, ParseException {
        wordService.outputWord(cropper, response);
    }
    @GetMapping("/getword")
    @ApiOperation("获取生成的报告")
    public String test() throws IOException {
    return wordService.getword();
}
}
