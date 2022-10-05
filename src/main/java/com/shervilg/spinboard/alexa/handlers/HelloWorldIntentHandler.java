package com.shervilg.spinboard.alexa.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;
import com.amazon.ask.model.Response;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.shervilg.spinboard.alexa.common.AlexaIntent;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldIntentHandler implements RequestHandler {

  @Override
  public boolean canHandle(HandlerInput handlerInput) {
    return handlerInput.matches(intentName(AlexaIntent.HELLO_WORLD_INTENT.getValue()));
  }

  @Override
  public Optional<Response> handle(HandlerInput handlerInput) {
    return handlerInput.getResponseBuilder()
        .withSpeech("Haale Haale !")
        .build();
  }
}
