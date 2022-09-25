package com.shervilg.spinboard.bot.listener;

import io.lettuce.core.protocol.Command;
import org.apache.commons.lang3.StringUtils;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.entity.message.embed.Embed;
import org.springframework.stereotype.Service;
import com.shervilg.spinboard.common.enums.DiscordChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import com.shervilg.spinboard.bot.common.CommandConstant;
import com.shervilg.spinboard.bot.template.MessageCreateListenerTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class HelpMessageListener extends MessageCreateListenerTemplate {
  @Override
  protected boolean isValidMessageCreateEvent() {
    return messageCreateEvent.getMessage().getChannel() != null
        && !StringUtils.isEmpty(messageCreateEvent.getMessage().getContent())
        && messageCreateEvent.getMessage().getContent().strip().equals(CommandConstant.HELP_COMMAND)
        && messageCreateEvent.getMessage().getChannel().getId() == DiscordChannel.COMMANDS_CHANNEL.getChannelId();
  }

  @Override
  protected void performActionOnMessageCreateEvent() {
    MessageBuilder message = new MessageBuilder();
    EmbedBuilder embed = new EmbedBuilder();
    List<LowLevelComponent> buttons = new ArrayList<>();

    embed.setTitle("List of Commands");

    CommandConstant.HELP_MAP.forEach((key, value) -> {
      embed.addField(key, value);

      if (CommandConstant.BUTTON_ENABLED_COMMANDS.get(key) != null) {
        buttons.add(Button.primary(key, CommandConstant.BUTTON_ENABLED_COMMANDS.get(key)));
      }
    });

    message.addEmbed(embed);

    if (buttons.size() != 0) {
      message.addActionRow(buttons.toArray(new LowLevelComponent[0]));
    }

    message.send(messageCreateEvent.getChannel());
  }
}
