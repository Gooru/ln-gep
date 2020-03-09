package org.gooru.gep.processor.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.gooru.gep.constants.Constants;
import org.gooru.gep.processor.KafkaMessageProcessorBuilder;
import org.gooru.gep.processor.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import io.netty.util.internal.StringUtil;
import org.json.JSONObject;

/**
 * @author mukul@gooru
 *  
 */
public class KafkaMessageConsumer implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaMessageConsumer.class);
	private KafkaConsumer<String, String> consumer = null;

	public KafkaMessageConsumer(KafkaConsumer<String, String> consumer) {
		this.consumer = consumer;

	}

	@Override
	public void run() {
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(200);
			for (ConsumerRecord<String, String> record : records) {
				switch (record.topic().split(Constants.Config.HYPHEN)[0]) {
				case Constants.Config.KAFKA_LOG_WRITER_USAGE_EVENTS_TOPIC:
					sendMessage(record.value());
					break;
				case Constants.Config.KAFKA_TEST_TOPIC:
					LOGGER.info("Test Kafka Consumer : {}", record.value());
					break;
				default:          
					LOGGER.warn("Message being read from the topic: {}", record.topic());
					sendMessage(record.value());
				}
			}
		}
	}
	
	private void sendMessage(String record) {
		//!StringUtil.isNullOrEmpty(record)
		if (record != null && !record.isEmpty()) {
			JSONObject eventObject = null;
			try {
				//LOGGER.info("RECEIVED RECORD :::: {}", record);
				eventObject = new JSONObject(record);
			} catch (Exception e) {
				LOGGER.warn("Kafka Message is not a JSONObject");
			}
			if (eventObject != null) {
				LOGGER.info("RECEIVED EVENT OBJECT :::: {}", eventObject);				
				MessageProcessor processor = KafkaMessageProcessorBuilder.buildKafkaProcessor(eventObject);
				if (processor != null) {
					processor.process();
				}					
						
			}
		} else {
			LOGGER.warn("NULL or Empty message can not be processed...");
		}
	}

}
