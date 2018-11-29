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


/**
 * @author mukul@gooru
 *  
 */
public class CollectionTimespentEventCreator implements CollectionEventCreator {
	
	private static final String TOPIC_COLLECTION_TIMESPENT = "org.gooru.da.sink.gep.collection.timespent";
    private static final String COLLECTION_TIMESPENT = "usage.collection.timespent";
    CollectionEventObject collEvent = new CollectionEventObject();

    public CollectionTimespentEventCreator(CollectionEventObject collEvent) {
        this.collEvent = collEvent;
    }
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionTimespentEventCreator.class);  
    
    @SuppressWarnings("rawtypes")
    public ExecutionResult<MessageResponse> createDiscreteEvent() {

        JSONObject dEvent = new JSONObject();
        JSONObject context = new JSONObject();
        JSONObject result = new JSONObject();
        
        dEvent.put(CollectionEventConstants.EventAttributes.USER_ID, collEvent.getUser());
        dEvent.put(CollectionEventConstants.EventAttributes.EVENT_NAME, COLLECTION_TIMESPENT);
        dEvent.put(CollectionEventConstants.EventAttributes.EVENT_ID, collEvent.getEventId());
        dEvent.put(CollectionEventConstants.EventAttributes.ACTIVITY_TIME, collEvent.getActivityTime());
        dEvent.put(CollectionEventConstants.EventAttributes.COLLECTION_TYPE, collEvent.getCollectionType());
        dEvent.put(CollectionEventConstants.EventAttributes.COLLECTION_ID, collEvent.getCollectionId());

        //Retain Null Attributes so that we don't end up with Serialization issues at DAP
        context.put(CollectionEventConstants.EventAttributes.CLASS_ID, (collEvent.getClassId() != null) ? collEvent.getClassId() : JSONObject.NULL);
        context.put(CollectionEventConstants.EventAttributes.COURSE_ID, (collEvent.getCourseId() != null) ? collEvent.getCourseId() : JSONObject.NULL);
        context.put(CollectionEventConstants.EventAttributes.UNIT_ID, (collEvent.getUnitId() != null) ? collEvent.getUnitId() : JSONObject.NULL);
        context.put(CollectionEventConstants.EventAttributes.LESSON_ID, (collEvent.getLessonId() != null) ? collEvent.getLessonId() : JSONObject.NULL);
        context.put(CollectionEventConstants.EventAttributes.CONTEXT_COLLECTION_ID, (collEvent.getContextCollectionId() != null) ? collEvent.getContextCollectionId() : JSONObject.NULL);
        context.put(CollectionEventConstants.EventAttributes.CONTEXT_COLLECTION_TYPE, (collEvent.getContextCollectionType() != null) ? collEvent.getContextCollectionType() : JSONObject.NULL);
        context.put(CollectionEventConstants.EventAttributes.SESSION_ID, collEvent.getSessionId());
        context.put(CollectionEventConstants.EventAttributes.PARTNER_ID, (collEvent.getPartnerId() != null) ? collEvent.getPartnerId() : JSONObject.NULL);
        context.put(CollectionEventConstants.EventAttributes.TENANT_ID, (collEvent.getTenantId() != null) ? collEvent.getTenantId() : JSONObject.NULL);
        context.put(CollectionEventConstants.EventAttributes.PATH_ID, collEvent.getPathId());
        context.put(CollectionEventConstants.EventAttributes.PATH_TYPE, (collEvent.getPathType() != null) ? collEvent.getPathType() : JSONObject.NULL);
        context.put(CollectionEventConstants.EventAttributes.QUESTION_COUNT, collEvent.getQuestionCount());
        
        result.put(CollectionEventConstants.EventAttributes.TIMESPENT, collEvent.getTimeSpent());
        
        dEvent.put("context", context);
        dEvent.put("result", result);
        
        LOGGER.info("The Collection Timespent event is " + dEvent.toString(1));
        
        sendDiscreteEventtoKafka(dEvent);        
        
  //*************************************************************************************************************      
        
  	  return new ExecutionResult<>(MessageResponseFactory.createCreatedResponse(dEvent), ExecutionStatus.SUCCESSFUL);
  	  
    }
    
    private void sendDiscreteEventtoKafka(JSONObject discreteEvent) {

    	JSONObject dEvent = discreteEvent;
        try {
          KafkaMessagePublisher.getInstance().sendMessage2Kafka(TOPIC_COLLECTION_TIMESPENT, dEvent);
          LOGGER.info("Successfully dispatched Collection TimeSpent event..");
        } catch (Exception e) {
          LOGGER.error("Error while dispatching Collection TimeSpent event.. ", e);
        }
      }

}
