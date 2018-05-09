package org.gooru.gep.processor.collection.events.creator;

import org.gooru.gep.responses.ExecutionResult;
import org.gooru.gep.responses.MessageResponse;


/**
 * @author mukul@gooru
 *  
 */
public interface CollectionEventCreator {

	ExecutionResult<MessageResponse> createDiscreteEvent();

}
