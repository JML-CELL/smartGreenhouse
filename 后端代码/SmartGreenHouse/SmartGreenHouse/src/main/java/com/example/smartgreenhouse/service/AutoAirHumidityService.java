package com.example.smartgreenhouse.service;

import com.example.smartgreenhouse.utils.DigitUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author :Kr
 * @date :2024/3/9 21:52
 */
@Service
public class AutoAirHumidityService extends Command{

    @Resource
    DigitUtils digitUtils;
    public AutoAirHumidityService() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
    }
    //设置 空气湿度
    public void setAirHumidity(Integer top,Integer floor) throws IOException {
        String t=digitUtils.DigitTwo(top);
        String f=digitUtils.DigitTwo(floor);
        sendCommand("auto_air_humidity_"+t+"_"+f);
    }
}
