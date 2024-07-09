package com.example.smartgreenhouse.controller;

import com.example.smartgreenhouse.entity.Mail;
import com.example.smartgreenhouse.entity.MailApp;
import com.example.smartgreenhouse.entity.Token;
import com.example.smartgreenhouse.service.MailService;
import com.example.smartgreenhouse.utils.MailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author :Kr
 * @date :2024/3/8 13:54
 */
@RestController
@RequestMapping("/mail")
@Api(tags = "发送邮箱相关接口")
public class MailController {
    @Resource
    private MailUtils mailUtil;
    @Autowired
    MailService mailService;
    //接收人
    //private static final String recipient = "1780016475@qq.com";

    @GetMapping("/sendMail")
    @ApiOperation("发送生长预警邮件")
    public void sendMail(@ApiParam(value = "用户名") String name, @ApiParam(value = "温度") Integer temperature,@ApiParam(value = "接收者的qq邮箱") String recipient)
    {
        Mail mail = new Mail();
        mail.setRecipient(recipient);
        mail.setSubject("生长预警");
        mail.setContent("亲爱的"+name+"：您好！\n" +
                "\n" + "    您收到这封电子邮件是因为您温室温度过高，温度为"+temperature+"℃\n" +
               "  发送时间为：（"+
                DateFormatUtils.format(System.currentTimeMillis() + 10 * 60 * 1000, "yyyy-MM-dd HH:mm:ss")+
                "）"
        );
        mailUtil.sendSimpleMail(mail);
    }
    @GetMapping("/sendtoApp")
    @ApiOperation("发送消息通知给APP")
    public MailApp sendtoApp(){
        return mailService.sendToApp();
    }

}
