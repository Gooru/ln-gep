package org.gooru.gep.responses;

import org.gooru.gep.constants.Constants;
import org.gooru.gep.constants.HttpConstants;
import org.json.JSONObject;

/**
 * @author ashish 
 */
public final class MessageResponseFactory {

    private static final String API_VERSION_DEPRECATED = "API version is deprecated";

    private MessageResponseFactory() {
        throw new AssertionError();
    }

    public static MessageResponse createInvalidRequestResponse() {
        return new MessageResponse.Builder().setStatusBadRequest()
            .setResponseBody(new JSONObject().put(Constants.Message.MSG_MESSAGE, "Invalid request")).build();
    }

    public static MessageResponse createForbiddenResponse() {
        return new MessageResponse.Builder().setStatusForbidden()
            .setResponseBody(new JSONObject().put(Constants.Message.MSG_MESSAGE, "Forbidden")).build();
    }

    public static MessageResponse createInternalErrorResponse() {
        return new MessageResponse.Builder().setStatusInternalError()
            .setResponseBody(new JSONObject().put(Constants.Message.MSG_MESSAGE, "Internal error")).build();
    }

    public static MessageResponse createInvalidRequestResponse(String message) {
        return new MessageResponse.Builder().setStatusBadRequest()
            .setResponseBody(new JSONObject().put(Constants.Message.MSG_MESSAGE, message)).build();
    }

    public static MessageResponse createForbiddenResponse(String message) {
        return new MessageResponse.Builder().setStatusForbidden()
            .setResponseBody(new JSONObject().put(Constants.Message.MSG_MESSAGE, message)).build();
    }

    public static MessageResponse createInternalErrorResponse(String message) {
        return new MessageResponse.Builder().setStatusInternalError()
            .setResponseBody(new JSONObject().put(Constants.Message.MSG_MESSAGE, message)).build();
    }

    public static MessageResponse createNotFoundResponse(String message) {
        return new MessageResponse.Builder().setStatusNotFound()
            .setResponseBody(new JSONObject().put(Constants.Message.MSG_MESSAGE, message)).build();
    }

    public static MessageResponse createOkayResponse(JSONObject body) {
        return new MessageResponse.Builder().setStatusOkay().setResponseBody(body).build();
    }

    public static MessageResponse createOkayStringResponse(String body) {
        return new MessageResponse.Builder().setStatusOkay().setStringResponseBody(body).build();
    }

    
    public static MessageResponse createCreatedResponse(String location) {
        return new MessageResponse.Builder().setStatusCreated().setHeader(HttpConstants.HEADER_LOCATION, location)
            .build();
    }

    public static MessageResponse createNoContentResponse() {
        return new MessageResponse.Builder().setStatusNoOutput().setResponseBody(new JSONObject()).build();
    }

    public static MessageResponse createVersionDeprecatedResponse() {
        return new MessageResponse.Builder().setStatusHttpCode(HttpConstants.HttpStatus.GONE).setContentTypeJson()
            .setResponseBody(new JSONObject().put(Constants.Message.MSG_MESSAGE, API_VERSION_DEPRECATED)).build();
    }
    
    public static MessageResponse createOkayResponse() {
        return new MessageResponse.Builder().setStatusOkay().build();
    }
    
    public static MessageResponse createCreatedResponse(JSONObject responseBody) {
        return new MessageResponse.Builder().setStatusOkay().setContentTypeJson()
            .setResponseBody(responseBody).build();
    }

}
