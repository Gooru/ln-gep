package org.gooru.gep.processor.collection.events.parser;

import org.gooru.gep.processor.collection.events.creator.CollectionEventCreator;
import org.gooru.gep.responses.ExecutionResult;
import org.gooru.gep.responses.MessageResponse;
import org.gooru.gep.responses.MessageResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollectionStartEventHandler {
	
	
    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionStartEventHandler.class);

	   private CollectionStartEventHandler() {
	        throw new AssertionError();
	    }
	    
	    public static MessageResponse collectionStartEventCreate(CollectionEventCreator eventCreator) {     
	    	ExecutionResult<MessageResponse> executionResult = createCollectionStartEvent(eventCreator);
	    	return executionResult.result();

	    }
	    
	    private static ExecutionResult<MessageResponse> createCollectionStartEvent(CollectionEventCreator eventCreator) {
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
