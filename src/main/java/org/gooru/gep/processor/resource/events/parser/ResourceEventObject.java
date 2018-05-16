package org.gooru.gep.processor.resource.events.parser;

import org.gooru.gep.exceptions.HttpResponseWrapperException;
import org.gooru.gep.constants.HttpConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import io.vertx.core.json.JSONObject;
import org.json.JSONObject;


/**
 * @author mukul@gooru
 *  
 */
public class ResourceEventObject {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceEventObject.class);

	private String user;
	private String classId;
	private String courseId;
	private String unitId;
	private String lessonId;
	private String collectionId;
	private String collectionType;
	private String sessionId;
	private long activityTime;
	private double score;
	private double max_score;
	private long timeSpent;
	private JSONObject result;
	private JSONObject context;
	private String resourceId;
	private String resourceType;
	private String eventName;
	private String eventId;
	private String tenantId;
	private String partnerId;
	
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

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
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
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	
    static ResourceEventObject builder(JSONObject requestBody) {
    	LOGGER.info(requestBody.toString(1));
    	ResourceEventObject result = ResourceEventObject.buildFromJSONObject(requestBody);
        result.validate();
        return result;
    }

    private static ResourceEventObject buildFromJSONObject(JSONObject requestBody) {
    	ResourceEventObject event = new ResourceEventObject();
        event.user = requestBody.getString(ResourceEventConstants.EventAttributes.USER_ID);
        event.activityTime = requestBody.getLong(ResourceEventConstants.EventAttributes.ACTIVITY_TIME);
        event.resourceId = requestBody.getString(ResourceEventConstants.EventAttributes.RESOURCE_ID);
        event.resourceType = requestBody.getString(ResourceEventConstants.EventAttributes.RESOURCE_TYPE);
        event.eventId = requestBody.getString(ResourceEventConstants.EventAttributes.EVENT_ID);
        event.eventName = requestBody.getString(ResourceEventConstants.EventAttributes.EVENT_NAME);
        
        event.result = requestBody.getJSONObject(ResourceEventConstants.EventAttributes.RESULT);
        if (event.result != null) {
        	if (event.result.has(ResourceEventConstants.EventAttributes.SCORE) && 
        			!event.result.isNull(ResourceEventConstants.EventAttributes.SCORE)) {
            	event.score = event.result.getDouble(ResourceEventConstants.EventAttributes.SCORE);        		
        	}
        	if (event.result.has(ResourceEventConstants.EventAttributes.MAX_SCORE) && 
        			!event.result.isNull(ResourceEventConstants.EventAttributes.MAX_SCORE)) {
            	event.max_score = event.result.getDouble(ResourceEventConstants.EventAttributes.MAX_SCORE);        		
        	}
        	if (event.result.has(ResourceEventConstants.EventAttributes.TIMESPENT) && 
        			!event.result.isNull(ResourceEventConstants.EventAttributes.TIMESPENT)) {
        		event.timeSpent = event.result.getLong(ResourceEventConstants.EventAttributes.TIMESPENT);        		
        	}
        	
        }
        event.context = requestBody.getJSONObject(ResourceEventConstants.EventAttributes.CONTEXT);
        
        if (event.context != null) {
        	if (!event.context.isNull(ResourceEventConstants.EventAttributes.CLASS_ID)) {
        		event.classId = event.context.getString(ResourceEventConstants.EventAttributes.CLASS_ID);        		
        	}
        	if (!event.context.isNull(ResourceEventConstants.EventAttributes.COURSE_ID)) {
                event.courseId = event.context.getString(ResourceEventConstants.EventAttributes.COURSE_ID);        		
        	}
        	if (!event.context.isNull(ResourceEventConstants.EventAttributes.UNIT_ID)) {
        		event.unitId = event.context.getString(ResourceEventConstants.EventAttributes.UNIT_ID);        		
        	}
        	if (!event.context.isNull(ResourceEventConstants.EventAttributes.LESSON_ID)) {
        		event.lessonId = event.context.getString(ResourceEventConstants.EventAttributes.LESSON_ID);        		
        	}            
            event.sessionId = event.context.getString(ResourceEventConstants.EventAttributes.SESSION_ID);
            event.collectionId = event.context.getString(ResourceEventConstants.EventAttributes.COLLECTION_ID);
            event.collectionType = event.context.getString(ResourceEventConstants.EventAttributes.COLLECTION_TYPE);
            if (event.context.has(ResourceEventConstants.EventAttributes.PARTNER_ID) && 
            		!event.context.isNull(ResourceEventConstants.EventAttributes.PARTNER_ID)) {
            	event.partnerId = event.context.getString(ResourceEventConstants.EventAttributes.PARTNER_ID);            	
            }            
            if (event.context.has(ResourceEventConstants.EventAttributes.TENANT_ID) && 
            		!event.context.isNull(ResourceEventConstants.EventAttributes.TENANT_ID)) {
            	event.tenantId = event.context.getString(ResourceEventConstants.EventAttributes.TENANT_ID);            	
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
        
        if (resourceId == null) {
            LOGGER.info("Resource Id not provided");
            throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
                "Resource Id not provided in the request");
        }        
        
        if (resourceType == null) {
            LOGGER.info("Resource Id not provided");
            throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
                "Resource Type not provided in the request");
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
