package com.example.smartgreenhouse.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author :Kr
 * @date :2024/3/9 20:37
 */
@Service
public class FanService extends Command {

    public FanService() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
    }
    //风扇开
    public void fanOn() throws IOException {
        sendCommand("fan_on");
    }
    //风扇关
    public void fanOff() throws IOException {
        sendCommand("fan_off");
    }
}
