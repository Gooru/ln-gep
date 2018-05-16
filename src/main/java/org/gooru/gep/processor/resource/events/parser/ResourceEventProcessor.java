package org.gooru.gep.processor.resource.events.parser;

import org.gooru.gep.processor.MessageProcessor;
import org.gooru.gep.processor.resource.events.creator.ResourceEventCreatorBuilder;
import org.gooru.gep.responses.MessageResponse;
import org.gooru.gep.responses.MessageResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;
import java.io.IOException;

/**
 * @author mukul@gooru
 *  
 */
public class ResourceEventProcessor implements MessageProcessor {

    private final JSONObject message;
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceEventProcessor.class);    

    public ResourceEventProcessor(JSONObject message) {
        this.message = message;
    }

    @SuppressWarnings("rawtypes")
	@Override
	public MessageResponse process() {
    	
    	ResourceEventObject resEvent = new ResourceEventObject();
    	MessageResponse result;

    	try {            
    		try {
    			resEvent = ResourceEventObject.builder(message);
    		} catch (Exception e) {
    			e.printStackTrace();
    			return MessageResponseFactory.createInternalErrorResponse();
    		}

    		try {    			
    			if (resEvent.getResourceType().equals(ResourceEventConstants.EventAttributes.RESOURCE)) {
        			result = ResourceTimeSpentEventHandler.resourceTSEventCreate
        					(ResourceEventCreatorBuilder.buildResourceTimespentEventCreator(resEvent));            	
        			LOGGER.info("Resource Timespent Event Successfully Dispatched" +  result.reply().getJSONObject("http.body").toString(1));
    			} else {
    				if (!resEvent.getEventName().equalsIgnoreCase(ResourceEventConstants.EventAttributes.RES_SCORE_UPDATE_EVENT)){
            			result = QuestionTimespentEventHandler.questionTSEventCreate
            					(ResourceEventCreatorBuilder.buildQuestionTimespentEventCreator(resEvent));
            			LOGGER.info("Question Timespent Event Successfully Dispatched" +  result.reply().getJSONObject("http.body").toString(1));    					
    				}
        			result = QuestionScoreEventHandler.queScoreEventCreate
        					(ResourceEventCreatorBuilder.buildQuestionScoreEventCreator(resEvent));    				
        			LOGGER.info("Question Score Event Successfully Dispatched" +  result.reply().getJSONObject("http.body").toString(1));
    			}

    			    			
    		} catch (Exception e) {
    			LOGGER.warn("Exception in processing Resource Events", e);    			
    			return MessageResponseFactory.createInternalErrorResponse();
    		}
    	} catch (Throwable e) {
    		LOGGER.warn("Encountered error while processing", e);
    		return MessageResponseFactory.createInternalErrorResponse();
    	}

    	return result;
    }

}
