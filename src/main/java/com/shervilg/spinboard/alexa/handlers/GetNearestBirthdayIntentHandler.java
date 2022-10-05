package com.shervilg.spinboard.alexa.handlers;

import java.time.Month;
import java.util.Optional;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.Predicates;
import com.shervilg.spinboard.entity.Birthday;
import org.springframework.stereotype.Component;
import com.shervilg.spinboard.service.BirthdayService;
import com.shervilg.spinboard.alexa.common.AlexaIntent;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class GetNearestBirthdayIntentHandler implements RequestHandler {

  @Autowired
  private BirthdayService birthdayService;

  @Override
  public boolean canHandle(HandlerInput handlerInput) {
    return handlerInput.matches(Predicates.intentName(AlexaIntent.NEAREST_BDAY_INTENT.getValue()));
  }

  @Override
  public Optional<Response> handle(HandlerInput handlerInput) {
    IntentRequest intentRequest = (IntentRequest) handlerInput.getRequestEnvelope().getRequest();
    Slot prioritySlot = intentRequest.getIntent().getSlots().get("priority");

    Birthday nearestBirthday = null;
    if (prioritySlot != null) {
      nearestBirthday = birthdayService.getNearestBirthday(prioritySlot.getValue().substring(1));
    } else {
      nearestBirthday = birthdayService.getNearestBirthday();
    }

    String speechText = (nearestBirthday == null) ? "No nearest Birthday !"
        : nearestBirthday.getFirstName() + " " + nearestBirthday.getLastName() + " " + nearestBirthday.getDate()
          + Month.of(nearestBirthday.getMonth());

    return handlerInput.getResponseBuilder()
        .withSpeech(speechText)
        .withShouldEndSession(false)
        .build();
  }
}
