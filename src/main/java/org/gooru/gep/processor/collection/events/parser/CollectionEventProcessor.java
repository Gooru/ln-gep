package org.gooru.gep.processor.collection.events.parser;

import org.gooru.gep.processor.MessageProcessor;
import org.gooru.gep.processor.collection.events.creator.CollectionEventCreatorBuilder;
import org.gooru.gep.processor.collection.events.parser.CollectionEventConstants.EventAttributes;
import org.gooru.gep.responses.MessageResponse;
import org.gooru.gep.responses.MessageResponseFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author mukul@gooru
 *  
 */
public class CollectionEventProcessor implements MessageProcessor {
	
    private final JSONObject message;
    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionEventProcessor.class);    

    public CollectionEventProcessor(JSONObject message) {
        this.message = message;
    }

    @SuppressWarnings("rawtypes")
	@Override
	public MessageResponse process() {
    	
    	CollectionEventObject collEvent = new CollectionEventObject();
    	MessageResponse result;

    	try {            
    		try {
    			collEvent = CollectionEventObject.builder(message);
    		} catch (Exception e) {
    			e.printStackTrace();
    			return MessageResponseFactory.createInternalErrorResponse();
    		}

    		try {
    			result = CollectionTimespentEventHandler.collectionTSEventCreate
    					(CollectionEventCreatorBuilder.buildCollectionTimespentEventCreator(collEvent));            	
    			LOGGER.info("Collection Score Event Successfully Dispatched" +  result.reply().getJSONObject("http.body").toString());    			
    			if (collEvent.getCollectionType().equals(CollectionEventConstants.EventAttributes.COLLECTION) ||
    					collEvent.getCollectionType().equals(CollectionEventConstants.EventAttributes.EXT_COLLECTION)) {
        			result = CollectionScoreEventHandler.collectionScoreEventCreate
        					(CollectionEventCreatorBuilder.buildCollectionScoreEventCreator(collEvent));            	
        			LOGGER.info("Collection Score Event Successfully Dispatched" +  result.reply().getJSONObject("http.body").toString());    				
    			} else if (collEvent.getCollectionType().equals(CollectionEventConstants.EventAttributes.ASSESSMENT) ||
    					collEvent.getCollectionType().equals(CollectionEventConstants.EventAttributes.EXT_ASSESSMENT)) {
        			//TODO: OPEN Ended Question: What happens when the score for the Assessment is NULL (when All questions are OE questions and are not yet graded)
        			result = AssessmentScoreEventHandler.assessmentScoreEventCreate
        					(CollectionEventCreatorBuilder.buildAssessmentScoreEventCreator(collEvent));            	
        			LOGGER.info("Assessment Score Event Successfully Dispatched" + result.reply().getJSONObject("http.body").toString());    				
    			}
    			
    		} catch (Exception e) {
    			LOGGER.warn("Exception in processing Collection Events", e);    			
    			return MessageResponseFactory.createInternalErrorResponse();
    		}
    	} catch (Throwable e) {
    		LOGGER.warn("Encountered error while processing Collection Events", e);
    		return MessageResponseFactory.createInternalErrorResponse();
    	}

    	return result;
    }


}
