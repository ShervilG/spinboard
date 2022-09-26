package com.shervilg.spinboard.config;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.shervilg.spinboard.bot.listener.HelpMessageListener;
import com.shervilg.spinboard.bot.listener.BirthdayCommandListener;
import com.shervilg.spinboard.bot.listener.HelpButtonClickListener;
import com.shervilg.spinboard.bot.listener.BirthdayButtonClickListener;

@Configuration
public class JavacordConfig {

  @Value(value = "${discord.bot.token}")
  private String discordBotToken;

  @Bean
  public DiscordApi getDiscordApi() {
    DiscordApi discordApi = new DiscordApiBuilder()
        .setToken(discordBotToken)
        .login()
        .join();

    discordApi.addMessageCreateListener(new HelpMessageListener());
    discordApi.addMessageCreateListener(new BirthdayCommandListener());

    discordApi.addButtonClickListener(new BirthdayButtonClickListener());
    discordApi.addButtonClickListener(new HelpButtonClickListener());

    return discordApi;
  }
}
