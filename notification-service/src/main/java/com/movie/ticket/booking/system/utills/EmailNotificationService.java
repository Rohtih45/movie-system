package com.movie.ticket.booking.system.utills;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService {

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendEmail(String subject, String body, String to) {

        try {

            MimeMessage mimeMsg = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true);

            helper.setSubject(subject);
            helper.setText(body, true);
            helper.setTo(to);
            //helper.addAttachment("plans-info", f);

            System.out.println("mail send to user");

            mailSender.send(mimeMsg);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

}
