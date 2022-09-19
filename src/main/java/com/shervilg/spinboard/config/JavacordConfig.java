package com.shervilg.spinboard.config;

import java.util.List;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.javacord.api.listener.message.MessageCreateListener;

@Configuration
public class JavacordConfig {

  @Value(value = "${discord.bot.token}")
  private String discordBotToken;

  @Bean
  public DiscordApi getDiscordApi(@Autowired List<MessageCreateListener> messageCreateListeners) {
    DiscordApi discordApi = new DiscordApiBuilder()
        .setToken(discordBotToken)
        .login()
        .join();

    messageCreateListeners.forEach(discordApi::addMessageCreateListener);

    return discordApi;
  }
}
