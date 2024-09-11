package com.tripj.config.mail;

import com.tripj.external.mail.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// JavaMailSender가 bean 으로 안올라가서 만들어준 config
@Configuration
public class MailConfig {

    @Value("${spring.mail.username}")
    private String user_email;

    @Value("${spring.mail.password}")
    private String user_pw;

    @Value("${spring.mail.host}")
    private String smtp_host;

    @Value("${spring.mail.port}")
    private int smtp_port;  // TLS : 587, SSL : 465

    @Bean
    public String user_email() {
        return user_email;
    }

    @Bean
    public String user_pw() {
        return user_pw;
    }

    @Bean
    public String smtp_host() {
        return smtp_host;
    }

    @Bean
    public int smtp_port() {
        return smtp_port;
    }

    @Bean(name = "customMailService")
    public MailService mailService() {
        return new MailService(user_email, user_pw, smtp_host, smtp_port);
    }



}
