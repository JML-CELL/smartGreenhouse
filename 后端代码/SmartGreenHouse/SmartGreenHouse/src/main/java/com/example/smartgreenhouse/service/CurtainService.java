package com.example.smartgreenhouse.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author :Kr
 * @date :2024/3/22 16:56
 */
@Service
public class CurtainService extends Command{
    public CurtainService() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
    }
    //窗帘开
    public void on() throws IOException {
        sendCommand("curtain_on");
    }
    //窗帘关
    public void off() throws IOException {
        sendCommand("curtain_off");
    }
}
