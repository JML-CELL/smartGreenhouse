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
 * @date :2024/3/9 22:02
 */
@Service
public class AutoLightIntensityService extends Command{

    @Resource
    DigitUtils digitUtils;

    public AutoLightIntensityService() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
    }
    //设置光强
    public void setLightIntensity(Integer top,Integer floor) throws IOException {
       String t= digitUtils.DigitFour(top);
       String f=digitUtils.DigitFour(floor);
       sendCommand("auto_light_intensity_"+t+"_"+f);
    }
}
