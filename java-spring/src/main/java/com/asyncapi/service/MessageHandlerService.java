package com.asyncapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageHandlerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandlerService.class);

    
      
    
      
    
    public void handleTurnOn(Message<?> message) {
        LOGGER.info("handler smartylighting/streetlights/1/0/action/{streetlightId}/turn/on");
        LOGGER.info(String.valueOf(message.getPayload().toString()));
    }
      
    
      
    
    public void handleTurnOff(Message<?> message) {
        LOGGER.info("handler smartylighting/streetlights/1/0/action/{streetlightId}/turn/off");
        LOGGER.info(String.valueOf(message.getPayload().toString()));
    }
      
    
      
    
    public void handleDimLight(Message<?> message) {
        LOGGER.info("handler smartylighting/streetlights/1/0/action/{streetlightId}/dim");
        LOGGER.info(String.valueOf(message.getPayload().toString()));
    }
      
    

}
