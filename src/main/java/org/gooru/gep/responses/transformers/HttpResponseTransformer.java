package org.gooru.gep.responses.transformers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.gooru.gep.constants.Constants;
//import io.vertx.core.eventbus.Message;
//import io.vertx.core.json.JsonObject;
import org.json.JSONObject;

/**
 * @author ashish
 */
class HttpResponseTransformer implements ResponseTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseTransformer.class);
    private final JSONObject message;
    private boolean transformed = false;
    private Map<String, String> headers;
    private int httpStatus;
    private JSONObject httpBody;

    public HttpResponseTransformer(JSONObject message) {
        this.message = message;
        if (message == null) {
            LOGGER.error("Invalid or null Message<JsonObject> for initialization");
            throw new IllegalArgumentException("Invalid or null Message<Object> for initialization");
        }
    }

    @Override
    public void transform() {
        if (!this.transformed) {
            processTransformation();
            this.transformed = true;
        }
    }

    @Override
    public JSONObject transformedBody() {
        transform();
        return this.httpBody;
    }

    @Override
    public Map<String, String> transformedHeaders() {
        transform();
        return this.headers;
    }

    @Override
    public int transformedStatus() {
        transform();
        return this.httpStatus;
    }

    private void processTransformation() {
        JSONObject messageBody = message;
        
        // First initialize the http status
        this.httpStatus = messageBody.getInt(Constants.Message.MSG_HTTP_STATUS);

        // Then initialize the headers
        processHeaders(messageBody);

        this.httpBody = messageBody.getJSONObject(Constants.Message.MSG_HTTP_BODY);
        this.transformed = true;
    }

    private void processHeaders(JSONObject jsonObject) {
        JSONObject jsonHeaders = jsonObject.getJSONObject(Constants.Message.MSG_HTTP_HEADERS);
        this.headers = new HashMap<>();
        if ((jsonHeaders != null) || (jsonHeaders.length() != 0)) {
            Map<String, Object> headerMap = jsonHeaders.toMap();
            for (Map.Entry<String, Object> entry : headerMap.entrySet()) {
                this.headers.put(entry.getKey(), entry.getValue().toString());
            }
        }
    }

}
