package com.diggit.qa.common;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.soap.Text;
import java.util.Properties;

/**
 * Created by yoosuf on 7/10/2016.
 */
public class EmailUtil {
    public static void main(String [] args) {
        send("dddd", "test");
    }

    public static void send(String body, String subject){
        final String username = "yoosuf@moogilu.com";
        final String password = "mahdi2210";

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
                    InternetAddress.parse("yoosuf@moogilu.com"));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getStateMachineEmail(int successCount, int failCount){

        String email = TextFileWriter.fileAsString("src/main/resources/stateMachineEmail.txt");
        String summary = "";
        if(successCount == 100){
            summary =  Constant.ALL_SUCCESS;
        } else {
            summary = Constant.SOME_FAILED;
            summary = summary.replace("s_count", String.valueOf(successCount)).replace("f_count", String.valueOf(failCount));
        }

        email = email.replace("summery_statement", summary);

        return email;
    }
}

