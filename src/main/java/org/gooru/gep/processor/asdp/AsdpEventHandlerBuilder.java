package org.gooru.gep.processor.asdp;

import org.gooru.gep.processor.asdp.learners.assessmentscore.LearnersAssessmentScoreEventHandler;
import org.gooru.gep.processor.asdp.learners.compstatus.LearnersCompetencyStatusEventHandler;
import org.json.JSONObject;

public final class AsdpEventHandlerBuilder {

  private AsdpEventHandlerBuilder() {
    throw new AssertionError();
  }

  public static AsdpEventHandler buildLearnersCompetencyStatusEventHandler(JSONObject message) {
    return new LearnersCompetencyStatusEventHandler(message);
  }


  public static AsdpEventHandler buildLearnersAssessmentScoreEventHandler(
      JSONObject message) {
    return new LearnersAssessmentScoreEventHandler(message);
  }
}
