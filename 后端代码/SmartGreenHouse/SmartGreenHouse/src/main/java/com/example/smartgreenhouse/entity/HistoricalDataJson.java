package com.example.smartgreenhouse.entity;

import lombok.Data;

import java.util.List;

/**
 * @author :Kr
 * @date :2024/1/30 9:43
 */
@Data
public class HistoricalDataJson {
    private int code;
    private Datalist data;
    private String msg;
    private String request_id;
    private int next_skip;

}
