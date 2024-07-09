package com.example.smartgreenhouse.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.smartgreenhouse.entity.*;
import com.example.smartgreenhouse.mapper.PredictDaoMapper;
import com.example.smartgreenhouse.mapper.VideoMapper;
import com.example.smartgreenhouse.utils.AliOSSUtils;
import com.example.smartgreenhouse.utils.ImgUtils;
import com.example.smartgreenhouse.utils.MailUtils;
import com.google.gson.Gson;
import com.zhipu.oapi.demo.GptService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static com.example.smartgreenhouse.service.WordService.getMultipartFile;

/**
 * @author :Kr
 * @date :2024/3/28 17:48
 */
@Component
@Order(0)
public class PortListenerService implements CommandLineRunner {

     private static VideoMapper videoMapper;
     private static PredictDaoMapper predictDaoMapper;
    private static MailUtils mailUtils;
    private static final String recipient = "2030175625@qq.com";
    @Autowired
    public void SetMailUtils(MailUtils mailUtils)
    {
        PortListenerService.mailUtils=mailUtils;
    }
     @Autowired
     public void SetVidoMapper(VideoMapper videoMapper)
     {
         PortListenerService.videoMapper=videoMapper;
     }
    @Autowired
    public void SetPredictDaoMapper(PredictDaoMapper predictDaoMapper)
    {
        PortListenerService.predictDaoMapper=predictDaoMapper;
    }
    @Override
    @Async
    public void run(String... args) throws Exception {
        int portNumber = 12347; // 要监听的端口号
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("服务器已启动，正在监听端口：" + portNumber);
            while (true) {
                // 等待客户端连接
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("客户端已连接：" + clientSocket.getInetAddress().getHostAddress());

                    // 获取输入流以读取客户端发送的数据
                        InputStream in=clientSocket.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
                        String tmp = null;
                        StringBuilder sb = new StringBuilder();
                        // 读取内容
                        while ((tmp = br.readLine()) != null) {
                            sb.append(tmp).append('\n');
                        }
//                        System.out.println("这是报文"+sb);

                    ListenResult app = new Gson().fromJson(String.valueOf(sb), ListenResult.class);
                    String str=app.getJson();
                    if(str.equals("1"))
                    {
                        String vedioUrl=app.getPath();
                        VideoDao videoDao=new VideoDao();
                        videoDao.setUrl(vedioUrl);
                        Date currentDate = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String formattedDate = dateFormat.format(currentDate);
                        Date datetime = dateFormat.parse(formattedDate);
                        videoDao.setTime(datetime);
                        Integer flag=videoMapper.insert(videoDao);
                        if(flag==1)
                        {
                            System.out.println("存储成功");
                        }
                    }
                    else {
                        JSONArray jsonArray= JSONUtil.parseArray(str);
                        PredictDao predictDao=new PredictDao();
                        predictDao.setPredict(str);
                        String imgName="src/main/resources/predictImg/predict3.png";
                        //            String newFileName = "src/main/resources/predictImg/predict2.png";
                        //Base64字符串转图片
                        ImgUtils imgUtils=new ImgUtils();
                        imgUtils.convertBase64StrToImage(app.getImg(), imgName);
                        File file=new File(imgName);
                        MultipartFile multipartFile=getMultipartFile(file);
                        AliOSSUtils aliOSSUtils=new AliOSSUtils();
                        String url=aliOSSUtils.upload(multipartFile);
                        predictDao.setImg(url);
                        predictDaoMapper.insert(predictDao);
                        List<PredictJson> predictJsonList=new ArrayList<>();
                        for(int i=0;i<jsonArray.size();i++)
                        {
                            JSONObject json = (JSONObject) jsonArray.get(i);
                            PredictJson predictJson=new PredictJson();
                            predictJson.setType((String) json.get("class"));
                            predictJson.setPrecision(json.get("confidence").toString());
                            predictJsonList.add(predictJson);
                        }
                        StringBuilder stringBuilder=new StringBuilder();
                        for(int i=0;i<predictJsonList.size();i++)
                        {
                            stringBuilder.append("疾病为"+predictJsonList.get(i).getType()+" "+"精确度为"+predictJsonList.get(i).getPrecision()).append('\n');
                        }
                        Mail mail = new Mail();
                        mail.setRecipient(recipient);
                        mail.setSubject("植物生长疾病预警");
                        String content="亲爱的用户" + "：您好！\n" +
                                "\n" + "    您收到这封电子邮件是因为您的植株遭遇到了疾病的侵害，请及时处理\n" +
                                "   "+stringBuilder+"\n"+
                                "我们的预测疾病为"+predictJsonList.get(0).getType()+" "+"精确度高达"+predictJsonList.get(0).getPrecision()+"\n"+
                                "预测图片为"+url+"\n"+
                                "  发送时间为：（" +
                                DateFormatUtils.format(System.currentTimeMillis() + 10 * 60 * 1000, "yyyy-MM-dd HH:mm:ss") +
                                "）";
                        mail.setContent(content);
                        mailUtils.sendSimpleMail(mail);
//                        System.out.println("这是json"+jsonArray);
//                        JSONObject json = (JSONObject) jsonArray.get(1);
//                        System.out.println("这是json2"+json.get("class"));
                    }


//                    JSONObject jsonObject=json.getJSONObject("Json");
//                    System.out.println("这是获取的json对象"+jsonObject);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            System.out.println("服务器异常：" + e.getMessage());
            e.printStackTrace();
        }
    }






}
