package com.example.smartgreenhouse.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author :Kr
 * @date :2024/3/22 16:55
 */
@Service
public class SmokeService extends Command{

    public SmokeService() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
    }
    //烟雾开
    public void on() throws IOException {
        sendCommand("smoke_on");
    }
    //烟雾关
    public void off() throws IOException {
        sendCommand("smoke_off");
    }
}
