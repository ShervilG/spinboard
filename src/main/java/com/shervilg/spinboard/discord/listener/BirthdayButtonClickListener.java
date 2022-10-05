package com.shervilg.spinboard.discord.listener;

import org.springframework.stereotype.Service;
import com.shervilg.spinboard.discord.common.CommandConstant;
import com.shervilg.spinboard.common.enums.DiscordChannel;
import org.springframework.beans.factory.annotation.Autowired;
import com.shervilg.spinboard.discord.template.ButtonClickListenerTemplate;

@Service
public class BirthdayButtonClickListener extends ButtonClickListenerTemplate {

  @Autowired
  private BirthdayCommandListener birthdayCommandListener;

  @Override
  public void performActionOnButtonClickEvent() {
    String buttonId = buttonClickEvent.getButtonInteraction().getCustomId();

    if (buttonId.equals(CommandConstant.BIRTHDAY_LIST_COMMAND)) {
      birthdayCommandListener.listBirthdays(buttonClickEvent.getButtonInteraction().getChannel().get());
    }
  }

  @Override
  protected boolean isValidButtonClickEvent() {
    return buttonClickEvent.getButtonInteraction().getChannel().isPresent()
        && CommandConstant.BUTTON_ENABLED_COMMANDS.get(buttonClickEvent.getButtonInteraction().getCustomId()) != null
        && buttonClickEvent.getInteraction().getChannel().get().getId() == DiscordChannel.COMMANDS_CHANNEL.getChannelId();
  }
}
