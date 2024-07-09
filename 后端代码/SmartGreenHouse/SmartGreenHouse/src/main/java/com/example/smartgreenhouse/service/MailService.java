package com.example.smartgreenhouse.service;

import com.example.smartgreenhouse.entity.MailApp;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;

/**
 * @author :Kr
 * @date :2024/3/16 22:51
 */
@Service
public class MailService {

    //发送生长预警给app
    public MailApp sendToApp()
    {
        MailApp mailApp=new MailApp();
        mailApp.setTopic("番茄叶斑病报警");
        mailApp.setContent("app提示您发现番茄叶斑病!请前往邮箱查收详细信息");
        String time=DateFormatUtils.format(System.currentTimeMillis() + 10 * 60 * 1000, "yyyy-MM-dd HH:mm:ss");
        mailApp.setTime(time);
        return mailApp;
    }
    public MailApp sent()
    {
        return null;
    }

}
