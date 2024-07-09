package com.example.smartgreenhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * @author :Kr
 * @date :2024/1/26 0:08
 */
@SpringBootApplication
@EnableSwagger2
@EnableAsync
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class SmartGreenHouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartGreenHouseApplication.class, args);
    }

}
