package com.example.smartgreenhouse.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author :Kr
 * @date :2024/3/9 20:47
 */
@Service
public class AirService extends Command{
    public AirService() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
    }
    //空调开
    public void on(Integer temperature,Integer speed,String mood) throws IOException {
        String temp=temperature.toString();
        String spe=speed.toString();
        System.out.println("这是air——on："+"air_on_"+temp+"_"+spe+"_"+mood);
        sendCommand("air_on_"+temp+"_"+spe+"_"+mood);
    }
    //空调关
    public void off() throws IOException {
        sendCommand("air_off");
    }
}
