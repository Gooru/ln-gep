package org.gooru.gep.responses.transformers;

import java.util.Collections;
import java.util.Map;

import org.gooru.gep.exceptions.HttpResponseWrapperException;
import org.json.JSONObject;

/**
 * 
 * @author ashish 
 */
public final class HttpResponseWrapperExceptionTransformer implements ResponseTransformer {

    private final HttpResponseWrapperException ex;

    HttpResponseWrapperExceptionTransformer(HttpResponseWrapperException ex) {
        this.ex = ex;
    }

    @Override
    public void transform() {
        // no op
    }

    @Override
    public JSONObject transformedBody() {
        return ex.getBody();
    }

    @Override
    public Map<String, String> transformedHeaders() {
        return Collections.emptyMap();
    }

    @Override
    public int transformedStatus() {
        return ex.getStatus();
    }
}
