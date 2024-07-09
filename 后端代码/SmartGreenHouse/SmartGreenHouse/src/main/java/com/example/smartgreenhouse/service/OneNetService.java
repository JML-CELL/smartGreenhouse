package com.example.smartgreenhouse.service;

import com.example.smartgreenhouse.entity.*;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author :Kr
 * @date :2024/1/30 8:56
 */
@Service
public class OneNetService {

    //生成全局token
    private String token=new Token().getToken();
    //生成全局请求对象
    OkHttpClient client = new OkHttpClient();
    public OneNetService() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
    }
    //获取设备最新数据查询
    public JsonRootBean getOnenetLatestdata() throws IOException {

        Request request = new Request.Builder().url("https://iot-api.heclouds.com/thingmodel/query-device-property?product_id=T558h4Pj6O&device_name=esp32")
                .header("authorization",token)
                .build();
//        System.out.println(token);
        Response response = client.newCall(request).execute();
        String responseData = response.body().string();
        JsonRootBean app = new Gson().fromJson(responseData, JsonRootBean.class);

//        System.out.println("token:{}"+responseData);
        return app;
    }
    //获取空气湿度历史数据
    public List<HistoricalDataList> airHistoricalData(String start_time, String end_time,String identifier,Integer model) throws IOException, ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String start_Time = String.valueOf(simpleDateFormat.parse(start_time).getTime());
        String end_Time=String.valueOf(simpleDateFormat.parse(end_time).getTime());
        long startTime=simpleDateFormat.parse(start_time).getTime();
        Request request = new Request.Builder().url("https://iot-api.heclouds.com/thingmodel/query-device-property-history?" +
                        "product_id=T558h4Pj6O&" +
                        "device_name=esp32&" +
                        "identifier="+identifier+"&" +
                        "start_time="+start_Time+"&" +
                        "end_time="+end_Time+"&" +
                        "sort=2&" +
                        "limit=1000&")
                .header("authorization",token)
                .build();
        Response response = client.newCall(request).execute();

        String responseData = response.body().string();
        HistoricalDataJson app = new Gson().fromJson(responseData, HistoricalDataJson.class);
        Datalist datalist=app.getData();
        Integer size=datalist.getList().size();
        List<HistoricalDataList> list=new ArrayList<>();
        List<HistoricalDataList> listday=new ArrayList<>();
        List<HistoricalDataList> listsum=new ArrayList<>();
        //System.out.println("这是反应体"+responseData);
        for(int i=0;i<size;i++)
        {
            long time=datalist.getList().get(i).getTime();
            String value=datalist.getList().get(i).getValue();

            //将时间戳转化为年月日时分秒的形式
            Date dateTime = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            HistoricalDataList historicalDataList=new HistoricalDataList();
            historicalDataList.setTime(sdf.format(dateTime));
            historicalDataList.setValue(Integer.valueOf(value));
            list.add(historicalDataList);
        }
        //model为0的话返回的是当天的数据
        if(model==0)
        {
            return list;
        }
        if(model==1)
        {
            //大致思路是录取了就去掉，注意避免循环重复，循环是动态的，可以先将开始时间格式化为yyyy-mm-dd 00:00:00，便于统计计算，然后startTime=+1之后进行计算
            Integer timesSum=0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                for(int j=1;j<=7;j++)
                {
                    Integer sum=0;
                    Integer times=0;
                    startTime=simpleDateFormat.parse(list.get(0).getTime()).getTime()+(1* 24 * 60 * 60 * 1000L);
                    for(int i=0;i<list.size();i++)
                    {

                        long time=simpleDateFormat.parse(list.get(i).getTime()).getTime();

                        if(time<startTime)
                        {
                            sum+=list.get(i).getValue();
                            list.remove(i);
                            times++;
                        }
                    }
                    timesSum+=times;
                    HistoricalDataList dataday=new HistoricalDataList();
                    dataday.setValue(sum/times);
                    dataday.setTime(list.get(0).getTime());
                    listday.add(dataday);
                }

                return listday;
        }
        return  null;
    }
}
