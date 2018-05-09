package org.gooru.gep.processor.resource.events.parser;


/**
 * @author mukul@gooru
 *  
 */
public class ResourceEventConstants {
	
	public static class EventAttributes {
		public static final String EVENT_NAME = "eventName";
		public static final String EVENT_ID = "eventId";
		public static final String USER_ID = "userId";
		public static final String RESOURCE_ID = "resourceId";
		public static final String RESOURCE_TYPE = "resourceType";
		public static final String CLASS_ID = "classId";
		public static final String COURSE_ID = "courseId";
		public static final String UNIT_ID = "unitId";
		public static final String LESSON_ID = "lessonId";
		public static final String COLLECTION_ID = "collectionId";
		public static final String COLLECTION_TYPE = "collectionType";
		public static final String SESSION_ID = "sessionId";
		public static final String PARTNER_ID = "partnerId";
		public static final String TENANT_ID = "tenantId";
		
		public static final String ACTIVITY_TIME = "activityTime";
		public static final String SCORE = "score";
		public static final String MAX_SCORE = "maxScore";
		
		public static final String TIMESPENT = "timeSpent";
		public static final String RESULT = "result";
		public static final String CONTEXT = "context";
		
		public static final String RESOURCE = "resource";
		public static final String QUESTION = "question";

		private EventAttributes() {
			throw new AssertionError();
		}
	}

}
