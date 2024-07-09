package com.example.smartgreenhouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

/**
 * @author :Kr
 * @date :2024/3/9 20:39
 */
@Service
public class WaterService extends Command{

   @Autowired
   private RecordService recordService;

    public WaterService() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
    }
    //水泵开
    public void waterOn(Long id) throws IOException, ParseException {
        sendCommand("water_on");
        //记录农事记录
        String action="浇水";
        recordService.AutoaddAgriculturalRecords(id,action);
    }
    //水泵关
    public void waterOff() throws IOException {
        sendCommand("water_off");
    }
}
