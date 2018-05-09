package org.gooru.gep.responses.transformers;

import java.util.Map;
import org.json.JSONObject;

/**
 * @author ashish
 */
public interface ResponseTransformer {

    void transform();

    JSONObject transformedBody();

    Map<String, String> transformedHeaders();

    int transformedStatus();

}
