package com.example.smartgreenhouse;

import com.alibaba.fastjson.JSON;
import com.example.smartgreenhouse.entity.*;
import com.example.smartgreenhouse.mapper.UserDaoMapper;
import com.example.smartgreenhouse.service.*;
import com.example.smartgreenhouse.utils.*;
import com.google.gson.Gson;

import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.core.httpclient.ApacheHttpClientTransport;

import com.zhipu.oapi.demo.GptService;
import com.zhipu.oapi.service.v4.model.*;
import okhttp3.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.util.Base64Util;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.*;



import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.oneOf;

@SpringBootTest

class SmartGreenHouseApplicationTests {

    @Resource
    private MailUtils mailUtil;
    private String token=new Token().getToken();

    @Resource
    private ImgUtils imgUtils;
    //接收人
    private static final String recipient = "2030175625@qq.com";

    @Autowired
    private RecordService recordService;
    SmartGreenHouseApplicationTests() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
    }

    /**
     * 发送文本邮件
     */
//    @Test
//    public void sendSimpleMail() {
//        Mail mail = new Mail();
//
//        mail.setRecipient(recipient);
//        mail.setSubject("生长预警");
//        mail.setContent("亲爱的用户：您好！\n" +
//                "\n" + "    您收到这封电子邮件是因为您温室温度过高，温度为60℃\n" +
//               "  发送时间为：（"+
//                DateFormatUtils.format(new Date().getTime() + 10 * 60 * 1000, "yyyy-MM-dd HH:mm:ss")+
//                "）"
//        );
//        mailUtil.sendSimpleMail(mail);
//    }
//
//


//        try {
//            // 设置Python解释器路径和Python脚本路径
//            String pythonInterpreter = "D:\\pytorch-gpu\\pytorch-gpu\\python.exe";
//            String pythonScriptPath = "C:/Users/86189/Desktop/智能温室/SmartGreenHouse/src/main/resources/tomato/ripe/tomato-ripe-predict.py";
////            String pythonScriptPath = "yolo";
//            System.out.println("Python解释器路径: " + pythonInterpreter);
//            System.out.println("Python脚本路径: " + pythonScriptPath);
//
//            // 构建Python命令的列表
//            ProcessBuilder pb = new ProcessBuilder(pythonInterpreter, pythonScriptPath);
//
//            // 启动进程并执行Python脚本
//            Process process = pb.start();
//
//            // 读取Python脚本的输出
//            InputStream is = process.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println("这是结果"+line);
//
//                System.out.println(line);
//            }
//
//            reader.close();
//
//            // 读取Python脚本的错误输出
//            InputStream errorStream = process.getErrorStream();
//            BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
//            while ((line = errorReader.readLine()) != null) {
//                System.err.println(line); // 输出到错误流
//            }
//            errorReader.close();
//
//            // 等待Python脚本执行完毕
//            int exitCode = process.waitFor();
//            System.out.println("Python脚本执行完毕，退出码：" + exitCode);
//
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//        @Test
//        public void testremoteCall() throws Exception {
//            PythonSocket pythonSocket = new PythonSocket();
//            pythonSocket.remoteCall("hello");
//        }
//        @Test
//        public void test()
//        {
//            System.out.println("----------------------------图片转Base64字符串---------------------------");
//            //图片文件路径
//            String imageFileName = "src/main/resources/predictImg/predict.png";
//            //图片转Base64字符串
//            String base64Str = imgUtils.convertImageToBase64Str(imageFileName);
//            System.out.println(base64Str);
//
//            System.out.println("----------------------------Base64字符串转图片---------------------------");
//            //新文件路径
//            String newFileName = "src/main/resources/predictImg/predict2.png";
//            //Base64字符串转图片
//            imgUtils.convertBase64StrToImage(base64Str, newFileName);
//            System.out.println("生成的文件的路径是："+newFileName);
//
//        }
//    @Test
//    public  void test() throws IOException {
////        "product_id":"B7EEW578EbRg5Y4K",
////            "device_name":"device3",
////            "params":["light","model"]
//        OkHttpClient client = new OkHttpClient();
//        String jsonInputString = "{\"product_id\":\"T558h4Pj6O\",\"device_name\":\"esp32\",\"params\":[\"smoke\",\"water_level\"]}";
//
//        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonInputString);
//        Request request = new Request.Builder().url("https://iot-api.heclouds.com/thingmodel/query-device-property-detail")
//                .post(body)
//                .header("authorization",token)
//                .build();
//        Response response = client.newCall(request).execute();
//        String responseData = response.body().string();
//        System.out.println("这是获取的属性"+responseData);
//
//    }

    @Test
    public void test() throws IOException {

        String content="给出番茄植株的生长建议";
        String res= GptService.testAsyncInvoke(content);
        GptService.testQueryResult(res);

    }

    }
