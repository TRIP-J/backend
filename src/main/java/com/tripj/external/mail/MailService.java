package com.tripj.external.mail;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@Transactional
@AllArgsConstructor
public class MailService {

    //yml에서 설정한 값
    private final String user_email;
    private final String user_pw;
    private final String smtp_host;
    private final int smtp_port;

    public void send(String email, String subject, String text) {
        Properties props = System.getProperties();
        props.put("mail.smtp.host", smtp_host);
        props.put("mail.smtp.port", smtp_port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", smtp_host);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user_email, user_pw);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user_email));

            // 받는 이메일
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email)
            );

            // 제목
            message.setSubject(subject);
            // 내용
            message.setText(text);
            // 발송
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
