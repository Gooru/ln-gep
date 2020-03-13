package org.gooru.gep.processor.asdp;

import org.json.JSONObject;

public interface AsdpEventHandler {

  JSONObject createDiscreteEvent();

  String topic();

}
