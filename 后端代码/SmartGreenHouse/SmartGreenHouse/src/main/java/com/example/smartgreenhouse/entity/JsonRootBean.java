package com.example.smartgreenhouse.entity;

import lombok.Data;

import java.util.List;

/**
 * @author :Kr
 * @date :2024/1/29 11:28
 */
@Data
public class JsonRootBean {
    private int code;
    private List<OneNetData> data;
    private String msg;
    private String request_id;
}
