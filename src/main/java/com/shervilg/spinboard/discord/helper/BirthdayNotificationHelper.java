package com.shervilg.spinboard.discord.helper;

import java.time.Month;
import java.util.*;

import com.shervilg.spinboard.dto.request.AlexaBirthdayNotificationRequest;
import org.javacord.api.DiscordApi;

import java.util.concurrent.CompletableFuture;
import com.shervilg.spinboard.entity.Birthday;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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

    AlexaBirthdayNotificationRequest request = new AlexaBirthdayNotificationRequest();
    request.setNotification(notificationStringJoiner.toString());
    request.setAccessCode(alexaNotificationSkillAccessCode);
    request.setTitle(title);

    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<AlexaBirthdayNotificationRequest> requestHttpEntity = new HttpEntity<>(request, headers);

    RestTemplate restTemplate = new RestTemplate();
    restTemplate.exchange(
        "https://api.notifymyecho.com/v1/NotifyMe",
        HttpMethod.POST,
        requestHttpEntity,
        String.class);
  }
}
