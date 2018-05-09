package org.gooru.gep.app.components;

import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.json.JSONObject;

public class KafkaRegistry implements Initializer, Finalizer {
	
    private static final String KAFKA_PROD_CONFIG = "kafka.producer.config";
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaRegistry.class);
    private Producer<String, String> kafkaProducer;

    private String KAFKA_TOPIC = "dafaultTopic";
    private boolean testWithoutKafkaServer = false;
    private volatile boolean initialized = false;

    @Override
    public void initializeComponent(JSONObject config) {
        // Skip if we are already initialized
        LOGGER.debug("Kafka Initialization called upon..");
        if (!initialized) {
            LOGGER.debug("May have to do initialization");
            // We need to do initialization, however, we are running it via
            // verticle instance which is going to run in
            // multiple threads hence we need to be safe for this operation
            synchronized (Holder.INSTANCE) {
                LOGGER.debug("Will initialize after double checking");
                if (!initialized) {
                    LOGGER.debug("Initializing KafkaRegistry now");
                    JSONObject kafkaConfig = config.getJSONObject(KAFKA_PROD_CONFIG);
                    this.kafkaProducer = initializeKafkaPublisher(kafkaConfig);
                    initialized = true;
                    LOGGER.debug("Kafka Registy Initialized");
                }
            }
        }
    }

    private Producer<String, String> initializeKafkaPublisher(JSONObject kafkaConfig) {
        LOGGER.debug("Initialize Kafka Publisher now...");

        final Properties properties = new Properties();

        kafkaConfig.keySet().forEach(key -> {
            switch (key) {
            case ProducerConfig.BOOTSTRAP_SERVERS_CONFIG:
            	properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getString(key));            			
            	LOGGER.debug("BOOTSTRAP_SERVERS_CONFIG : " + kafkaConfig.getString(key));
            	break;
            case ProducerConfig.RETRIES_CONFIG:
            	properties.put(ProducerConfig.RETRIES_CONFIG, kafkaConfig.getInt(key));
            	LOGGER.debug("RETRIES_CONFIG : " + kafkaConfig.getInt(key));
            	break;
            case ProducerConfig.BATCH_SIZE_CONFIG:
            	properties.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaConfig.getInt(key));
            	LOGGER.debug("BATCH_SIZE_CONFIG : " + kafkaConfig.getInt(key));
            	break;
            case ProducerConfig.LINGER_MS_CONFIG:
            	properties.put(ProducerConfig.LINGER_MS_CONFIG, kafkaConfig.getInt(key));
            	LOGGER.debug("LINGER_MS_CONFIG : " + kafkaConfig.getInt(key));
            	break;
            case ProducerConfig.BUFFER_MEMORY_CONFIG:
            	properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, kafkaConfig.getLong(key));
            	LOGGER.debug("BUFFER_MEMORY_CONFIG : " + kafkaConfig.getLong(key));
            	break;
            case ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG:
            	properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaConfig.getString(key));
            	LOGGER.debug("KEY_SERIALIZER_CLASS_CONFIG : " + kafkaConfig.getString(key));
            	break;
            case ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG:
            	properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaConfig.getString(key));
            	LOGGER.debug("VALUE_SERIALIZER_CLASS_CONFIG : " + kafkaConfig.getString(key));
            	break;
            case ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG:
            	properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, kafkaConfig.getString(key));
            	LOGGER.debug("REQUEST_TIMEOUT_MS_CONFIG : " + kafkaConfig.getString(key));
            	break;
//            case "testEnvironmentWithoutKafkaServer":
//            	this.testWithoutKafkaServer = (boolean) entry.getValue();
//            	LOGGER.debug("KAFKA_TOPIC : " + this.KAFKA_TOPIC);
//            	break;            	
            }
        });
        
        if (this.testWithoutKafkaServer) {
            return null;
        }

        LOGGER.debug("Kafka Publisher properties created...");
        Producer<String, String> producer = new KafkaProducer<>(properties);

        LOGGER.debug("Kafka producer created successfully!");

        return producer;
    }

    public Producer<String, String> getKafkaProducer() {
        if (initialized) {
            return this.kafkaProducer;
        }
        return null;
    }

    @Override
    public void finalizeComponent() {
        if (this.kafkaProducer != null) {
            this.kafkaProducer.close();
            this.kafkaProducer = null;
        }
    }

    public boolean testWithoutKafkaServer() {
        return this.testWithoutKafkaServer;
    }

    public String getKafkaTopic() {
        return this.KAFKA_TOPIC;
    }
    
    public static KafkaRegistry getInstance() {
        return Holder.INSTANCE;
    }

    private KafkaRegistry() {
        // TODO Auto-generated constructor stub
    }

    private static final class Holder {
        private static final KafkaRegistry INSTANCE = new KafkaRegistry();
    }

}
