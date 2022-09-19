package com.shervilg.spinboard.bot.messagelistener;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.javacord.api.event.message.MessageCreateEvent;
import com.shervilg.spinboard.common.enums.DiscordChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import com.shervilg.spinboard.common.constant.DiscordConstant;
import org.javacord.api.listener.message.MessageCreateListener;

@Service
public class HelpMessageListener implements MessageCreateListener {

  @Override
  public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
    if (!isMessageValid(messageCreateEvent)) {
      return;
    }

    System.out.println("Passed");
    EmbedBuilder helpMessageEmbed = new EmbedBuilder().addField("testing", "like this !");

    messageCreateEvent.getChannel().sendMessage(helpMessageEmbed);
  }

  private boolean isMessageValid(MessageCreateEvent messageCreateEvent) {
    return messageCreateEvent.getMessage().getChannel() != null
        && !StringUtils.isEmpty(messageCreateEvent.getMessage().getContent())
        && messageCreateEvent.getMessage().getContent().startsWith(DiscordConstant.HELP_COMMAND)
        && messageCreateEvent.getMessage().getChannel().getId() == DiscordChannel.COMMANDS_CHANNEL.getChannelId();
  }
}
