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
public class SetKeyIntentHandler implements RequestHandler {

  @Autowired
  private RedisHelper redisHelper;

  @Override
  public boolean canHandle(HandlerInput handlerInput) {
    return handlerInput.matches(Predicates.intentName(AlexaIntent.SET_KEY_INTENT.getValue()));
  }

  @Override
  public Optional<Response> handle(HandlerInput handlerInput) {
    String speechText = "";
    IntentRequest intentRequest = (IntentRequest) handlerInput.getRequest();
    Map<String, Slot> slotMap = intentRequest.getIntent().getSlots();

    String reminderKey = slotMap.get("reminderKey").getValue();
    String key = AlexaSlotConstant.SLOT_VALUE_TO_REDIS_KEY_MAP.get(reminderKey);
    if (key == null) {
      speechText = "Bad Reminder Key Entered!";
    } else {
      String reminderValue = slotMap.get("reminderValue").getValue();
      redisHelper.setKey(key, reminderValue);
      speechText = reminderKey + " set to value " + reminderValue;
    }

    return handlerInput.getResponseBuilder()
        .withSpeech(speechText)
        .withShouldEndSession(true)
        .build();
  }
}
