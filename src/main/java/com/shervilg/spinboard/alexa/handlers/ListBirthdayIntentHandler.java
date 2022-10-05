package com.shervilg.spinboard.alexa.handlers;

import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import com.shervilg.spinboard.entity.Birthday;
import com.shervilg.spinboard.service.BirthdayService;
import com.shervilg.spinboard.alexa.common.AlexaIntent;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import org.springframework.beans.factory.annotation.Autowired;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import org.springframework.stereotype.Component;

@Component
public class ListBirthdayIntentHandler implements RequestHandler {

  @Autowired
  private BirthdayService birthdayService;

  @Override
  public boolean canHandle(HandlerInput handlerInput) {
    return handlerInput.matches(Predicates.intentName(AlexaIntent.LIST_BDAY_INTENT.getValue()));
  }

  @Override
  public Optional<Response> handle(HandlerInput handlerInput) {
    List<Birthday> birthdays = birthdayService.getAllBirthdays();
    String speechText = getBirthdayString(birthdays);

    return handlerInput.getResponseBuilder()
        .withSpeech(speechText)
        .withShouldEndSession(false)
        .build();
  }

  private String getBirthdayString(List<Birthday> birthdays) {
    StringJoiner joiner = new StringJoiner("\n");

    birthdays.forEach(birthday -> {
      String temp = birthday.getFirstName() + " " + birthday.getLastName() + " ";
      temp += birthday.getDate() + " " + Month.of(birthday.getMonth());

      joiner.add(temp);
    });

    return joiner.toString();
  }
}
