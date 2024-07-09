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
 * @date :2024/3/9 22:08
 */
@Service
public class AutoCarbonDioxideService extends Command{

    @Resource
    DigitUtils digitUtils;

    public AutoCarbonDioxideService() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
    }
    //设置二氧化碳
    public void setCarbonDioxide(Integer top,Integer floor) throws IOException {
        String t=digitUtils.DigitFour(top);
        String f=digitUtils.DigitFour(floor);
        sendCommand("auto_carbon_dioxide_"+t+"_"+f);
    }
}
