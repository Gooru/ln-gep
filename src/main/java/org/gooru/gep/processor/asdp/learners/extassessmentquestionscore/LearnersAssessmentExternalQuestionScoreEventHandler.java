package org.gooru.gep.processor.asdp.learners.extassessmentquestionscore;

import org.gooru.gep.processor.asdp.AsdpEventHandler;
import org.json.JSONObject;

public class LearnersAssessmentExternalQuestionScoreEventHandler implements AsdpEventHandler {

  private static final String ACTIVITY_LEARNERS_QUESTION_SCORE =
      "org.gooru.da.sink.asdp.learners.competency.status";
  
  private final JSONObject message;

  public LearnersAssessmentExternalQuestionScoreEventHandler(JSONObject message) {
    this.message = message;
  }

  @Override
  public JSONObject createDiscreteEvent() {
    return LearnersAssessmentExternalQuestionScoreEventModel.builder(message);
  }


  @Override
  public String topic() {
    return ACTIVITY_LEARNERS_QUESTION_SCORE;
  }

}
