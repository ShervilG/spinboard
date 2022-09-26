package com.shervilg.spinboard.bot.helper;

import java.util.List;
import org.javacord.api.DiscordApi;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import com.shervilg.spinboard.entity.Gift;
import org.springframework.stereotype.Service;
import com.shervilg.spinboard.repo.GiftRepository;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import com.shervilg.spinboard.bot.common.ServerConstant;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AnviDayNotificationHelper {

  @Autowired
  private DiscordApi discordApi;

  @Autowired
  private GiftRepository giftRepository;

  public void sendAnviDayNotification() {
    List<Gift> gifts = StreamSupport.stream(giftRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
    MessageBuilder messageBuilder = new MessageBuilder();
    TextChannel channel = discordApi.getServerById(ServerConstant.SPINBOARD_SERVER_ID).get()
        .getTextChannelById(ServerConstant.ANV_SHABBI_CHANNEL_ID).get();

    messageBuilder.append("Happy Anvi day anvi !\n");

    if (gifts.size() == 0) {
      messageBuilder.append("Sorry no automated gifts in the DB today :(");
      messageBuilder.send(channel);

      return;
    }

    int randomNumber = Math.max(0, Math.min(gifts.size() - 1, (int)Math.floor(Math.random() * gifts.size())));
  }
}
