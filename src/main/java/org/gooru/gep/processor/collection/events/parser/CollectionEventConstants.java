package org.gooru.gep.processor.collection.events.parser;


/**
 * @author mukul@gooru
 *  
 */
public class CollectionEventConstants {
	
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
		public static final String PATH_ID = "pathId";
		public static final String QUESTION_COUNT = "questionCount";
		public static final String PARTNER_ID = "partnerId";
		public static final String TENANT_ID = "tenantId";
		
		public static final String CONTEXT_COLLECTION_ID = "contextCollectionId";
		public static final String CONTEXT_COLLECTION_TYPE = "contextCollectionType";
		public static final String PATH_TYPE = "pathType";
		public static final String CONTENT_SOURCE = "contentSource";
		public static final String ADDITIONAL_CONTEXT = "additionalContext";
		
		public static final String ACTIVITY_TIME = "activityTime";
		public static final String SCORE = "score";
		public static final String MAX_SCORE = "maxScore";
		public static final String TIMESPENT = "timeSpent";
		
		public static final String RESULT = "result";
		public static final String CONTEXT = "context";
		
		public static final String COLLECTION = "collection";
		public static final String EXT_COLLECTION = "collection-external";
		public static final String ASSESSMENT = "assessment";
		public static final String EXT_ASSESSMENT = "assessment-external";
		public static final String OFFLINE_ACTIVITY = "offline-activity";
		
		
		public static final String COLLECTION_PERF_EVENT = "collection.performance";
		public static final String COLLECTION_START_EVENT = "collection.start";
		public static final String RESOURCE_PERF_EVENT = "resource.performance";
		public static final String COLL_SCORE_UPDATE_EVENT = "collection.score.update";


		private EventAttributes() {
			throw new AssertionError();
		}
	}

}
