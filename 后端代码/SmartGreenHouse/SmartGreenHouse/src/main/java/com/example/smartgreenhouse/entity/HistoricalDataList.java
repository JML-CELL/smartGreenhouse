package com.example.smartgreenhouse.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author :Kr
 * @date :2024/1/30 11:12
 */
@Data
public class HistoricalDataList {
    private String time;
    private Integer value;
}
