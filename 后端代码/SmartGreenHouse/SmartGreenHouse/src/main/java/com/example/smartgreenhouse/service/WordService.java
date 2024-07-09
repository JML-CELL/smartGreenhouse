package com.example.smartgreenhouse.service;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.example.smartgreenhouse.entity.*;
import com.example.smartgreenhouse.utils.AliOSSUtils;
import com.example.smartgreenhouse.utils.DateTime;
import com.example.smartgreenhouse.utils.StreamUtil;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author :Kr
 * @date :2024/3/21 21:22
 */
@Service
public class WordService {


    @Autowired
    private RecordService recordService;
    @Autowired
    private CropperService cropperService;
    @Autowired
    private AliOSSUtils aliOSSUtils;
    @Autowired
    private StreamUtil streamUtil;
//    //模板地址
//    String templateFilePath = "src/main/resources/model/";
    //生成文件的保存地址
    String destFilePath = "src/main/resources/outmodel/";

    public void outputWord(String cropper, HttpServletResponse response) throws IOException, ParseException {
        //存放要填充的数据
        Map<String, Object> datas = new HashMap<String, Object>();
        //存放模板中肥药记录
        List<FertilizerRecordWord> fertilizerRecordWordList=new ArrayList<>();
        //存放模板中的农事记录
        List<AgriculturalRecordWord> agriculturalRecordWordList=new ArrayList<>();
        //取出肥药记录
        List<FertilizerRecordsDao> fertilizerRecordsDaoList=new ArrayList<>();
        fertilizerRecordsDaoList=recordService.showFertilizerRecords(cropper);
        //取出农事记录
        List<AgriculturalRecordsDao> agriculturalRecordsDaoList=new ArrayList<>();
        agriculturalRecordsDaoList=recordService.showAgriculturalRecords(cropper);
        SimpleDateFormat dateFormat= new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        if(fertilizerRecordsDaoList.size()!=0)
        {
            for(int i=0;i<fertilizerRecordsDaoList.size();i++)
            {
                FertilizerRecordWord fertilizerRecordWord=new FertilizerRecordWord();
                fertilizerRecordWord.setAction(fertilizerRecordsDaoList.get(i).getAction());
                fertilizerRecordWord.setFertilizer(fertilizerRecordsDaoList.get(i).getFertilizer());
                String dateStr=fertilizerRecordsDaoList.get(i).getTime().toString();
                Date date = (Date)dateFormat.parse(dateStr);
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                fertilizerRecordWord.setTime(time);
                fertilizerRecordWordList.add(fertilizerRecordWord);
            }
            System.out.println(fertilizerRecordsDaoList.get(0).getTime());
        }
        if(agriculturalRecordsDaoList.size()!=0)
        {
            for(int i=0;i<agriculturalRecordsDaoList.size();i++)
            {
                AgriculturalRecordWord agriculturalRecordWord=new AgriculturalRecordWord();
                agriculturalRecordWord.setAction(agriculturalRecordsDaoList.get(i).getAction());
                String dateStr=agriculturalRecordsDaoList.get(i).getTime().toString();
                Date date = (Date)dateFormat.parse(dateStr);
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                agriculturalRecordWord.setTime(time);
                agriculturalRecordWordList.add(agriculturalRecordWord);
            }
        }
        //创建字体格式
        Style style = Style.builder().buildFontSize(18).buildColor("000000").build();
        Style style2 = Style.builder().buildFontSize(15).buildColor("000000").build();
        datas.put("cropper", Texts.of(cropper).style(style).create());
        datas.put("var1",Texts.of("28").style(style).create());
        datas.put("var2",Texts.of("30").style(style).create());
        datas.put("var3",Texts.of("1000").style(style).create());
        datas.put("var4",Texts.of("34").style(style).create());
        datas.put("var5",Texts.of("5.5").style(style).create());
        datas.put("var9",Texts.of("30000").style(style).create());
        datas.put("var7",Texts.of("13").style(style).create());
        datas.put("var8",Texts.of("20").style(style).create());
        CropperDao cropperDao=cropperService.cropperDaoInfo(cropper);
        //格式化时间
        DateTime dateTime=new DateTime();
        String var6=dateTime.dateformat(cropperDao.getPlantTime().toString());
        datas.put("var6",Texts.of(var6).style(style2).create());
        // 用行循环插件
        LoopRowTableRenderPolicy renderPolicy = new LoopRowTableRenderPolicy();
        ConfigureBuilder builder = Configure.builder();
        Configure configure = builder.bind("list0", renderPolicy).bind("list1", renderPolicy).build();
        //渲染文件
        //通过ClassPathResource读取文件，因为打包成jar包文件不能直接通过路径访问
        ClassPathResource classPathResource = new ClassPathResource("/model/model.docx");
        InputStream inputStream = classPathResource.getInputStream();
        //将文件流的形式转化为文件
        File file=streamUtil.stream2file(inputStream);
        String filePath=file.getPath();
        XWPFTemplate compile = XWPFTemplate.compile(filePath,configure);

        datas.put("list0",fertilizerRecordWordList);
        datas.put("list1",agriculturalRecordWordList);
        compile.render(datas);
        //下载文件
        String fileName = "农事记录" + System.currentTimeMillis() + ".docx";
        String fileNameURL = URLEncoder.encode(fileName, "UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileNameURL + ";" + "filename*=utf-8''" + fileNameURL);
        response.setContentType("application/octet-stream");
        //刷新缓冲
        response.flushBuffer();
        OutputStream ouputStream = response.getOutputStream();
        compile.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
        //输出为文件，指定输出文件名
        compile.writeToFile(destFilePath+"model_test"+".docx");
        compile.close();


    }
    public static MultipartFile getMultipartFile(File file) {
        FileItem item = new DiskFileItemFactory().createItem("file"
                , MediaType.MULTIPART_FORM_DATA_VALUE
                , true
                , file.getName());
        try (InputStream input = new FileInputStream(file);
             OutputStream os = item.getOutputStream()) {
            // 流转移
            IOUtils.copy(input, os);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file: " + e, e);
        }

        return new CommonsMultipartFile(item);
    }
    public String getword() throws IOException {
//        destFilePath+"model_test"+".docx"
        ClassPathResource classPathResource = new ClassPathResource("/outmodel/model_test.docx");
        InputStream inputStream = classPathResource.getInputStream();
        File file=streamUtil.stream2file(inputStream);
//        System.out.println("这是生成的路径"+file.getPath());
        MultipartFile multipartFile=getMultipartFile(file);
        String url=aliOSSUtils.uploadWord(multipartFile);
        return url;
    }


}
