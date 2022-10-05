package com.shervilg.spinboard.alexa.handlers;

import java.util.Optional;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import org.springframework.stereotype.Component;

@Component
public class LaunchRequestHandler implements RequestHandler {

  @Override
  public boolean canHandle(HandlerInput handlerInput) {
    return handlerInput.matches(Predicates.requestType(LaunchRequest.class));
  }

  @Override
  public Optional<Response> handle(HandlerInput handlerInput) {
    return handlerInput.getResponseBuilder()
        .withSpeech("Spinboard launched !")
        .withShouldEndSession(false)
        .build();
  }
}
