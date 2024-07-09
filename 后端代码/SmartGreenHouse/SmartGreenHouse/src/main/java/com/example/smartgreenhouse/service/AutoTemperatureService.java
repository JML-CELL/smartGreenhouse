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
 * @date :2024/3/9 21:32
 */
@Service
public class AutoTemperatureService extends Command{

    @Resource
    DigitUtils digitUtils;

    public AutoTemperatureService() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
    }
    //设置 温度
    public void setTemperature(Integer temperature) throws IOException {
        String temp=digitUtils.DigitTwo(temperature);
        sendCommand("auto_temperature_"+temp);
    }
}
