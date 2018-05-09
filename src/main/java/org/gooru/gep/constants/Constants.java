package org.gooru.gep.constants;


/**
 * @author mukul@gooru
 *  
 */
public final class Constants {

    public static final class Message {

        public static final String MSG_OP = "mb.op";
        public static final String MSG_OP_AUTH = "auth";
        public static final String MSG_OP_STATUS = "mb.op.status";
        public static final String MSG_OP_STATUS_SUCCESS = "mb.op.status.success";
        public static final String MSG_OP_STATUS_FAIL = "mb.op.status.fail";
        public static final String MSG_API_VERSION = "api.version";
        public static final String MSG_SESSION_TOKEN = "session.token";
        public static final String MSG_KEY_SESSION = "session";
        public static final String MSG_USER_ANONYMOUS = "anonymous";
        public static final String MSG_USER_ID = "user_id";
        public static final String MSG_HTTP_STATUS = "http.status";
        public static final String MSG_HTTP_BODY = "http.body";
        public static final String MSG_HTTP_HEADERS = "http.headers";
        public static final String MSG_MESSAGE = "message";
        public static final String PROCESSING_AUTH_TIME = "auth.processing.time";
        public static final String PROCESSING_HANDLER_START_TIME = "handler.start.time";
        
        public static final String MSG_OP_XFORM_XAPI_EVENT = "mb.op.xform.xapi.event";

        private Message() {
            throw new AssertionError();
        }
    }

    public static final class Config {

    	  //Kafka Config Constants
    	  public static final String CONFIG_KAFKA_CONSUMER = "kafkaConsumerConfig";
    	  public static final String CONFIG_KAFKA_SERVERS = "bootstrap.servers";
    	  public static final String CONFIG_KAFKA_GROUP = "group.id";
    	  public static final String CONFIG_KAFKA_TOPICS = "topics";
    	  public static final String CONFIG_KAFKA_TIME_OUT_IN_MS = "session.timeout.ms";
    	  public static final String CONFIG_KAFKA_KEY_DESERIALIZER = "key.deserializer";
    	  public static final String CONFIG_KAFKA_VALUE_DESERIALIZER = "value.deserializer";

    	  public static final String KAFKA_EVENTLOGS_TOPIC = "EVENTLOGS";
    	  public static final String KAFKA_TEST_TOPIC = "test";
    	  public static final String KAFKA_LOG_WRITER_USAGE_EVENTS_TOPIC = "org.gooru.da.sink.logW.usage.events";

    	  public static final String HYPHEN = "-";
    	  public static final String COMMA = ",";
    	  public static final String COLON = ":";

    	  public static final String THREAD_POOL_SIZE ="thread.pool.size";

        private Config() {
            throw new AssertionError();
        }
    }
     
    public static final class Event {
    	
  	  public static final String RESOURCE_PERFORMANCE = "resource.performance";
	  public static final String COLLECTION_PERFORMANCE = "collection.performance";
	  public static final String COLLECTION_START = "collection.start";

        private Event() {
            throw new AssertionError();
        }
    }
    
    public static final class Response {

        private Response() {
            throw new AssertionError();
        }
    }

    public static final class Params {

        private Params() {
            throw new AssertionError();
        }
    }

    private Constants() {
        throw new AssertionError();
    }
}
