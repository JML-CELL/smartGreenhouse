package com.example.smartgreenhouse.service;

import com.example.smartgreenhouse.entity.JsonRootBean;
import com.example.smartgreenhouse.entity.Mail;
import com.example.smartgreenhouse.entity.Token;
import com.example.smartgreenhouse.utils.MailUtils;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author :Kr
 * @date :2024/3/30 9:15
 */
@Component
@Order(1)
public class EarlyWarningService implements CommandLineRunner {

    private static MailUtils mailUtils;
    private  String token=new Token().getToken();
    private static final String recipient = "2030175625@qq.com";
    private static int sum=0;
    @Autowired
    public void SetMailUtils(MailUtils mailUtils)
    {
        EarlyWarningService.mailUtils=mailUtils;
    }


    public EarlyWarningService() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
    }

    @Override
    public void run(String... args) throws Exception {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://iot-api.heclouds.com/thingmodel/query-device-property?product_id=T558h4Pj6O&device_name=esp32")
                        .header("authorization", token)
                        .build();

                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                JsonRootBean app = new Gson().fromJson(responseData, JsonRootBean.class);
                Integer smokeValue = 0;
                Integer waterLevel = 0;
                for (int i = 0; i < app.getData().size(); i++) {
                    if (app.getData().get(i).getIdentifier().equals("smoke")) {
                        smokeValue = Integer.valueOf(app.getData().get(i).getValue());
                    }
                    if (app.getData().get(i).getIdentifier().equals("water_level")) {
                        waterLevel = Integer.valueOf(app.getData().get(i).getValue());
                    }
                }

                if (smokeValue == 1) {
                    Mail mail = new Mail();
                    mail.setRecipient(recipient);
                    mail.setSubject("浓烟预警");
                    mail.setContent("亲爱的用户" + "：您好！\n" +
                            "\n" + "    您收到这封电子邮件是因为您的大棚烟雾浓度过高，请及时打开视频查看\n" +
                            "  发送时间为：（" +
                            DateFormatUtils.format(System.currentTimeMillis() + 10 * 60 * 1000, "yyyy-MM-dd HH:mm:ss") +
                            "）"
                    );
                    mailUtils.sendSimpleMail(mail);
                }

                if (waterLevel == 0) {
//                    Mail mail = new Mail();
//                    mail.setRecipient(recipient);
//                    mail.setSubject("水位预警");
//                    mail.setContent("亲爱的用户" + "：您好！\n" +
//                            "\n" + "    您收到这封电子邮件是因为您的大棚供水系统水位过低，请及时加水\n" +
//                            "  发送时间为：（" +
//                            DateFormatUtils.format(System.currentTimeMillis() + 10 * 60 * 1000, "yyyy-MM-dd HH:mm:ss") +
//                            "）"
//                    );
//                    if(sum==0)
//                    {
//                        mailUtils.sendSimpleMail(mail);
//                    }
//                    sum++;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

}
