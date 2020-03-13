package org.gooru.gep.bootstrap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.Executors;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.gooru.gep.app.components.Initializer;
import org.gooru.gep.app.components.Initializers;
import org.gooru.gep.constants.Constants;
import org.gooru.gep.processor.kafka.KafkaMessageConsumer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

public class AppRunner {
  private static final Logger LOGGER = LoggerFactory.getLogger(AppRunner.class);
  private static final String KAFKA_CONFIG = "kafka.consumer.config";
  private static final String SYSTEM_PROPERTIES = "systemProperties";
  private static final String LOGBACK_CONFIG = "logback.configurationFile";
  private static JSONObject conf;
  private static final String KAFKA_ASDP_CONFIG = "kafka.asdp.consumer.config";
  private KafkaConsumer<String, String> consumer = null;

  public static void main(String[] args) {
    if (args.length != 1) {
      throw new IllegalArgumentException("No configuration file found!");
    }
    AppRunner runner = new AppRunner();
    AppRunner.initializeConfig(args[0]);
    runner.run();
  }

  private void run() {
    setupSystemProperties();
    this.initializeApplication();
  }

  private static void setupSystemProperties() {
    JSONObject systemProperties = conf.getJSONObject(SYSTEM_PROPERTIES);
    systemProperties.keySet().forEach(key -> {
      String propValue = systemProperties.getString(key);
      System.setProperty(key, propValue);
    });

    String logbackFile = System.getProperty(LOGBACK_CONFIG);
    if (logbackFile != null && !logbackFile.isEmpty()) {
      setupLoggerMachinery(logbackFile);
    }
  }

  private static void setupLoggerMachinery(String logbackFile) {
    LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

    try {
      JoranConfigurator configurator = new JoranConfigurator();
      configurator.setContext(context);
      context.reset();
      configurator.doConfigure(logbackFile);
    } catch (JoranException je) {
      // StatusPrinter will handle this
    }

    StatusPrinter.printInCaseOfErrorsOrWarnings(context);

  }

  private void initializeApplication() {

    Initializers initializers = new Initializers();
    for (Initializer initializer : initializers) {
      initializer.initializeComponent(conf);
    }
    createConsumer(conf);
    createASDPConsumer(conf);

  }

  private void createConsumer(JSONObject config) {
    LOGGER.debug("Configuring Kafka consumer");

    JSONObject kafkaConfig = config.getJSONObject(KAFKA_CONFIG);
    if (kafkaConfig == null || kafkaConfig.length() == 0) {
      throw new IllegalStateException("No configuaration found for Kafka Consumer");
    }
    Properties props = new Properties();
    LOGGER
        .debug("kafka config props" + kafkaConfig.getString(Constants.Config.CONFIG_KAFKA_SERVERS));
    props.put(Constants.Config.CONFIG_KAFKA_SERVERS,
        kafkaConfig.getString(Constants.Config.CONFIG_KAFKA_SERVERS));
    props.put(Constants.Config.CONFIG_KAFKA_TIME_OUT_IN_MS,
        kafkaConfig.getString(Constants.Config.CONFIG_KAFKA_TIME_OUT_IN_MS));
    props.put(Constants.Config.CONFIG_KAFKA_GROUP,
        kafkaConfig.getString(Constants.Config.CONFIG_KAFKA_GROUP));
    props.put(Constants.Config.CONFIG_KAFKA_KEY_DESERIALIZER, StringDeserializer.class.getName());
    props.put(Constants.Config.CONFIG_KAFKA_VALUE_DESERIALIZER, StringDeserializer.class.getName());
    consumer = new KafkaConsumer<>(props);
    String[] topics =
        kafkaConfig.getString(Constants.Config.CONFIG_KAFKA_TOPICS).split(Constants.Config.COMMA);
    consumer.subscribe(Arrays.asList(topics));
    Executors.newFixedThreadPool(10).submit(new KafkaMessageConsumer(consumer));
  }

  private void createASDPConsumer(JSONObject config) {
    LOGGER.debug("Configuring Kafka consumer");

    JSONObject kafkaConfig = config.getJSONObject(KAFKA_ASDP_CONFIG);
    if (kafkaConfig == null || kafkaConfig.length() == 0) {
      throw new IllegalStateException("No configuaration found for Kafka Consumer");
    }
    Properties props = new Properties();
    LOGGER
        .debug("kafka config props" + kafkaConfig.getString(Constants.Config.CONFIG_KAFKA_SERVERS));
    props.put(Constants.Config.CONFIG_KAFKA_SERVERS,
        kafkaConfig.getString(Constants.Config.CONFIG_KAFKA_SERVERS));
    props.put(Constants.Config.CONFIG_KAFKA_TIME_OUT_IN_MS,
        kafkaConfig.getString(Constants.Config.CONFIG_KAFKA_TIME_OUT_IN_MS));
    props.put(Constants.Config.CONFIG_KAFKA_GROUP,
        kafkaConfig.getString(Constants.Config.CONFIG_KAFKA_GROUP));
    props.put(Constants.Config.CONFIG_KAFKA_KEY_DESERIALIZER, StringDeserializer.class.getName());
    props.put(Constants.Config.CONFIG_KAFKA_VALUE_DESERIALIZER, StringDeserializer.class.getName());
    consumer = new KafkaConsumer<>(props);
    String[] topics =
        kafkaConfig.getString(Constants.Config.CONFIG_KAFKA_TOPICS).split(Constants.Config.COMMA);
    consumer.subscribe(Arrays.asList(topics));
    Executors.newFixedThreadPool(10).submit(new KafkaMessageConsumer(consumer));
  }

  private static void initializeConfig(String configFile) {
    if (configFile != null) {
      try (Scanner scanner = new Scanner(new File(configFile)).useDelimiter("\\A")) {
        String sconf = scanner.next();
        try {
          conf = new JSONObject(sconf);
          // TODO: Change to suitable JSON Parsing Exception
        } catch (Exception e) {
          LOGGER.error("Configuration file " + sconf + " does not contain a valid JSON object");
          throw e;
        }
      } catch (FileNotFoundException e) {
        try {
          conf = new JSONObject(configFile);
        } catch (Exception de) {
          LOGGER.error("Argument does not point to a file and is not valid JSON: " + configFile);
          throw de;
        }
      }
    } else {
      LOGGER.error("Null file path");
      throw new IllegalArgumentException("Null configuration file");
    }
  }

  public static JSONObject getGlobalConfiguration() {
    return conf;
  }

}
