package org.gooru.gep.processor.asdp.learners.assessmentscore;

import org.gooru.gep.processor.asdp.AsdpEventHandler;
import org.json.JSONObject;

public class LearnersAssessmentScoreEventHandler implements AsdpEventHandler {

  private static final String ACTIVITY_LEARNERS_ASSESSMENT_SCORE =
      "org.gooru.da.sink.gep.activity.learners.assessment.score";

  private final JSONObject message;

  public LearnersAssessmentScoreEventHandler(JSONObject message) {
    this.message = message;
  }

  @Override
  public JSONObject createDiscreteEvent() {
    return LearnersAssessmentScoreEventModel.builder(message);
  }


  @Override
  public String topic() {
    return ACTIVITY_LEARNERS_ASSESSMENT_SCORE;
  }

}
