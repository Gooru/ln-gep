package org.gooru.gep.processor.collection.events.creator;

import org.gooru.gep.processor.collection.events.parser.CollectionEventConstants;
import org.gooru.gep.processor.collection.events.parser.CollectionEventObject;
import org.gooru.gep.processor.kafka.producer.KafkaMessagePublisher;
import org.gooru.gep.responses.ExecutionResult;
import org.gooru.gep.responses.MessageResponse;
import org.gooru.gep.responses.MessageResponseFactory;
import org.gooru.gep.responses.ExecutionResult.ExecutionStatus;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollectionStartEventCreator implements CollectionEventCreator {
	
	private static final String TOPIC_COLLECTION_TIMESPENT = "org.gooru.da.sink.gep.collection.start";
    private static final String COLLECTION_START = "usage.collection.start";
    CollectionEventObject collEvent = new CollectionEventObject();

    public CollectionStartEventCreator(CollectionEventObject collEvent) {
        this.collEvent = collEvent;
    }
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionScoreEventCreator.class);  
    
    @SuppressWarnings("rawtypes")
    public ExecutionResult<MessageResponse> createDiscreteEvent() {

        JSONObject dEvent = new JSONObject();
        JSONObject context = new JSONObject();
        
        dEvent.put(CollectionEventConstants.EventAttributes.USER_ID, collEvent.getUser());
        dEvent.put(CollectionEventConstants.EventAttributes.EVENT_NAME, COLLECTION_START);
        dEvent.put(CollectionEventConstants.EventAttributes.EVENT_ID, collEvent.getEventId());
        dEvent.put(CollectionEventConstants.EventAttributes.ACTIVITY_TIME, collEvent.getActivityTime());
        dEvent.put(CollectionEventConstants.EventAttributes.COLLECTION_TYPE, collEvent.getCollectionType());
        dEvent.put(CollectionEventConstants.EventAttributes.COLLECTION_ID, collEvent.getCollectionId());

        context.put(CollectionEventConstants.EventAttributes.CLASS_ID, (collEvent.getClassId() != null) ? collEvent.getClassId() : null);
        context.put(CollectionEventConstants.EventAttributes.COURSE_ID, (collEvent.getCourseId() != null) ? collEvent.getCourseId() : null);
        context.put(CollectionEventConstants.EventAttributes.UNIT_ID, (collEvent.getUnitId() != null) ? collEvent.getUnitId() : null);
        context.put(CollectionEventConstants.EventAttributes.LESSON_ID, (collEvent.getLessonId() != null) ? collEvent.getLessonId() : null);
        context.put(CollectionEventConstants.EventAttributes.SESSION_ID, collEvent.getSessionId());
        context.put(CollectionEventConstants.EventAttributes.PARTNER_ID, (collEvent.getPartnerId() != null) ? collEvent.getPartnerId() : null);
        context.put(CollectionEventConstants.EventAttributes.TENANT_ID, (collEvent.getTenantId() != null) ? collEvent.getTenantId() : null);
        context.put(CollectionEventConstants.EventAttributes.PATH_ID, collEvent.getPathId());
        
        dEvent.put("context", context);        
        
        LOGGER.info("The Collection Start event is " + dEvent.toString(1));
        
        sendDiscreteEventtoKafka(dEvent);        
        
  //*************************************************************************************************************      
        
  	  return new ExecutionResult<>(MessageResponseFactory.createCreatedResponse(dEvent), ExecutionStatus.SUCCESSFUL);
  	  
    }
    
    private void sendDiscreteEventtoKafka(JSONObject discreteEvent) {

    	JSONObject dEvent = discreteEvent;
        try {
          KafkaMessagePublisher.getInstance().sendMessage2Kafka(TOPIC_COLLECTION_TIMESPENT, dEvent);
          LOGGER.info("Successfully dispatched Collection Start event..");
        } catch (Exception e) {
          LOGGER.error("Error while dispatching Collection Start event.. ", e);
        }
      }

}
