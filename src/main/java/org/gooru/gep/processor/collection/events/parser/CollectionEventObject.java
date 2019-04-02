package org.gooru.gep.processor.collection.events.parser;

import org.gooru.gep.constants.HttpConstants;
import org.gooru.gep.exceptions.HttpResponseWrapperException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author mukul@gooru
 *  
 */
public class CollectionEventObject {

	private static final Logger LOGGER = LoggerFactory.getLogger(CollectionEventObject.class);
	
	private String user;
	private String classId;
	private String courseId;
	private String unitId;
	private String lessonId;
	private String collectionId;
	private String collectionType;
	private String sessionId;
	private int pathId;
	private long activityTime;
	private double score;
	private double max_score;
	
	private long timeSpent;
	private JSONObject result;
	private JSONObject context;
	private String eventName;
	private String eventId;
	private int questionCount;
	private String partnerId;
	private String tenantId;

	private String contextCollectionId;
	private String contextCollectionType;
	private String pathType;
	private String contentSource;
	private String additionalContext;

  public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getLessonId() {
		return lessonId;
	}

	public void setLessonId(String lessonId) {
		this.lessonId = lessonId;
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

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public int getPathId() {
		return pathId;
	}

	public void setPathId(int pathId) {
		this.pathId = pathId;
	}

	public long getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(long activityTime) {
		this.activityTime = activityTime;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getMax_score() {
		return max_score;
	}

	public void setMax_score(double max_score) {
		this.max_score = max_score;
	}

	public long getTimeSpent() {
		return timeSpent;
	}

	public void setTimeSpent(long timeSpent) {
		this.timeSpent = timeSpent;
	}

	public JSONObject getResult() {
		return result;
	}

	public void setResult(JSONObject result) {
		this.result = result;
	}

	public JSONObject getContext() {
		return context;
	}

	public void setContext(JSONObject context) {
		this.context = context;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public int getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(int questionCount) {
		this.questionCount = questionCount;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getContextCollectionId() {
		return contextCollectionId;
	}

	public void setContextCollectionId(String contextCollectionId) {
		this.contextCollectionId = contextCollectionId;
	}

	public String getContextCollectionType() {
		return contextCollectionType;
	}

	public void setContextCollectionType(String contextCollectionType) {
		this.contextCollectionType = contextCollectionType;
	}

	public String getPathType() {
		return pathType;
	}

	public void setPathType(String pathType) {
		this.pathType = pathType;
	}

    public String getContentSource() {
		return contentSource;
	}

	public void setContentSource(String contentSource) {
		this.contentSource = contentSource;
	}
	
	public String getAdditionalContext() {
	  return additionalContext;
	}

	public void setAdditionalContext(String additionalContext) {
	  this.additionalContext = additionalContext;
	}

	static CollectionEventObject builder(JSONObject requestBody) {
    	LOGGER.info(requestBody.toString(1));
    	CollectionEventObject event = CollectionEventObject.buildFromJSONObject(requestBody);
        event.validate();
        return event;
    }

    private static CollectionEventObject buildFromJSONObject(JSONObject requestBody) {
    	CollectionEventObject event = new CollectionEventObject();

        event.user = requestBody.getString(CollectionEventConstants.EventAttributes.USER_ID);
        event.activityTime = requestBody.getLong(CollectionEventConstants.EventAttributes.ACTIVITY_TIME);
        event.eventId = requestBody.getString(CollectionEventConstants.EventAttributes.EVENT_ID);
        event.eventName = requestBody.getString(CollectionEventConstants.EventAttributes.EVENT_NAME);
        event.collectionId = requestBody.getString(CollectionEventConstants.EventAttributes.COLLECTION_ID);
        event.collectionType = requestBody.getString(CollectionEventConstants.EventAttributes.COLLECTION_TYPE);

        event.result = requestBody.getJSONObject(CollectionEventConstants.EventAttributes.RESULT);
        if (event.result != null) {
        	if (event.result.has(CollectionEventConstants.EventAttributes.SCORE) && 
        			!event.result.isNull(CollectionEventConstants.EventAttributes.SCORE)) {
            	event.score = event.result.getDouble(CollectionEventConstants.EventAttributes.SCORE);        		
        	}
        	if (event.result.has(CollectionEventConstants.EventAttributes.MAX_SCORE) && 
        			!event.result.isNull(CollectionEventConstants.EventAttributes.MAX_SCORE)) {
            	event.max_score = event.result.getDouble(CollectionEventConstants.EventAttributes.MAX_SCORE);        		
        	}
        	if (event.result.has(CollectionEventConstants.EventAttributes.TIMESPENT) && 
        			!event.result.isNull(CollectionEventConstants.EventAttributes.TIMESPENT)) {
        		event.timeSpent = event.result.getLong(CollectionEventConstants.EventAttributes.TIMESPENT);        		
        	}
        	        	
        }
        event.context = requestBody.getJSONObject(CollectionEventConstants.EventAttributes.CONTEXT);
        if (event.context != null) {
        	if (!event.context.isNull(CollectionEventConstants.EventAttributes.CLASS_ID)) {
        		event.classId = event.context.getString(CollectionEventConstants.EventAttributes.CLASS_ID);        		
        	}
        	if (!event.context.isNull(CollectionEventConstants.EventAttributes.COURSE_ID)) {
                event.courseId = event.context.getString(CollectionEventConstants.EventAttributes.COURSE_ID);        		
        	}
        	if (!event.context.isNull(CollectionEventConstants.EventAttributes.UNIT_ID)) {
        		event.unitId = event.context.getString(CollectionEventConstants.EventAttributes.UNIT_ID);        		
        	}
        	if (!event.context.isNull(CollectionEventConstants.EventAttributes.LESSON_ID)) {
        		event.lessonId = event.context.getString(CollectionEventConstants.EventAttributes.LESSON_ID);        		
        	}        	        	
        	if (event.context.has(CollectionEventConstants.EventAttributes.PATH_ID) && 
        			!event.context.isNull(CollectionEventConstants.EventAttributes.PATH_ID)) {
        		event.pathId = event.context.getInt(CollectionEventConstants.EventAttributes.PATH_ID);                    		
        	} else {
        		//Default value of pathId is 0
        		event.pathId = 0;
        	}
        	if (event.context.has(CollectionEventConstants.EventAttributes.QUESTION_COUNT) && 
        			!event.context.isNull(CollectionEventConstants.EventAttributes.QUESTION_COUNT)) {
        		event.questionCount = event.context.getInt(CollectionEventConstants.EventAttributes.QUESTION_COUNT);        		
        	}
            event.sessionId = event.context.getString(CollectionEventConstants.EventAttributes.SESSION_ID);
            if (event.context.has(CollectionEventConstants.EventAttributes.PARTNER_ID) && 
            		!event.context.isNull(CollectionEventConstants.EventAttributes.PARTNER_ID)) {
            	event.partnerId = event.context.getString(CollectionEventConstants.EventAttributes.PARTNER_ID);            	
            }            
            if (event.context.has(CollectionEventConstants.EventAttributes.TENANT_ID) && 
            		!event.context.isNull(CollectionEventConstants.EventAttributes.TENANT_ID)) {
            	event.tenantId = event.context.getString(CollectionEventConstants.EventAttributes.TENANT_ID);            	
            }      
            
        	if (!event.context.isNull(CollectionEventConstants.EventAttributes.CONTEXT_COLLECTION_ID)) {
                event.contextCollectionId = event.context.getString(CollectionEventConstants.EventAttributes.CONTEXT_COLLECTION_ID);        		
        	}
        	if (!event.context.isNull(CollectionEventConstants.EventAttributes.CONTEXT_COLLECTION_TYPE)) {
        		event.contextCollectionType = event.context.getString(CollectionEventConstants.EventAttributes.CONTEXT_COLLECTION_TYPE);        		
        	}
        	if (!event.context.isNull(CollectionEventConstants.EventAttributes.CONTENT_SOURCE)) {
        		event.contentSource = event.context.getString(CollectionEventConstants.EventAttributes.CONTENT_SOURCE);        		
        	}
        	if (!event.context.isNull(CollectionEventConstants.EventAttributes.PATH_TYPE)) {
        		event.pathType = event.context.getString(CollectionEventConstants.EventAttributes.PATH_TYPE);        		
        	}
            if (!event.context.isNull(CollectionEventConstants.EventAttributes.ADDITIONAL_CONTEXT)) {
              event.additionalContext = event.context.getString(CollectionEventConstants.EventAttributes.ADDITIONAL_CONTEXT);             
          }
        }
        
        return event;
    }

    //TODO: Add more Validations
    private void validate() {

        if (user == null) {
            LOGGER.info("User not provided");
            throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
                "User not provided for request");
        }
        
        if (collectionId == null) {
            LOGGER.info("Collection Id not provided");
            throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
                "Collection Id not provided in the request");
        }        

        if (collectionType == null) {
            LOGGER.info("Collection Type not provided");
            throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
                "Collection Type not provided in the request");
        }        
        
        if (sessionId == null) {
            LOGGER.info("Collection Type not provided");
            throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
                "Session Id not provided in the request");
        }        

    }
}
