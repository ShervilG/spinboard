package com.shervilg.spinboard.alexa.handlers;

import java.util.Map;
import java.util.Optional;
import com.amazon.ask.model.Slot;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import com.amazon.ask.model.IntentRequest;
import org.springframework.stereotype.Component;
import com.shervilg.spinboard.helper.RedisHelper;
import com.shervilg.spinboard.alexa.common.AlexaIntent;
import com.shervilg.spinboard.alexa.common.AlexaSlotConstant;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import org.springframework.beans.factory.annotation.Autowired;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;

@Component
public class GetReminderIntentHandler implements RequestHandler {

  @Autowired
  private RedisHelper redisHelper;

  @Override
  public boolean canHandle(HandlerInput handlerInput) {
    return handlerInput.matches(Predicates.intentName(AlexaIntent.GET_REMINDER_INTENT.getValue()));
  }

  @Override
  public Optional<Response> handle(HandlerInput handlerInput) {
    String speechText = "";
    IntentRequest intentRequest = (IntentRequest) handlerInput.getRequest();
    Map<String, Slot> slotMap = intentRequest.getIntent().getSlots();

    String reminderKey = slotMap.get("reminderKey").getValue();
    String key = AlexaSlotConstant.SLOT_VALUE_TO_REDIS_KEY_MAP.get(reminderKey);

    if (key == null) {
      speechText = "No Such reminder key !";
    } else {
      String value = redisHelper.getKey(key, String.class);
      if (value == null) {
        speechText = "No Such reminder key !";
      } else {
        speechText = reminderKey + " " + value;
      }
    }

    return handlerInput.getResponseBuilder()
        .withSpeech(speechText)
        .withShouldEndSession(true)
        .build();
  }
}
