package org.gooru.gep.exceptions;

import org.gooru.gep.constants.Constants;
import org.gooru.gep.constants.HttpConstants;
import org.json.JSONObject;

/**
 * @author ashish
 */
public final class HttpResponseWrapperException extends RuntimeException {
    private final HttpConstants.HttpStatus status;
    private final JSONObject payload;

    public HttpResponseWrapperException(HttpConstants.HttpStatus status, JSONObject payload) {
        this.status = status;
        this.payload = payload;
    }

    public HttpResponseWrapperException(HttpConstants.HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.payload = new JSONObject().put(Constants.Message.MSG_MESSAGE, message);
    }

    public int getStatus() {
        return this.status.getCode();
    }

    public JSONObject getBody() {
        return this.payload;
    }
}
