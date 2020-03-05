package org.gooru.gep.processor.asdp.learners.extassessmentscore;

import org.gooru.gep.processor.asdp.AsdpEventHandler;
import org.json.JSONObject;

public class LearnersAssessmentExternalScoreEventHandler implements AsdpEventHandler {

  private static final String ACTIVITY_LEARNERS_ASSESSMENT_SCORE =
      "org.gooru.da.sink.asdp.learners.competency.status";

  private final JSONObject message;

  public LearnersAssessmentExternalScoreEventHandler(JSONObject message) {
    this.message = message;
  }

  @Override
  public JSONObject createDiscreteEvent() {
    return LearnersAssessmentExternalScoreEventModel.builder(message);
  }


  @Override
  public String topic() {
    return ACTIVITY_LEARNERS_ASSESSMENT_SCORE;
  }

}
