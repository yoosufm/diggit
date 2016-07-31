package com.diggit.qa.common;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by yoosuf on 7/10/2016.
 */
public class EmailUtil {
    public static void main(String [] args) {
        send("dddd");
    }

    public static void send(String body){
        final String username = "mak83826@gmail.com";
        final String password = "229/2Mhm";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mak83826@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("yoosuf@moogilu.com,jagadish@moogilu.com, "));
            message.setSubject("Diggit Vs Bitsnoop");
            message.setText(body);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    }

