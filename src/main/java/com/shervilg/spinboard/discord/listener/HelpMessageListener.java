package com.shervilg.spinboard.discord.listener;

import java.util.List;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;
import org.javacord.api.entity.channel.TextChannel;
import org.springframework.stereotype.Service;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.Button;
import com.shervilg.spinboard.discord.common.CommandConstant;
import com.shervilg.spinboard.common.enums.DiscordChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.message.component.LowLevelComponent;
import com.shervilg.spinboard.discord.template.MessageCreateListenerTemplate;

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
    sendHelpMessage(messageCreateEvent.getChannel());
  }

  public void sendHelpMessage(TextChannel textChannel) {
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

    message.send(textChannel);
  }
}
