package com.example.smartgreenhouse.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author :Kr
 * @date :2024/3/9 20:56
 */
@Service
public class MusicService extends Command{

    public MusicService() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
    }
    //音乐开
    public void on() throws IOException {
        sendCommand("music_on");
    }
    //音乐关
    public void off() throws IOException {
        sendCommand("music_off");
    }
    //音乐下一首
    public void next() throws IOException {
        sendCommand("music_next");
    }
}
