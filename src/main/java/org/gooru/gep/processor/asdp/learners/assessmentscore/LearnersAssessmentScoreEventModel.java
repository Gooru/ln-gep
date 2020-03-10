package org.gooru.gep.processor.asdp.learners.assessmentscore;

import org.gooru.gep.constants.HttpConstants;
import org.gooru.gep.constants.HttpConstants.HttpStatus;
import org.gooru.gep.exceptions.HttpResponseWrapperException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LearnersAssessmentScoreEventModel {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(LearnersAssessmentScoreEventModel.class);

  private final String eventId;
  private final String eventName;
  private String collectionId;
  private String collectionType;
  private final Context context;
  private final String userId;
  private final Result result;

  public LearnersAssessmentScoreEventModel(String eventId, String eventName, Context context,
      String userId, Result result) {
    this.eventId = eventId;
    this.eventName = eventName;
    this.context = context;
    this.userId = userId;
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

  public String getUserId() {
    return userId;
  }

  public Result getResult() {
    return result;
  }

  public String getCollectionId() {
    return collectionId;
  }

  public void setCollectionId(String collectionId) {
    this.collectionId = collectionId;
  }

  public String getCollectionType() {
    return collectionType;
  }

  public void setCollectionType(String collectionType) {
    this.collectionType = collectionType;
  }



  private static class Context {
    private final String contentSource;
    private final String tenantId;

    Context(JSONObject requestBody) {
      JSONObject context = requestBody.getJSONObject(EventAttributes.CONTEXT);
      if (context == null) {
        throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST,
            "Event -> Context is NULL OR EMPTY");
      }
      this.tenantId = context.getString(EventAttributes.TENANT_ID);

      if (this.tenantId == null || this.tenantId.isEmpty()) {
        throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST,
            "Event -> User -> tenantId is NULL OR EMPTY");
      }

      this.contentSource = context.getString(EventAttributes.CONTENT_SOURCE);

      if (contentSource == null || contentSource.isEmpty()) {
        throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST,
            "Event -> Context -> contentSource is NULL OR EMPTY");
      }
    }

    public String getContentSource() {
      return this.contentSource;
    }

    public String getTenantId() {
      return this.tenantId;
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
    LearnersAssessmentScoreEventModel eventModel = buildFromJSONObject(requestBody);
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

  private static LearnersAssessmentScoreEventModel buildFromJSONObject(JSONObject requestBody) {
    String eventId = requestBody.getString(EventAttributes.EVENT_ID);
    String eventName = requestBody.getString(EventAttributes.EVENT_NAME);
    String userId = requestBody.getString(EventAttributes.USER_ID);
    String collectionId = requestBody.getString(EventAttributes.COLLECTION_ID);
    String collectionType = requestBody.getString(EventAttributes.COLLECTION_TYPE);
    Context context = new Context(requestBody);
    Result result = new Result(requestBody);
    LearnersAssessmentScoreEventModel eventModel =
        new LearnersAssessmentScoreEventModel(eventId, eventName, context, userId, result);
    eventModel.setCollectionId(collectionId);
    eventModel.setCollectionType(collectionType);
    return eventModel;
  }


  private static JSONObject asJSONObject(LearnersAssessmentScoreEventModel eventModel) {
    JSONObject event = new JSONObject();
    JSONObject context = new JSONObject();
    JSONObject result = new JSONObject();

    result.put(EventAttributes.SCORE, eventModel.getResult().getScore());
    result.put(EventAttributes.TIME_SPENT, eventModel.getResult().getTimeSpent());

    context.put(EventAttributes.TENANT_ID, eventModel.getContext().getTenantId());
    context.put(EventAttributes.CONTENT_SOURCE, eventModel.getContext().getContentSource());
    
    event.put(EventAttributes.EVENT_ID, eventModel.getEventId());
    event.put(EventAttributes.EVENT_NAME, eventModel.getEventName());
    event.put(EventAttributes.USER_ID, eventModel.getUserId());
    event.put(EventAttributes.COLLECTION_ID, eventModel.getCollectionId());
    event.put(EventAttributes.COLLECTION_TYPE, eventModel.getCollectionType());

    event.put(EventAttributes.CONTEXT, context);
    event.put(EventAttributes.RESULT, result);

    return event;
  }



  static class EventAttributes {
    private static final String EVENT_ID = "eventId";
    private static final String EVENT_NAME = "eventName";
    private static final String COLLECTION_ID = "collectionId";
    private static final String COLLECTION_TYPE = "collectionType";
    private static final String CONTENT_SOURCE = "contentSource";
    private static final String USER_ID = "userId";
    private static final String TENANT_ID = "tenantId";
    private static final String CONTEXT = "context";
    private static final String RESULT = "result";
    private static final String SCORE = "score";
    private static final String TIME_SPENT = "timeSpent";

    private EventAttributes() {
      throw new AssertionError();
    }
  }
}
