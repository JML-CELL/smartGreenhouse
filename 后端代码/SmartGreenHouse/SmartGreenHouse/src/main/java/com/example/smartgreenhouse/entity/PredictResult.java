package com.example.smartgreenhouse.entity;

import lombok.Data;

/**
 * @author :Kr
 * @date :2024/3/17 21:15
 */
@Data
public class PredictResult {

    private String temperature;
    private String soil_moisture;
    private String carbon;
    private String illumination;
    private String ph;
    private String air_humidity;
}
