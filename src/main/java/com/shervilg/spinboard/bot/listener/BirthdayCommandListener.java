package com.shervilg.spinboard.bot.listener;

import java.time.Month;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.shervilg.spinboard.entity.Birthday;
import org.springframework.stereotype.Service;
import com.shervilg.spinboard.service.BirthdayService;
import com.shervilg.spinboard.bot.common.CommandConstant;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import com.shervilg.spinboard.common.enums.DiscordChannel;
import org.springframework.beans.factory.annotation.Autowired;
import com.shervilg.spinboard.bot.template.MessageCreateListenerTemplate;

@Service
public class BirthdayCommandListener extends MessageCreateListenerTemplate {

  @Autowired
  private BirthdayService birthdayService;

  @Override
  protected void performActionOnMessageCreateEvent() {
    String command = messageCreateEvent.getMessageContent().strip();

    if (CommandConstant.BIRTHDAY_LIST_COMMAND.equals(command)) {
      listBirthdays();
    } else {
      messageCreateEvent.getChannel().sendMessage("Invalid bday Command !");
    }
  }

  @Override
  protected boolean isValidMessageCreateEvent() {
    return messageCreateEvent.getMessage().getChannel() != null
        && !StringUtils.isEmpty(messageCreateEvent.getMessage().getContent())
        && messageCreateEvent.getMessage().getContent().startsWith(CommandConstant.BIRTHDAY_COMMAND_PREFIX)
        && messageCreateEvent.getMessage().getChannel().getId() == DiscordChannel.COMMANDS_CHANNEL.getChannelId();
  }

  private void listBirthdays() {
    List<Birthday> birthdays = birthdayService.getAllBirthdays();

    if (birthdays == null || birthdays.size() == 0) {
      messageCreateEvent.getChannel().sendMessage("No Birthdays in Cache and DB !");
    }

    EmbedBuilder embed = new EmbedBuilder();
    embed.setTitle("List of all Birthdays");

    birthdays.forEach(birthday -> {
      String name = birthday.getFirstName() + " " + birthday.getLastName() + " | P" + birthday.getPriority();
      String line = birthday.getDate() + "-" + Month.of(birthday.getMonth());

      embed.addField(name, line);
    });

    messageCreateEvent.getChannel().sendMessage(embed);
  }
}
