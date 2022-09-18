package com.shervilg.spinboard.config;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendgridClientConfig {

  @Value("${sendgrid.api.key}")
  private String sendgridApiKey;

  @Bean
  public SendGrid getClient() {
    return new SendGrid(sendgridApiKey);
  }
}
