package com.zhipu.oapi.service.v4.model;


import com.zhipu.oapi.service.v4.model.WeatherUnit;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherResponse {

    public String location;
    public com.zhipu.oapi.service.v4.model.WeatherUnit unit;
    public int temperature;
    public String description;

    public WeatherResponse(String location, WeatherUnit unit, int temperature, String description) {
        this.location = location;
        this.unit = unit;
        this.temperature = temperature;
        this.description = description;
    }
}
