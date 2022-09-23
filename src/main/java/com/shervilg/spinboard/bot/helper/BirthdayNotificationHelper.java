package com.shervilg.spinboard.bot.helper;

import java.time.Month;
import java.util.List;
import org.javacord.api.DiscordApi;
import java.util.concurrent.CompletableFuture;
import com.shervilg.spinboard.entity.Birthday;
import org.springframework.stereotype.Service;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import com.shervilg.spinboard.bot.common.ServerConstant;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class BirthdayNotificationHelper {

  @Autowired
  private DiscordApi discordApi;

  public void sendBirthdayNotification(List<Birthday> birthdays) {
    EmbedBuilder birthdayEmbed = new EmbedBuilder();
    birthdayEmbed.setTitle("Birthday(s) Alert");

    birthdays.forEach(birthday -> {
      String name = birthday.getFirstName() + " " + birthday.getLastName() + " | P" + birthday.getPriority();
      String line = birthday.getDate() + "-" + Month.of(birthday.getMonth());

      birthdayEmbed.addField(name,line);
    });

    CompletableFuture<Message> messageBuilder = new MessageBuilder()
        .setEmbed(birthdayEmbed)
        .send(discordApi.getServerById(ServerConstant.SPINBOARD_SERVER_ID).get().
            getTextChannelById(ServerConstant.BIRTHDAY_NOTIFICATION_CHANNEL_ID).get()
        );

    try {
      messageBuilder.get();
    } catch (Exception ignored) {}
  }
}
