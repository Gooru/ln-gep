package org.gooru.gep.processor.resource.events.creator;

import org.gooru.gep.processor.kafka.producer.KafkaMessagePublisher;
import org.gooru.gep.processor.resource.events.parser.ResourceEventConstants;
import org.gooru.gep.processor.resource.events.parser.ResourceEventObject;
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
public class QuestionScoreEventCreator implements ResourceEventCreator {
	
	private static final String TOPIC_QUESTION_SCORE = "org.gooru.da.sink.gep.question.score";
    private static final String QUESTION_SCORE = "usage.question.score";
    ResourceEventObject queEvent = new ResourceEventObject();

    public QuestionScoreEventCreator(ResourceEventObject queEvent) {
        this.queEvent = queEvent;
    }
    
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionScoreEventCreator.class);  

    
    @SuppressWarnings("rawtypes")
    public ExecutionResult<MessageResponse> createDiscreteEvent() {

        JSONObject dEvent = new JSONObject();
        JSONObject context = new JSONObject();
        JSONObject result = new JSONObject();
        
        dEvent.put(ResourceEventConstants.EventAttributes.USER_ID, queEvent.getUser());
        dEvent.put(ResourceEventConstants.EventAttributes.ACTIVITY_TIME, queEvent.getActivityTime() );
        dEvent.put(ResourceEventConstants.EventAttributes.RESOURCE_ID, queEvent.getResourceId());
        dEvent.put(ResourceEventConstants.EventAttributes.RESOURCE_TYPE, queEvent.getResourceType());
        dEvent.put(ResourceEventConstants.EventAttributes.EVENT_NAME, QUESTION_SCORE);
        dEvent.put(ResourceEventConstants.EventAttributes.EVENT_ID, queEvent.getEventId());
        
        //Retain Null Attributes so that we don't end up with Serialization issues at DAP.
        context.put(ResourceEventConstants.EventAttributes.CLASS_ID, (queEvent.getClassId() != null) ? queEvent.getClassId() : JSONObject.NULL);
        context.put(ResourceEventConstants.EventAttributes.COURSE_ID, (queEvent.getCourseId() != null) ? queEvent.getCourseId() : JSONObject.NULL);
        context.put(ResourceEventConstants.EventAttributes.UNIT_ID, (queEvent.getUnitId() != null) ? queEvent.getUnitId() : JSONObject.NULL);
        context.put(ResourceEventConstants.EventAttributes.LESSON_ID, (queEvent.getLessonId() != null) ? queEvent.getLessonId() : JSONObject.NULL);
        context.put(ResourceEventConstants.EventAttributes.CONTEXT_COLLECTION_ID, (queEvent.getContextCollectionId() != null) ? queEvent.getContextCollectionId() : JSONObject.NULL);
        context.put(ResourceEventConstants.EventAttributes.CONTEXT_COLLECTION_TYPE, (queEvent.getContextCollectionType() != null) ? queEvent.getContextCollectionType() : JSONObject.NULL);
        context.put(ResourceEventConstants.EventAttributes.CONTENT_SOURCE, (queEvent.getContentSource() != null) ? queEvent.getContentSource() : JSONObject.NULL);
        context.put(ResourceEventConstants.EventAttributes.SESSION_ID, queEvent.getSessionId());
        context.put(ResourceEventConstants.EventAttributes.COLLECTION_ID, queEvent.getCollectionId());
        context.put(ResourceEventConstants.EventAttributes.COLLECTION_TYPE, queEvent.getCollectionType());
        context.put(ResourceEventConstants.EventAttributes.PARTNER_ID, (queEvent.getPartnerId() != null) ? queEvent.getPartnerId() : JSONObject.NULL);
        context.put(ResourceEventConstants.EventAttributes.TENANT_ID, (queEvent.getTenantId() != null) ? queEvent.getTenantId() : JSONObject.NULL);

        context.put(ResourceEventConstants.EventAttributes.PATH_ID, queEvent.getPathId());
        context.put(ResourceEventConstants.EventAttributes.PATH_TYPE, (queEvent.getPathType() != null) ? queEvent.getPathType() : JSONObject.NULL);

        result.put(ResourceEventConstants.EventAttributes.SCORE, queEvent.getScore());      

        dEvent.put("context", context);
        dEvent.put("result", result);
        
        LOGGER.info("The transformed event is" + dEvent.toString(1));
        
        sendDiscreteEventtoKafka(dEvent);        
        
  //*************************************************************************************************************      
        
  	  return new ExecutionResult<>(MessageResponseFactory.createCreatedResponse(dEvent), ExecutionStatus.SUCCESSFUL);
  	  
    }
    
    private void sendDiscreteEventtoKafka(JSONObject discreteEvent) {
        //Getting LTI event and publishing into Kafka topic.

    	JSONObject dEvent = discreteEvent;
        try {
          KafkaMessagePublisher.getInstance().sendMessage2Kafka(TOPIC_QUESTION_SCORE, dEvent);
          LOGGER.info("Successfully dispatched Question Score event..");
        } catch (Exception e) {
          LOGGER.error("Error while dispatching Question Score event.. ", e);
        }
      }
    
    public boolean handlerReadOnly() {
        return true;
    }


}
