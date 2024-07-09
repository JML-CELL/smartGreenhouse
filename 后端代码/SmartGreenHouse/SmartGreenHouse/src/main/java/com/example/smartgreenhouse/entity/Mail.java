package com.example.smartgreenhouse.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author :Kr
 * @date :2024/1/31 11:30
 */
@Data
public class Mail implements Serializable {
    private static final long serialVersionUID = 4359709211352400087L;
    private String recipient;//邮件接收人
    private String subject; //邮件主题
    private String content; //邮件内容

}
