package com.asyncapi.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface PublisherService {

    
        
    /**
     * The topic on which measured values may be produced and consumed.
     */
    @Gateway(requestChannel = "receiveLightMeasurementOutboundChannel")
    void receiveLightMeasurement(String data);
        
    
        
    
        
    
        
    
}
