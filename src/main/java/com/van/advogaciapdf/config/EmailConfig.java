package com.van.advogaciapdf.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {


    @Value("${spring.mail.host}")
    private String host;


    @Value("${spring.mail.port}")
    private String port;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.username}")
    private String username;


    @Bean
    public JavaMailSender getJavaMailSender() {


        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();


        mailSender.setHost(host);
        mailSender.setUsername(username);
        mailSender.setPort(587);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

}
