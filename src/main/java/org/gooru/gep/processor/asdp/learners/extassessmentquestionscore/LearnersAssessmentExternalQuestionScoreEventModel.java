package org.gooru.gep.processor.asdp.learners.extassessmentquestionscore;

import org.gooru.gep.constants.HttpConstants;
import org.gooru.gep.constants.HttpConstants.HttpStatus;
import org.gooru.gep.exceptions.HttpResponseWrapperException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LearnersAssessmentExternalQuestionScoreEventModel {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(LearnersAssessmentExternalQuestionScoreEventModel.class);

  private final String eventId;
  private final String eventName;
  private final Context context;
  private final User user;
  private final Result result;

  public LearnersAssessmentExternalQuestionScoreEventModel(String eventId, String eventName,
      Context context, User user, Result result) {
    this.eventId = eventId;
    this.eventName = eventName;
    this.context = context;
    this.user = user;
    this.result = result;
  }

  public String getEventId() {
    return eventId;
  }


  public String getEventName() {
    return eventName;
  }

  public Context getContext() {
    return context;
  }

  public User getUser() {
    return user;
  }

  public Result getResult() {
    return result;
  }


  public String getUserId() {
    return user.getId();
  }

  public String getGutCode() {
    return context.getGutCode();
  }


  private static class Context {
    private final String gutCode;
    private final String questionId;

    Context(JSONObject requestBody) {
      JSONObject context = requestBody.getJSONObject(EventAttributes.CONTEXT);
      if (context == null) {
        throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST,
            "Event -> Context is NULL OR EMPTY");
      }
      this.gutCode = context.getString(EventAttributes.GUT_CODE);

      if (gutCode == null || gutCode.isEmpty()) {
        throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST,
            "Event -> Context -> compGutCode is NULL OR EMPTY");
      }

      this.questionId = context.getString(EventAttributes.QUESTION_ID);

      if (questionId == null || questionId.isEmpty()) {
        throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST,
            "Event -> Context -> questionId is NULL OR EMPTY");
      }
    }

    public String getQuestionId() {
      return questionId;
    }

    public String getGutCode() {
      return this.gutCode;
    }


  }

  private static class User {
    private String id;
    private final String tenantId;

    User(JSONObject requestBody) {
      JSONObject user = requestBody.getJSONObject(EventAttributes.USER);
      if (user == null) {
        throw new IllegalStateException("Event -> User is NULL OR EMPTY");
      }
      this.id = user.getString(EventAttributes.ID);
      if (id == null || id.isEmpty()) {
        throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST,
            "Event -> User -> id is NULL OR EMPTY");
      }
      this.tenantId = user.getString(EventAttributes.TENANT_ID);
      if (this.tenantId == null || this.tenantId.isEmpty()) {
        throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST,
            "Event -> User -> tenantId is NULL OR EMPTY");
      }
    }

    public String getId() {
      return id;
    }

    public String getTenantId() {
      return tenantId;
    }
  }

  private static class Result {
    private Integer score;
    private Long timeSpent;

    Result(JSONObject requestBody) {
      JSONObject result = requestBody.getJSONObject(EventAttributes.RESULT);
      if (result == null) {
        throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST,
            "Event -> Result is NULL OR EMPTY");
      }
      this.score = result.getInt(EventAttributes.SCORE);
      if (score == null) {
        throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST,
            "Event -> Result -> score is NULL OR Invalid");
      }

    }

    public Integer getScore() {
      return this.score;
    }

    public Long getTimeSpent() {
      return this.timeSpent;
    }
  }

  static JSONObject builder(JSONObject requestBody) {
    LearnersAssessmentExternalQuestionScoreEventModel eventModel = buildFromJSONObject(requestBody);
    eventModel.validate();
    return asJSONObject(eventModel);
  }

  private void validate() {
    if (eventId == null || eventId.isEmpty()) {
      LOGGER.debug("Event ID is NULL or EMPTY");
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
          "Event ID is NULL or EMPTY");
    }
    if (eventName == null || eventName.isEmpty()) {
      LOGGER.debug("Event Name is NULL or EMPTY");
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
          "Event Name is NULL or EMPTY");
    }
  }

  private static LearnersAssessmentExternalQuestionScoreEventModel buildFromJSONObject(
      JSONObject requestBody) {
    String eventId = requestBody.getString(EventAttributes.EVENT_ID);
    String eventName = requestBody.getString(EventAttributes.EVENT_NAME);
    Context context = new Context(requestBody);
    User user = new User(requestBody);
    Result result = new Result(requestBody);
    LearnersAssessmentExternalQuestionScoreEventModel eventModel =
        new LearnersAssessmentExternalQuestionScoreEventModel(eventId, eventName, context, user,
            result);
    return eventModel;
  }


  private static JSONObject asJSONObject(
      LearnersAssessmentExternalQuestionScoreEventModel eventModel) {
    JSONObject event = new JSONObject();
    JSONObject user = new JSONObject();
    JSONObject context = new JSONObject();
    JSONObject result = new JSONObject();

    user.put(EventAttributes.ID, eventModel.getUser().getId());
    user.put(EventAttributes.TENANT_ID, eventModel.getUser().getTenantId());

    context.put(EventAttributes.GUT_CODE, eventModel.getContext().getGutCode());
    context.put(EventAttributes.QUESTION_ID, eventModel.getContext().getQuestionId());

    result.put(EventAttributes.SCORE, eventModel.getResult().getScore());
    result.put(EventAttributes.TIME_SPENT, eventModel.getResult().getTimeSpent());

    event.put(EventAttributes.EVENT_ID, eventModel.getEventId());
    event.put(EventAttributes.EVENT_NAME, eventModel.getEventName());
    event.put(EventAttributes.CONTEXT, context);
    event.put(EventAttributes.RESULT, result);
    event.put(EventAttributes.USER, user);
    return event;
  }

  static class EventAttributes {
    private static final String EVENT_ID = "eventId";
    private static final String EVENT_NAME = "eventName";
    private static final String GUT_CODE = "gutCode";
    private static final String QUESTION_ID = "questionId";
    private static final String TENANT_ID = "tenantId";
    private static final String CONTEXT = "context";
    private static final String USER = "user";
    private static final String RESULT = "result";
    private static final String ID = "id";
    private static final String SCORE = "score";
    private static final String TIME_SPENT = "timeSpent";

    private EventAttributes() {
      throw new AssertionError();
    }
  }
}
