package com.shervilg.spinboard.alexa.handlers;

import java.util.Optional;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import com.shervilg.spinboard.alexa.common.AlexaIntent;
import org.springframework.stereotype.Component;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;

@Component
public class CancelIntentHandler implements RequestHandler {

  @Override
  public boolean canHandle(HandlerInput handlerInput) {
    return handlerInput.matches(Predicates.intentName(AlexaIntent.CANCEL_INTENT.getValue()));
  }

  @Override
  public Optional<Response> handle(HandlerInput handlerInput) {
    return handlerInput.getResponseBuilder()
        .withSpeech("Command cancelled !")
        .withShouldEndSession(false)
        .build();
  }
}
