package org.gooru.gep.processor.kafka.producer;

import org.apache.kafka.clients.producer.BufferExhaustedException;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.InterruptException;
import org.apache.kafka.common.errors.SerializationException;
import org.gooru.gep.app.components.KafkaRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.json.JSONObject;


/**
 * @author mukul@gooru
 *  
 */
public class KafkaMessagePublisher {

	  private static final KafkaMessagePublisher INSTANCE = new KafkaMessagePublisher();
	  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaMessagePublisher.class);

	  private KafkaMessagePublisher() {
	  }

	  public static KafkaMessagePublisher getInstance() {
	    return INSTANCE;
	  }

	  public void sendMessage2Kafka(String topic, JSONObject eventBody) {

		  sendMessageToKafka(topic, eventBody);
	  }

	  private void sendMessageToKafka(String topic, JSONObject eventBody) {
	    Producer<String, String> producer = KafkaRegistry.getInstance().getKafkaProducer();
	    ProducerRecord<String, String> kafkaMsg;

	    kafkaMsg = new ProducerRecord<>(topic, eventBody.toString());

	    try {
	      if (producer != null) {
	        producer.send(kafkaMsg, (metadata, exception) -> {
	          if (exception == null) {
	            LOGGER.info("Message Delivered Successfully: Offset : " + metadata.offset() + " : Topic : " + metadata.topic() + " : Partition : "
	                    + metadata.partition() + " : Message : " + kafkaMsg);
	          } else {
	            LOGGER.error("Message Could not be delivered : " + kafkaMsg + ". Cause: " + exception.getMessage());
	          }
	        });
	        LOGGER.debug("Message Sent Successfully: " + kafkaMsg);
	      } else {
	        LOGGER.error("Not able to obtain producer instance");
	      }
	    } catch (InterruptException | BufferExhaustedException | SerializationException ie) {
	      // - If the thread is interrupted while blocked
	      LOGGER.error("sendMessageToKafka: to Kafka server:", ie);
	    }
	  }
}
