package org.gooru.gep.processor.resource.events.creator;

import org.json.JSONObject;
import org.gooru.gep.processor.kafka.producer.KafkaMessagePublisher;
import org.gooru.gep.processor.resource.events.parser.ResourceEventConstants;
import org.gooru.gep.processor.resource.events.parser.ResourceEventObject;
import org.gooru.gep.responses.ExecutionResult;
import org.gooru.gep.responses.MessageResponse;
import org.gooru.gep.responses.MessageResponseFactory;
import org.gooru.gep.responses.ExecutionResult.ExecutionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mukul@gooru
 *  
 */
public class ResourceTimespentEventCreator implements ResourceEventCreator {
	
	private static final String TOPIC_RESOURCE_TIMESPENT = "org.gooru.da.sink.gep.resource.timespent";
    private static final String RESOURCE_TIMESPENT = "usage.resource.timespent";
    ResourceEventObject resEvent = new ResourceEventObject();

    public ResourceTimespentEventCreator(ResourceEventObject resEvent) {
        this.resEvent = resEvent;
    }
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceTimespentEventCreator.class);  

    
    @SuppressWarnings("rawtypes")
    public ExecutionResult<MessageResponse> createDiscreteEvent() {

        JSONObject dEvent = new JSONObject();
        JSONObject context = new JSONObject();
        JSONObject result = new JSONObject();
        
        dEvent.put(ResourceEventConstants.EventAttributes.USER_ID, resEvent.getUser());
        dEvent.put(ResourceEventConstants.EventAttributes.ACTIVITY_TIME, resEvent.getActivityTime() );
        dEvent.put(ResourceEventConstants.EventAttributes.RESOURCE_ID, resEvent.getResourceId());
        dEvent.put(ResourceEventConstants.EventAttributes.RESOURCE_TYPE, resEvent.getResourceType());
        dEvent.put(ResourceEventConstants.EventAttributes.EVENT_NAME, RESOURCE_TIMESPENT);
        dEvent.put(ResourceEventConstants.EventAttributes.EVENT_ID, resEvent.getEventId());
        
        //Retain Null Attributes so that we don't end up with Serialization issues at DAP
        context.put(ResourceEventConstants.EventAttributes.CLASS_ID, (resEvent.getClassId() != null) ? resEvent.getClassId() : JSONObject.NULL);
        context.put(ResourceEventConstants.EventAttributes.COURSE_ID, (resEvent.getCourseId() != null) ? resEvent.getCourseId() : JSONObject.NULL);
        context.put(ResourceEventConstants.EventAttributes.UNIT_ID, (resEvent.getUnitId() != null) ? resEvent.getUnitId() : JSONObject.NULL);
        context.put(ResourceEventConstants.EventAttributes.LESSON_ID, (resEvent.getLessonId() != null) ? resEvent.getLessonId() : JSONObject.NULL);
        context.put(ResourceEventConstants.EventAttributes.SESSION_ID, resEvent.getSessionId());
        context.put(ResourceEventConstants.EventAttributes.CONTEXT_COLLECTION_ID, (resEvent.getContextCollectionId() != null) ? resEvent.getContextCollectionId() : JSONObject.NULL);
        context.put(ResourceEventConstants.EventAttributes.CONTEXT_COLLECTION_TYPE, (resEvent.getContextCollectionType() != null) ? resEvent.getContextCollectionType() : JSONObject.NULL);
        context.put(ResourceEventConstants.EventAttributes.CONTENT_SOURCE, (resEvent.getContentSource() != null) ? resEvent.getContentSource() : JSONObject.NULL);
        context.put(ResourceEventConstants.EventAttributes.COLLECTION_ID, resEvent.getCollectionId());
        context.put(ResourceEventConstants.EventAttributes.COLLECTION_TYPE, resEvent.getCollectionType());
        context.put(ResourceEventConstants.EventAttributes.PARTNER_ID, (resEvent.getPartnerId() != null) ? resEvent.getPartnerId() : JSONObject.NULL);
        context.put(ResourceEventConstants.EventAttributes.TENANT_ID, (resEvent.getTenantId() != null) ? resEvent.getTenantId() : JSONObject.NULL);
        
        context.put(ResourceEventConstants.EventAttributes.PATH_ID, resEvent.getPathId());
        context.put(ResourceEventConstants.EventAttributes.PATH_TYPE, (resEvent.getPathType() != null) ? resEvent.getPathType() : JSONObject.NULL);

        result.put(ResourceEventConstants.EventAttributes.TIMESPENT, resEvent.getTimeSpent());      

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
          KafkaMessagePublisher.getInstance().sendMessage2Kafka(TOPIC_RESOURCE_TIMESPENT, dEvent);
          LOGGER.info("Successfully dispatched Resource Timespent event..");
        } catch (Exception e) {
          LOGGER.error("Error while dispatching Resource Timespent event.. ", e);
        }
      }
    
    public boolean handlerReadOnly() {
        return true;
    }

}
