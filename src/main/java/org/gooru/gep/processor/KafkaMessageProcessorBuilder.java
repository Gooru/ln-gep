package org.gooru.gep.processor;

import org.gooru.gep.constants.Constants;
import org.gooru.gep.processor.asdp.AsdpEventProcessor;
import org.gooru.gep.processor.collection.events.parser.CollectionEventProcessor;
import org.gooru.gep.processor.resource.events.parser.ResourceEventProcessor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mukul@gooru
 * 
 */
public class KafkaMessageProcessorBuilder {

  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaMessageProcessorBuilder.class);

  private KafkaMessageProcessorBuilder() {
    throw new AssertionError();
  }

  public static MessageProcessor buildKafkaProcessor(JSONObject message) {

    final String eventName = message.getString("eventName");
    switch (eventName) {
      case Constants.Event.RESOURCE_PERFORMANCE:
        return new ResourceEventProcessor(message);
      case Constants.Event.COLLECTION_START:
        return new CollectionEventProcessor(message);
      case Constants.Event.COLLECTION_PERFORMANCE:
        return new CollectionEventProcessor(message);
      case Constants.Event.COLL_SCORE_UPDATE_EVENT:
        return new CollectionEventProcessor(message);
      case Constants.Event.RES_SCORE_UPDATE_EVENT:
        return new ResourceEventProcessor(message);
      case Constants.Event.ACTIVITY_LEARNERS_COMPETENCY_STATUS:
      case Constants.Event.ACTIVITY_LEARNERS_ASSESSMENT_SCORE:
      case Constants.Event.ACTIVITY_LEARNERS_QUESTION_SCORE:
        return new AsdpEventProcessor(message);
      default:
        LOGGER.error("Invalid operation type passed in, not able to handle");
        return null;
    }



  }

}
