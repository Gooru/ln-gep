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
public class AssessmentScoreEventCreator implements CollectionEventCreator {
	
	private static final String TOPIC_ASSESSMENT_SCORE = "org.gooru.da.sink.gep.assessment.score";
    private static final String ASSESSMENT_SCORE = "usage.assessment.score";
    CollectionEventObject collEvent = new CollectionEventObject();

    public AssessmentScoreEventCreator(CollectionEventObject collEvent) {
        this.collEvent = collEvent;
    }
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AssessmentScoreEventCreator.class);  
    
    @SuppressWarnings("rawtypes")
    public ExecutionResult<MessageResponse> createDiscreteEvent() {

        JSONObject dEvent = new JSONObject();
        JSONObject context = new JSONObject();
        JSONObject result = new JSONObject();
        
        dEvent.put(CollectionEventConstants.EventAttributes.USER_ID, collEvent.getUser());
        dEvent.put(CollectionEventConstants.EventAttributes.EVENT_NAME, ASSESSMENT_SCORE);
        dEvent.put(CollectionEventConstants.EventAttributes.EVENT_ID, collEvent.getEventId());
        dEvent.put(CollectionEventConstants.EventAttributes.ACTIVITY_TIME, collEvent.getActivityTime());
        dEvent.put(CollectionEventConstants.EventAttributes.COLLECTION_TYPE, collEvent.getCollectionType());
        dEvent.put(CollectionEventConstants.EventAttributes.COLLECTION_ID, collEvent.getCollectionId());
        
        //Retain Null Attributes so that we don't end up with Serialization issues at DAP.
        context.put(CollectionEventConstants.EventAttributes.CLASS_ID, (collEvent.getClassId() != null) ? collEvent.getClassId() : JSONObject.NULL);
        context.put(CollectionEventConstants.EventAttributes.COURSE_ID, (collEvent.getCourseId() != null) ? collEvent.getCourseId() : JSONObject.NULL);
        context.put(CollectionEventConstants.EventAttributes.UNIT_ID, (collEvent.getUnitId() != null) ? collEvent.getUnitId() : JSONObject.NULL);
        context.put(CollectionEventConstants.EventAttributes.LESSON_ID, (collEvent.getLessonId() != null) ? collEvent.getLessonId() : JSONObject.NULL);
        context.put(CollectionEventConstants.EventAttributes.CONTEXT_COLLECTION_ID, (collEvent.getContextCollectionId() != null) ? collEvent.getContextCollectionId() : JSONObject.NULL);
        context.put(CollectionEventConstants.EventAttributes.CONTEXT_COLLECTION_TYPE, (collEvent.getContextCollectionType() != null) ? collEvent.getContextCollectionType() : JSONObject.NULL);
        context.put(CollectionEventConstants.EventAttributes.CONTENT_SOURCE, (collEvent.getContentSource() != null) ? collEvent.getContentSource() : JSONObject.NULL);
        context.put(CollectionEventConstants.EventAttributes.SESSION_ID, collEvent.getSessionId());
        context.put(CollectionEventConstants.EventAttributes.PARTNER_ID, (collEvent.getPartnerId() != null) ? collEvent.getPartnerId() : JSONObject.NULL);
        context.put(CollectionEventConstants.EventAttributes.TENANT_ID, (collEvent.getTenantId() != null) ? collEvent.getTenantId() : JSONObject.NULL);
        context.put(CollectionEventConstants.EventAttributes.PATH_ID, collEvent.getPathId());
        context.put(CollectionEventConstants.EventAttributes.PATH_TYPE, (collEvent.getPathType() != null) ? collEvent.getPathType() : JSONObject.NULL);
        
        context.put(CollectionEventConstants.EventAttributes.QUESTION_COUNT, collEvent.getQuestionCount());
        context.put(CollectionEventConstants.EventAttributes.ADDITIONAL_CONTEXT, (collEvent.getAdditionalContext() != null) ? collEvent.getAdditionalContext() : JSONObject.NULL);
        
        result.put(CollectionEventConstants.EventAttributes.SCORE, collEvent.getScore());       
        
        dEvent.put("context", context);
        dEvent.put("result", result);
        
        LOGGER.info("The transformed event is" + dEvent.toString(1));
        
        sendDiscreteEventtoKafka(dEvent);        
        
  //*************************************************************************************************************      
        
  	  return new ExecutionResult<>(MessageResponseFactory.createCreatedResponse(dEvent), ExecutionStatus.SUCCESSFUL);
        
  	  
    }
    
    private void sendDiscreteEventtoKafka(JSONObject discreteEvent) {

    	JSONObject dEvent = discreteEvent;
        try {
          KafkaMessagePublisher.getInstance().sendMessage2Kafka(TOPIC_ASSESSMENT_SCORE, dEvent);
          LOGGER.info("Successfully dispatched Assessment Score event..");
        } catch (Exception e) {
          LOGGER.error("Error while dispatching Assessment Score event.. ", e);
        }
      }


}
