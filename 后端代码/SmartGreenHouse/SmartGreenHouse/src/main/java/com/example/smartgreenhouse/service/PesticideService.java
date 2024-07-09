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
 * @date :2024/3/9 20:42
 */
@Service
public class PesticideService extends Command{

    @Autowired
    RecordService recordService;
    public PesticideService() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
    }
    //农药开
    public void pesticideOn(Long id) throws IOException, ParseException {
        sendCommand("pesticide_on");
        String action="施肥";
        recordService.AutoaddFertilizerRecords(id,action);

    }
    //农药关
    public void pesticideOff() throws IOException {
        sendCommand("pesticide_off");
    }
}
