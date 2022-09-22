package com.shervilg.spinboard.bot.template;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public abstract class MessageCreateListenerTemplate implements MessageCreateListener {

  protected MessageCreateEvent messageCreateEvent;

  @Override
  public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
    this.messageCreateEvent = messageCreateEvent;

    if (isValidMessageCreateEvent()) {
      performActionOnMessageCreateEvent();
    }
  }

  protected abstract boolean isValidMessageCreateEvent();
  protected abstract void performActionOnMessageCreateEvent();
}
