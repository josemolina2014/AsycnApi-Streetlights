package com.asyncapi;

 
import com.asyncapi.model.LightMeasuredPayload;
       
   
import com.asyncapi.model.TurnOnOffPayload;
  
import com.asyncapi.model.TurnOnOffPayload;
  
import com.asyncapi.model.DimLightPayload;
 
import com.asyncapi.service.PublisherService;
import org.eclipse.paho.client.mqttv3.*;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Example of tests for mqtt based on testcontainers library
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestcontainerMqttTest {

    
    @Value("${mqtt.topic.receiveLightMeasurement}")
    private String receiveLightMeasurementTopic;
    
    @Value("${mqtt.topic.turnOn}")
    private String turnOnTopic;
    
    @Value("${mqtt.topic.turnOff}")
    private String turnOffTopic;
    
    @Value("${mqtt.topic.dimLight}")
    private String dimLightTopic;
    

    @ClassRule
    public static GenericContainer mosquitto = new GenericContainer("eclipse-mosquitto").withExposedPorts(1883);
    
    @Autowired
    private PublisherService publisherService;
    
    private IMqttClient publisher;

    @DynamicPropertySource
    public static void mqttProperties(DynamicPropertyRegistry registry) {
        String address = "tcp://" + mosquitto.getContainerIpAddress() + mosquitto.getMappedPort(1883);
        registry.add("mqtt.broker.address", () -> address);
    }

    @BeforeEach
    public void before() throws MqttException {
        String address = "tcp://" + mosquitto.getContainerIpAddress() + mosquitto.getMappedPort(1883);
        publisher = new MqttClient(address, UUID.randomUUID().toString());
        publisher.connect();
    }

    @AfterEach
    public void after() throws MqttException {
        publisher.disconnect();
    }

     
    @Test
    public void receiveLightMeasurementProducerTestcontainers() throws MqttException {
        LightMeasuredPayload payload = new LightMeasuredPayload();

        List<MqttMessage> receivedMessages = new ArrayList<>();
        publisher.subscribe(receiveLightMeasurementTopic, (topic, message) -> {
            receivedMessages.add(message);
        });

        publisherService.receiveLightMeasurement(payload.toString());

        MqttMessage message = receivedMessages.get(receivedMessages.size() - 1);

        assertEquals("Message is wrong", payload.toString().getBytes(), message.getPayload());
    }
     
      
    @Test
    public void turnOnConsumerTestcontainers() throws Exception {
        TurnOnOffPayload payload = new TurnOnOffPayload();

        sendMessage(turnOnTopic, payload.toString().getBytes());

        Thread.sleep(1_000);
    }
    
      
    @Test
    public void turnOffConsumerTestcontainers() throws Exception {
        TurnOnOffPayload payload = new TurnOnOffPayload();

        sendMessage(turnOffTopic, payload.toString().getBytes());

        Thread.sleep(1_000);
    }
    
      
    @Test
    public void dimLightConsumerTestcontainers() throws Exception {
        DimLightPayload payload = new DimLightPayload();

        sendMessage(dimLightTopic, payload.toString().getBytes());

        Thread.sleep(1_000);
    }
    
    
    
    protected void sendMessage(String topic, byte[] message) throws Exception {
        publisher.publish(topic, new MqttMessage(message));
    }
    
}
