package com.todo.todoapp.services.implentations;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.todo.todoapp.services.interfaces.EmailServiceI;

@Service
public class EmailService implements EmailServiceI {

    @Value("${sendgrid.api.key}")
    private String sendGridAPIKey;

    @Value("${sendgrid.sender.email}")
    private String senderEmail;

    @Override
    public void sendOTPEmail(String recieverEmail, String otp) {
        
        String subject = "Your OTP for ToDo App Registration";
        String body = "Dear user,\n\nYour OTP is: " + otp + "\n\nThis OTP will expire in 10 minutes.";

        Email from = new Email(senderEmail);
        Email to = new Email(recieverEmail);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridAPIKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            // Send email
            Response response = sg.api(request);

            // Log status code (for debugging or logging purposes)
            System.out.println("Email sent! Status Code: " + response.getStatusCode());

        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to send email via SendGrid", ex);
        }
    }

}
