package com.shervilg.spinboard.discord.helper;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import org.javacord.api.DiscordApi;

import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import com.shervilg.spinboard.entity.Birthday;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import com.shervilg.spinboard.discord.common.ServerConstant;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

@Service
public class BirthdayNotificationHelper {

  @Value("${alexa.notify.me.token}")
  public String alexaNotificationSkillAccessCode;

  @Autowired
  private DiscordApi discordApi;

  @Autowired
  private RestTemplateBuilder restTemplateBuilder;

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

  public void sendBirthdayNotificationViaAlexa(List<Birthday> birthdays) {
    String title = "Spinboard Birthday Alert";
    StringJoiner notificationStringJoiner = new StringJoiner(", ");

    birthdays.forEach(birthday -> {
      String birthdayString = birthday.getFirstName() + " " + birthday.getLastName();
      notificationStringJoiner.add(birthdayString);
    });

    Map<String, String> urlParams = new HashMap<>();
    urlParams.put("notification", notificationStringJoiner.toString());
    urlParams.put("accessCode", alexaNotificationSkillAccessCode);
    urlParams.put("title", title);

    RestTemplate restTemplate = restTemplateBuilder.build();
    restTemplate.exchange(
        "https://api.notifymyecho.com/v1/NotifyMe",
        HttpMethod.POST,
        null,
        Object.class,
        urlParams);
  }
}
