package org.gooru.gep.processor.asdp;

import org.gooru.gep.processor.MessageProcessor;
import org.gooru.gep.processor.kafka.producer.KafkaMessagePublisher;
import org.gooru.gep.responses.MessageResponse;
import org.gooru.gep.responses.MessageResponseFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsdpEventProcessor implements MessageProcessor {

  private final JSONObject message;
  private static final Logger LOGGER = LoggerFactory.getLogger(AsdpEventProcessor.class);
  private static final String EVENT_NAME = "eventName";

  public AsdpEventProcessor(JSONObject message) {
    this.message = message;
  }

  @Override
  public MessageResponse process() {
    final String eventName = message.getString(EVENT_NAME);
    LOGGER.debug("ASDP EVENT :: {}", eventName);
    AsdpEventHandler asdpEventHandler =  AsdpEventProcessorBuilder.lookupBuilder(eventName).build(message);
    JSONObject asdpEvent = asdpEventHandler.createDiscreteEvent();
    String topic = asdpEventHandler.topic();
    sendDiscreteEventtoKafka(eventName, topic, asdpEvent);
    return MessageResponseFactory.createCreatedResponse(asdpEvent);
  }

  private void sendDiscreteEventtoKafka(String eventName, String topic, JSONObject asdpEvent) {

    try {
      KafkaMessagePublisher.getInstance().sendMessage2Kafka(topic, asdpEvent);
      LOGGER.info("Successfully dispatched asdp event.. {}", eventName);
    } catch (Exception e) {
      LOGGER.error("Error while dispatching asdp event.. {} :: error :: {}", eventName, e);
    }
  }
}
