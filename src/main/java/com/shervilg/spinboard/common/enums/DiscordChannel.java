package com.shervilg.spinboard.common.enums;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public enum DiscordChannel {
  COMMANDS_CHANNEL("spinboard-commands",1021487739167854662L);

  private final String channelName;
  private final Long channelId;
}
