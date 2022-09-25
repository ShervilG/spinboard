package com.shervilg.spinboard.bot.template;

import org.javacord.api.event.interaction.ButtonClickEvent;
import org.javacord.api.listener.interaction.ButtonClickListener;

public abstract class ButtonClickListenerTemplate implements ButtonClickListener {
  protected ButtonClickEvent buttonClickEvent;

  @Override
  public void onButtonClick(ButtonClickEvent buttonClickEvent) {
    this.buttonClickEvent = buttonClickEvent;

    if (isValidButtonClickEvent()) {
      performActionOnButtonClickEvent();
    }

    this.buttonClickEvent.getButtonInteraction().acknowledge();
  }

  protected abstract boolean isValidButtonClickEvent();
  protected abstract void performActionOnButtonClickEvent();
}
