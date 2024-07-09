package com.example.smartgreenhouse.utils;

import org.springframework.stereotype.Component;

/**
 * @author :Kr
 * @date :2024/3/9 21:38
 */
@Component
public class DigitUtils {

    //处理两位数
    public String DigitTwo(Integer number)
    {
        String result="";
        if(number<10)
        {
           result="0"+number;
        }
        else {
            result=number.toString();
        }
        return result;
    }
    //处理四位数
    public String DigitFour(Integer number)
    {
        String result="";
        if(number>0&&number<10)
        {
            result="000"+number;
        } else if (number>=10&&number<100) {
            result="00"+number;
        } else if (number>=100&&number<999) {
            result="0"+number;
        }
        else {
            result=number.toString();
        }
        return result;
    }
}
