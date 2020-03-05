package org.gooru.gep.processor.asdp;

import org.gooru.gep.processor.asdp.learners.compstatus.LearnersCompetencyStatusEventHandler;
import org.gooru.gep.processor.asdp.learners.extassessmentquestionscore.LearnersAssessmentExternalQuestionScoreEventHandler;
import org.gooru.gep.processor.asdp.learners.extassessmentscore.LearnersAssessmentExternalScoreEventHandler;
import org.json.JSONObject;

public final class AsdpEventHandlerBuilder {

  private AsdpEventHandlerBuilder() {
    throw new AssertionError();
  }

  public static AsdpEventHandler buildLearnersCompetencyStatusEventHandler(JSONObject message) {
    return new LearnersCompetencyStatusEventHandler(message);
  }

  public static AsdpEventHandler buildLearnersAssessmentExternalScoreEventHandler(
      JSONObject message) {
    return new LearnersAssessmentExternalScoreEventHandler(message);
  }

  public static AsdpEventHandler buildLearnersAssessmentExternalQuestionScoreEventHandler(
      JSONObject message) {
    return new LearnersAssessmentExternalQuestionScoreEventHandler(message);
  }
}
