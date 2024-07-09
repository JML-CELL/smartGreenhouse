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
 * @date :2024/3/9 21:58
 */
@Service
public class AutoSoilHumidityService extends Command{

    @Resource
    DigitUtils digitUtils;

    public AutoSoilHumidityService() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
    }
    //设置土壤湿度
    public void setSoilHumidity(Integer top,Integer floor) throws IOException {
        String t=digitUtils.DigitTwo(top);
        String f=digitUtils.DigitTwo(floor);
        sendCommand("auto_soil_humidity_"+t+"_"+f);
    }
}
