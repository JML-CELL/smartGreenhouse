package com.example.smartgreenhouse.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author :Kr
 * @date :2024/3/9 20:45
 */
@Service
public class HumidifierService extends Command{

    public HumidifierService() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
    }
    //加湿器开
    public void on() throws IOException {
        sendCommand("humidifier_on");
    }
    //加湿器关
    public void off() throws IOException {
        sendCommand("humidifier_off");
    }
}
