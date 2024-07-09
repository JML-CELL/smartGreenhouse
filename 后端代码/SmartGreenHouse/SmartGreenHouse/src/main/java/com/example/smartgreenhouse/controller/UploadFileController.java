package com.example.smartgreenhouse.controller;

import com.example.smartgreenhouse.utils.AliOSSUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author :Kr
 * @date :2024/3/8 16:02
 */
@RestController
@RequestMapping("/upload")
@Api(tags = "上传文件")
public class UploadFileController {

    //注解注入工具类
    @Autowired
    private AliOSSUtils aliOSSUtils;

    @PostMapping("/img")
    @ApiOperation("上传图片")
    public String uploadImg(@ApiParam(value = "上传文件") MultipartFile fileImage) throws Exception {
        //调用工具类方法将文件存入阿里云OSS
        String url = aliOSSUtils.upload(fileImage);
        return url;
    }
    @PostMapping("/predict")
    @ApiOperation("上传预测图片")
    public String uploadPredict(@ApiParam(value = "上传文件") MultipartFile fileImage) throws IOException {

        File dir = new File("src/main/resources/predictImg");
        if (!dir.exists()) {
            dir.mkdirs(); // 创建目录
        }
        String realPath = dir.getCanonicalPath(); // 获取真实路径
        String fileimg=realPath;
        if(fileimg.equals(""))
        {
            return"文件夹为空";
        }
        String casePath = fileimg;
        String imgFormat = fileImage.getOriginalFilename().substring(fileImage.getOriginalFilename().lastIndexOf("."));
        //验证图片类型
//        try{ boolean ifimg = VERIFY(imgFormat);
//            if (false==ifimg){ return "图片类型错误"; }
//        }catch (Exception e){ return "图片类型不正确"; }
        //判断文件是否存在
        File f = new File(casePath);
        try {if (!f.exists()){f.mkdirs();}
        }catch (Exception e){ return"文件夹不存在"; }
        String name= "predict"+imgFormat;
        //保存图片
        fileImage.transferTo(new File(casePath+"\\"+name));
        return null;
    }
}
