package org.gooru.gep.processor.resource.events.parser;

import org.gooru.gep.processor.resource.events.creator.ResourceEventCreator;
import org.gooru.gep.responses.ExecutionResult;
import org.gooru.gep.responses.MessageResponse;
import org.gooru.gep.responses.MessageResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author mukul@gooru
 *  
 */
public class ResourceTimeSpentEventHandler {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceTimeSpentEventHandler.class);

	   private ResourceTimeSpentEventHandler() {
	        throw new AssertionError();
	    }
	    
	    public static MessageResponse resourceTSEventCreate(ResourceEventCreator eventCreator) {     
	    	ExecutionResult<MessageResponse> executionResult = createResourceTSEvent(eventCreator);
	    	return executionResult.result();

	    }
	    
	    
	    private static ExecutionResult<MessageResponse> createResourceTSEvent(ResourceEventCreator eventCreator) {
	        ExecutionResult<MessageResponse> executionResult;

	        try {
	                 executionResult = eventCreator.createDiscreteEvent();  
	            return executionResult;
	        } catch (Throwable e) {
	            LOGGER.error("Caught exception, need to rollback and abort", e);
	            return new ExecutionResult<>(
	                MessageResponseFactory.createInternalErrorResponse("event.processing.error"),
	                ExecutionResult.ExecutionStatus.FAILED);
	        } 
	    }
}
