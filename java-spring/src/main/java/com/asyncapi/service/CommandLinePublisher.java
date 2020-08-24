package com.asyncapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CommandLinePublisher implements CommandLineRunner {

    @Autowired
    PublisherService publisherService;

    @Override
    public void run(String... args) {
        System.out.println("******* Sending message: *******");
        publisherService.receiveLightMeasurement("Hello World from smartylighting/streetlights/1/0/event/{streetlightId}/lighting/measured");
            
        System.out.println("Message sent");
    }
}
