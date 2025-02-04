package com.example.smartgreenhouse.utils;

import com.example.smartgreenhouse.entity.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author :Kr
 * @date :2024/1/31 11:24
 */
@Component
@Slf4j
public class MailUtils {
    @Value("${spring.mail.username}")
    private String sender; //邮件发送者

    @Resource
    private JavaMailSender javaMailSender;

    /**
     * 发送文本邮件
     *
     * @param mail
     */
    public void sendSimpleMail(Mail mail) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender); //邮件发送者
            mailMessage.setTo(mail.getRecipient()); // 邮件发给的人
            mailMessage.setSubject(mail.getSubject());  // 邮件主题
            mailMessage.setText(mail.getContent());  // 邮件内容
            javaMailSender.send(mailMessage);
            log.info("邮件发送成功 收件人：{}", mail.getRecipient());
        } catch (Exception e) {
            log.error("邮件发送失败 {}", e.getMessage());
        }
    }

}
