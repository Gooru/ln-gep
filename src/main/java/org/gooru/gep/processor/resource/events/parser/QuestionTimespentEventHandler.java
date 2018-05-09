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
public class QuestionTimespentEventHandler {
	
	
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionTimespentEventHandler.class);

	   private QuestionTimespentEventHandler() {
	        throw new AssertionError();
	    }
	    
	    public static MessageResponse questionTSEventCreate(ResourceEventCreator eventCreator) {     
	    	ExecutionResult<MessageResponse> executionResult = createQuestionTSEvent(eventCreator);
	    	return executionResult.result();

	    }
	    
	    
	    private static ExecutionResult<MessageResponse> createQuestionTSEvent(ResourceEventCreator eventCreator) {
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
