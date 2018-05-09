package org.gooru.gep.exceptions;

import org.gooru.gep.responses.MessageResponse;

/**
 * @author ashish
 */
public class MessageResponseWrapperException extends RuntimeException {
    private final MessageResponse response;

    public MessageResponseWrapperException(MessageResponse response) {
        this.response = response;
    }

    public MessageResponse getMessageResponse() {
        return this.response;
    }
}
