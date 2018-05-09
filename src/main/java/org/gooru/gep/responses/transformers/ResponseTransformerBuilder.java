package org.gooru.gep.responses.transformers;

import org.gooru.gep.exceptions.HttpResponseWrapperException;
import org.json.JSONObject;

/**
 * @author ashish
 */
public final class ResponseTransformerBuilder {

	public static ResponseTransformer build(JSONObject message) {
        return new HttpResponseTransformer(message);
    }

    public static ResponseTransformer buildHttpResponseWrapperExceptionBuild(HttpResponseWrapperException ex) {
        return new HttpResponseWrapperExceptionTransformer(ex);
    }

    private ResponseTransformerBuilder() {
        throw new AssertionError();
    }
}
