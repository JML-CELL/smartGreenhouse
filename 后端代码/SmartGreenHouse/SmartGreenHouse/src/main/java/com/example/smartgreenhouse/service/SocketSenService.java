package com.example.smartgreenhouse.service;

import com.example.smartgreenhouse.entity.PredictResultDao;
import com.example.smartgreenhouse.entity.Solution;
import com.example.smartgreenhouse.utils.AliOSSUtils;
import com.example.smartgreenhouse.utils.ImgUtils;
import com.google.gson.Gson;
import com.zhipu.oapi.demo.GptService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

import static com.example.smartgreenhouse.service.WordService.getMultipartFile;

/**
 * @author :Kr
 * @date :2024/3/28 22:20
 */
@Service
public class SocketSenService {

    public Solution send(Integer command)
    {
        String HOST = "192.168.10.87";
        Integer PORT = 12345;
        Logger log = Logger.getLogger(this.getClass().getName());
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        jsonObject.put("content", command);
        String str = jsonObject.toJSONString();
        System.out.println("发送内容(JSONObject)：" + jsonObject);
        System.out.println("发送内容(Integer)：" + str);
        // 访问服务进程的套接字
        Socket socket = null;
        // List<Question> questions = new ArrayList<>();
        log.info("调用远程接口:host=>" + HOST + ",port=>" + PORT);
        try {
            // 初始化套接字，设置访问服务的主机和进程端口号，HOST是访问python进程的主机名称，可以是IP地址或者域名，PORT是python进程绑定的端口号
            socket = new Socket(HOST, PORT);
            // 获取输出流对象
            OutputStream os = socket.getOutputStream();
            PrintStream out = new PrintStream(os);
            // 发送内容
            out.print(str);
            // 告诉服务进程，内容发送完毕，可以开始处理
            out.print("over");
            // 获取服务进程的输入流
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            String tmp = null;
            StringBuilder sb = new StringBuilder();
            // 读取内容
            while ((tmp = br.readLine()) != null) {
                sb.append(tmp).append('\n');
            }
            PredictResultDao res = new Gson().fromJson(String.valueOf(sb), PredictResultDao.class);
            if(command==12)
            {
                //将图片路径转化为图片文件
                String imgName="src/main/resources/predictImg/predict2.png";
                //            String newFileName = "src/main/resources/predictImg/predict2.png";
                //Base64字符串转图片
                ImgUtils imgUtils=new ImgUtils();
                imgUtils.convertBase64StrToImage(res.getImg(), imgName);
                File file=new File(imgName);
                MultipartFile multipartFile=getMultipartFile(file);
                AliOSSUtils aliOSSUtils=new AliOSSUtils();
                String url=aliOSSUtils.upload(multipartFile);

                String virus=res.getRes();

                String content="番茄患有的植病为"+virus+"请给出有效的解决方案,限定字数为200，总分总的形式，将英文转化为中文";
                String solution= GptService.robot(content);
                Solution solutionRes=new Solution();
                solutionRes.setSolution(solution);
                solutionRes.setCircumstance(virus);
                solutionRes.setImg(url);
                return solutionRes;
            }
            else if(command==11){
                //将图片路径转化为图片文件
                String imgName="src/main/resources/predictImg/predict3.png";
                //            String newFileName = "src/main/resources/predictImg/predict2.png";
                //Base64字符串转图片
                ImgUtils imgUtils=new ImgUtils();
                imgUtils.convertBase64StrToImage(res.getImg(), imgName);
                File file=new File(imgName);
                MultipartFile multipartFile=getMultipartFile(file);
                AliOSSUtils aliOSSUtils=new AliOSSUtils();
                String url=aliOSSUtils.upload(multipartFile);

                String maturity=res.getRes();
                System.out.println("输出成熟度"+maturity);
                String content="番茄的成熟度为"+maturity+"请给予200字该成熟度的培育建议,全中文作答,将英文转化为中文";
                String solution= GptService.robot(content);
                Solution solutionRes=new Solution();
                solutionRes.setSolution(solution);
                solutionRes.setCircumstance(maturity);
                solutionRes.setImg(url);

                return solutionRes;
            } else if (command==41) {
                //将图片路径转化为图片文件
                String imgName="src/main/resources/predictImg/predict4.png";
                //            String newFileName = "src/main/resources/predictImg/predict2.png";
                //Base64字符串转图片
                ImgUtils imgUtils=new ImgUtils();
                imgUtils.convertBase64StrToImage(res.getImg(), imgName);
                File file=new File(imgName);
                MultipartFile multipartFile=getMultipartFile(file);
                AliOSSUtils aliOSSUtils=new AliOSSUtils();
                String url=aliOSSUtils.upload(multipartFile);

                String maturity=res.getRes();
                System.out.println("存在的杂草为"+maturity);
                String content="存在的杂草为"+maturity+"请给予200字处理该杂草的建议建议,全中文作答,将英文转化为中文";
                String solution= GptService.robot(content);
                Solution solutionRes=new Solution();
                solutionRes.setSolution(solution);
                solutionRes.setCircumstance(maturity);
                solutionRes.setImg(url);
                return solutionRes;
            }

//            System.out.println("输出"+sb);
//            System.out.println("这是输入流"+is);
//            File f= streamUtil.stream2file(is);
//            MultipartFile multipartFile=getMultipartFile(f);
//            String url= aliOSSUtils.upload(multipartFile);
//            System.out.println("这是返回的url"+url);
            // 解析结果
//            System.out.println("接收数据：" + sb.toString());
//            ImgBase64 app = new Gson().fromJson(String.valueOf(sb), ImgBase64.class);
//            System.out.println("这是输入的结果"+app.getContent());
            // 解析成数组
            // JSONArray res = JSON.parseArray(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("远程接口调用结束");
        }
        return null;
    }
}
