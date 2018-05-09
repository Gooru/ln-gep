package org.gooru.gep.processor.resource.events.creator;

import org.gooru.gep.responses.ExecutionResult;
import org.gooru.gep.responses.MessageResponse;

/**
 * @author mukul@gooru
 *  
 */
public interface ResourceEventCreator {

	ExecutionResult<MessageResponse>createDiscreteEvent();
		

}
