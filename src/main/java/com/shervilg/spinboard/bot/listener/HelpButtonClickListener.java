package com.shervilg.spinboard.bot.listener;

import com.shervilg.spinboard.bot.common.CommandConstant;
import com.shervilg.spinboard.common.enums.DiscordChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shervilg.spinboard.bot.template.ButtonClickListenerTemplate;

@Service
public class HelpButtonClickListener extends ButtonClickListenerTemplate {
  @Autowired
  private HelpMessageListener helpMessageListener;

  @Override
  protected void performActionOnButtonClickEvent() {
    String buttonId = buttonClickEvent.getButtonInteraction().getCustomId();
    System.out.println(buttonId);

    if (buttonId.equals(CommandConstant.HELP_COMMAND)) {
      helpMessageListener.sendHelpMessage(buttonClickEvent.getButtonInteraction().getChannel().get());
    }
  }

  @Override
  protected boolean isValidButtonClickEvent() {
   return buttonClickEvent.getButtonInteraction().getChannel().isPresent()
        && CommandConstant.BUTTON_ENABLED_COMMANDS.get(buttonClickEvent.getButtonInteraction().getCustomId()) != null
        && buttonClickEvent.getInteraction().getChannel().get().getId() == DiscordChannel.COMMANDS_CHANNEL.getChannelId();
  }
}
