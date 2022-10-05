package com.shervilg.spinboard.alexa.config;

import javax.servlet.http.HttpServlet;

import com.amazon.ask.Skills;
import com.amazon.ask.builder.StandardSkillBuilder;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.servlet.SkillServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

import java.util.List;

@Configuration
public class AlexaServletRegistrationConfig {
  @Autowired
  private List<RequestHandler> requestHandlers;

  @Bean
  public ServletRegistrationBean<HttpServlet> alexaServlet() {
    StandardSkillBuilder skillBuilder = Skills.standard();
    requestHandlers.forEach(skillBuilder::addRequestHandlers);

    SkillServlet skillServlet = new SkillServlet(skillBuilder.build());

    ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
    servRegBean.setServlet(skillServlet);
    servRegBean.addUrlMappings("/alexa/*");
    servRegBean.setLoadOnStartup(1);

    return servRegBean;
  }
}
