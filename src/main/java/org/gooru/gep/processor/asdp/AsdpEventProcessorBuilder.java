package org.gooru.gep.processor.asdp;

import java.util.HashMap;
import java.util.Map;
import org.gooru.gep.constants.Constants;
import org.gooru.gep.constants.HttpConstants;
import org.gooru.gep.exceptions.HttpResponseWrapperException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum AsdpEventProcessorBuilder {


  DEFAULT("default") {
    private final Logger LOGGER = LoggerFactory.getLogger(AsdpEventProcessorBuilder.class);

    @Override
    public AsdpEventHandler build(JSONObject message) {
      LOGGER.error("Invalid event name operation type passed in, not able to handle");
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.ERROR,
          "Invalid Event Name operation");
    }

  },
  ACTIVITY_LEARNERS_COMPETENCY_STATUS(Constants.Event.ACTIVITY_LEARNERS_COMPETENCY_STATUS) {

    @Override
    public AsdpEventHandler build(JSONObject message) {
      return AsdpEventHandlerBuilder.buildLearnersCompetencyStatusEventHandler(message);
    }
  },
  ACTIVITY_LEARNERS_ASSESSMENT_SCORE(Constants.Event.ACTIVITY_LEARNERS_ASSESSMENT_SCORE) {

    @Override
    public AsdpEventHandler build(JSONObject message) {
      return AsdpEventHandlerBuilder.buildLearnersAssessmentExternalScoreEventHandler(message);
    }
  },
  ACTIVITY_LEARNERS_QUESTION_SCORE(Constants.Event.ACTIVITY_LEARNERS_QUESTION_SCORE) {

    @Override
    public AsdpEventHandler build(JSONObject message) {
      return AsdpEventHandlerBuilder
          .buildLearnersAssessmentExternalQuestionScoreEventHandler(message);
    }

  };

  private String name;

  AsdpEventProcessorBuilder(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  private static final Map<String, AsdpEventProcessorBuilder> LOOKUP = new HashMap<>();

  static {
    for (AsdpEventProcessorBuilder builder : values()) {
      LOOKUP.put(builder.getName(), builder);
    }
  }


  public static AsdpEventProcessorBuilder lookupBuilder(String name) {
    AsdpEventProcessorBuilder builder = LOOKUP.get(name);
    if (builder == null) {
      return DEFAULT;
    }
    return builder;
  }

  public abstract AsdpEventHandler build(JSONObject message);
}
