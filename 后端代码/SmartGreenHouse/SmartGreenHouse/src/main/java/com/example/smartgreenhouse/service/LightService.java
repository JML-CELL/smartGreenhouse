package com.example.smartgreenhouse.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author :Kr
 * @date :2024/3/9 19:57
 */
@Service
public class LightService extends Command {

    public LightService() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
    }

    //开灯
    public void lightOn(Integer r, Integer g, Integer b) throws IOException {
        String R=rgb(r);
        String G=rgb(g);
        String B=rgb(b);
        String command="light_on_"+R+"_"+G+"_"+B;
//        System.out.println("这是command"+command);
        sendCommand(command);
    }
    //关灯
    public void lightOff() throws IOException {
        sendCommand("light_off");
    }

    public String rgb(Integer number)
    {
        String result="";
        if(number<=10)
        {
            if(number==10)
            {
                result="0"+number;
            }
            else {
                result="00"+number;
            }

            return result;
        } else if (number<=100) {
            if(number==100)
            {
                result=number.toString();
            }
            else {
                result="0"+number;
            }
           return result;
        }
        else {
            return number.toString();
        }

    }
}
