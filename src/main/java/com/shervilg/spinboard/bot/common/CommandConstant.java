package com.shervilg.spinboard.bot.common;

import java.util.Map;

public class CommandConstant {
  // Common strings
  public static final String SPACE = " ";
  public static final String COMMAND_PREFIX = "=>";

  // Help Commands
  public static final String HELP_COMMAND = COMMAND_PREFIX + "help";

  // Birthday Commands
  public static final String BIRTHDAY_COMMAND_PREFIX = COMMAND_PREFIX + "bday";
  public static final String BIRTHDAY_LIST_COMMAND = BIRTHDAY_COMMAND_PREFIX + SPACE + "list";

  public static Map<String, String> HELP_MAP = Map.of(
      HELP_COMMAND, "For viewing all help commands",
      BIRTHDAY_LIST_COMMAND, "For getting the list of all Birthdays"
  );
}
