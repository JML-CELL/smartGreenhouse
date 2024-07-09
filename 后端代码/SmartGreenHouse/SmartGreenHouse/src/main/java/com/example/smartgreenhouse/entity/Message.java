package com.example.smartgreenhouse.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author :Kr
 * @date :2024/3/16 22:45
 */
@Data
public class Message {

    private String topic;
    private Date time;
    private String content;
    private String picUrl;
}
