package com.example.smartgreenhouse.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author :Kr
 * @date :2024/1/27 23:23
 */
public class DateTime {

    //将EEE MMM dd HH:mm:ss zzz yyyy转化为yyyy-mm-dd hh:mm:ss格式
    public String dateformat(String time) throws ParseException {
        SimpleDateFormat dateFormat= new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        String dateStr=time;
        Date date = (Date)dateFormat.parse(dateStr);
        String result= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        return result;
    }
    //将将EEE MMM dd HH:mm:ss zzz yyyy转化为yyyy-mm-dd格式
    public String dateformatyearmouthday(String time) throws ParseException {
        SimpleDateFormat dateFormat= new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        String dateStr=time;
        Date date = (Date)dateFormat.parse(dateStr);
        String result= new SimpleDateFormat("yyyy-MM-dd").format(date);
        return result;
    }
}
