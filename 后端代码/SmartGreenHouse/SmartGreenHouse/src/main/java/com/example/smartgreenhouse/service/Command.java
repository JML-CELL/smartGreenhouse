package com.example.smartgreenhouse.service;

import com.example.smartgreenhouse.entity.Token;
import okhttp3.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author :Kr
 * @date :2024/3/9 20:24
 */
public class Command {

    private String token=new Token().getToken();

    public Command() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
    }

    public void sendCommand(String command) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String json = "{\"product_id\":\"T558h4Pj6O\",\"device_name\":\"esp32\"" +
                ",\"params\":{\"command\":\""+command+"\""+
                "}" + "}";
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url("https://iot-api.heclouds.com/thingmodel/set-device-property")
                .header("authorization",token)
                .post(body)
                .build();
//        System.out.println(json);
//        System.out.println(request);
        Response response = client.newCall(request).execute();
    }
}
