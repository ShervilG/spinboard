package com.shervilg.spinboard.helper.impl;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import org.springframework.stereotype.Service;
import com.sendgrid.helpers.mail.objects.Email;
import com.shervilg.spinboard.helper.EmailHelper;
import com.sendgrid.helpers.mail.objects.Content;
import org.springframework.beans.factory.annotation.Autowired;
import com.shervilg.spinboard.dto.request.EmailRequest;

@Service
public class SendgridEmailHelper implements EmailHelper {

  @Autowired
  private SendGrid sendGridClient;

  @Override
  public void sendEmail(EmailRequest emailRequest) {
    // TODO: Add request validation

    Mail mail = new Mail(
        new Email(emailRequest.getFrom()),
        emailRequest.getSubject(),
        new Email(emailRequest.getTo()),
        new Content(emailRequest.getContentType(), emailRequest.getContent())
    );

    try {
      Request request = new Request();
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());

      Response response = sendGridClient.api(request);
    } catch (Exception e) {
      throw new RuntimeException("Couldn't send email via sendgrid: " + e);
    }
  }
}
