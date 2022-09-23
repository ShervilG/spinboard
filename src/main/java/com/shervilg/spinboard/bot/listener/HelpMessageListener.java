package com.shervilg.spinboard.bot.listener;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.shervilg.spinboard.common.enums.DiscordChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import com.shervilg.spinboard.common.constant.DiscordConstant;
import com.shervilg.spinboard.bot.template.MessageCreateListenerTemplate;

@Service
public class HelpMessageListener extends MessageCreateListenerTemplate {
  @Override
  protected boolean isValidMessageCreateEvent() {
    return messageCreateEvent.getMessage().getChannel() != null
        && !StringUtils.isEmpty(messageCreateEvent.getMessage().getContent())
        && messageCreateEvent.getMessage().getContent().startsWith(DiscordConstant.HELP_COMMAND)
        && messageCreateEvent.getMessage().getChannel().getId() == DiscordChannel.COMMANDS_CHANNEL.getChannelId();
  }

  @Override
  protected void performActionOnMessageCreateEvent() {
    EmbedBuilder helpMessageEmbed = new EmbedBuilder().addField("List Of Commands", "Under construction !");
    messageCreateEvent.getChannel().sendMessage(helpMessageEmbed);
  }
}
