package org.gooru.gep.processor;

import org.gooru.gep.constants.Constants;
import org.json.JSONObject;

/**
 * @author mukul@gooru
 *  
 */
public final class MessageProcessorBuilder {
    private MessageProcessorBuilder() {
        throw new AssertionError();
    }

    public static MessageProcessor buildProcessor(JSONObject message, String op) {
    	switch (op) {
    	case Constants.Message.MSG_OP_XFORM_XAPI_EVENT:
    		return null; 
    	default:
    		return null;
    	}

    }

}
