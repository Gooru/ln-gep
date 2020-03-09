package org.gooru.gep.processor.asdp.learners.compstatus;

import java.util.Arrays;
import java.util.List;
import org.gooru.gep.constants.HttpConstants;
import org.gooru.gep.constants.HttpConstants.HttpStatus;
import org.gooru.gep.exceptions.HttpResponseWrapperException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LearnersCompetencyStatusEventModel {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(LearnersCompetencyStatusEventModel.class);

  private final String eventId;
  private final String eventName;
  private final Context context;
  private final String userId;
  private final Result result;

  public LearnersCompetencyStatusEventModel(String eventId, String eventName, Context context,
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

  public String getGutCode() {
    return context.getGutCode();
  }

  public Result getResult() {
    return result;
  }


  private static class Context {
    private final String gutCode;
    private final String tenantId;
    private final String contentSource;

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
      this.tenantId = context.getString(EventAttributes.TENANT_ID);
      if (this.tenantId == null || this.tenantId.isEmpty()) {
        throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST,
            "Event -> Context -> tenantId is NULL OR EMPTY");
      }
      this.contentSource = context.getString(EventAttributes.CONTENT_SOURCE);
      if (this.contentSource == null || this.contentSource.isEmpty()) {
        throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST,
            "Event -> Context -> tenantId is NULL OR EMPTY");
      }
    }

    public String getGutCode() {
      return this.gutCode;
    }

    public String getTenantId() {
      return this.tenantId;
    }



  }

  private static class Result {
    private Integer status;
    private final static List<Integer> ALLOWED_STATUS = Arrays.asList(0, 1, 2, 3, 4, 5);

    Result(JSONObject requestBody) {
      JSONObject result = requestBody.getJSONObject(EventAttributes.RESULT);
      if (result == null) {
        throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST,
            "Event -> Result is NULL OR EMPTY");
      }
      this.status = result.getInt(EventAttributes.STATUS);
      if (status == null || !ALLOWED_STATUS.contains(this.status)) {
        throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST,
            "Event -> Result -> status is NULL OR Invalid");
      }
    }

    public Integer getStatus() {
      return status;
    }
  }

  static JSONObject builder(JSONObject message) {
    LearnersCompetencyStatusEventModel eventModel = buildFromJSONObject(message);
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

  private static LearnersCompetencyStatusEventModel buildFromJSONObject(JSONObject requestBody) {
    String eventId = requestBody.getString(EventAttributes.EVENT_ID);
    String eventName = requestBody.getString(EventAttributes.EVENT_NAME);
    String userId = requestBody.getString(EventAttributes.USER_ID);
    Context context = new Context(requestBody);
    Result result = new Result(requestBody);
    LearnersCompetencyStatusEventModel eventModel =
        new LearnersCompetencyStatusEventModel(eventId, eventName, context, userId, result);
    return eventModel;
  }

  private static JSONObject asJSONObject(LearnersCompetencyStatusEventModel eventModel) {
    JSONObject event = new JSONObject();
    JSONObject context = new JSONObject();
    JSONObject result = new JSONObject();

    context.put(EventAttributes.TENANT_ID, eventModel.getContext().getTenantId());

    context.put(EventAttributes.GUT_CODE, eventModel.getContext().getGutCode());

    result.put(EventAttributes.STATUS, eventModel.getResult().getStatus());

    event.put(EventAttributes.EVENT_ID, eventModel.getEventId());
    event.put(EventAttributes.EVENT_NAME, eventModel.getEventName());
    event.put(EventAttributes.USER_ID, eventModel.getEventId());
    event.put(EventAttributes.CONTEXT, context);
    event.put(EventAttributes.RESULT, result);
    return event;
  }



  static class EventAttributes {
    private static final String EVENT_ID = "eventId";
    private static final String EVENT_NAME = "eventName";
    private static final String GUT_CODE = "gutCode";
    private static final String CONTEXT = "context";
    private static final String USER_ID = "userId";
    private static final String RESULT = "result";
    private static final String STATUS = "status";
    private static final String TENANT_ID = "tenantId";
    private static final String CONTENT_SOURCE = "contentSource";

    private EventAttributes() {
      throw new AssertionError();
    }
  }
}
