package org.gooru.gep.processor.asdp.learners.compstatus;

import org.gooru.gep.processor.asdp.AsdpEventHandler;
import org.json.JSONObject;

public class LearnersCompetencyStatusEventHandler implements AsdpEventHandler {

  private static final String ACTIVITY_LEARNERS_COMPETENCY_STATUS_TOPIC =
      "org.gooru.da.sink.gep.activity.learners.competency.status";

  private final JSONObject message;


  public LearnersCompetencyStatusEventHandler(JSONObject message) {
    this.message = message;
  }

  @Override
  public JSONObject createDiscreteEvent() {
    return LearnersCompetencyStatusEventModel.builder(message);
  }


  @Override
  public String topic() {
    return ACTIVITY_LEARNERS_COMPETENCY_STATUS_TOPIC;
  }



}
