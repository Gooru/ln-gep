package org.gooru.gep.app.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.json.JSONObject;

public final class AppConfiguration implements Initializer {
    private static final String APP_CONFIG_KEY = "app.configuration";
    private JSONObject configuration;
    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfiguration.class);

    public static AppConfiguration getInstance() {
        return Holder.INSTANCE;
    }

    private volatile boolean initialized = false;

    private AppConfiguration() {
    }

    @Override
    public void initializeComponent(JSONObject config) {
        if (!initialized) {
            synchronized (Holder.INSTANCE) {
                if (!initialized) {
                    JSONObject appConfiguration = config.getJSONObject(APP_CONFIG_KEY);                	
                    if ((appConfiguration == null) || (appConfiguration.length() == 0)) {
                        LOGGER.warn("App configuration is not available");
                    } else {
                    	configuration = appConfiguration;
                        initialized = true;
                    }
                }
            }
        }
    }

    public int getConfigAsInt(String key) {
        return configuration.getInt(key);
    }

    public boolean getConfigAsBoolean(String key) {
        return configuration.getBoolean(key);        
    }

    public String getConfigAsString(String key) {
        return configuration.getString(key);
    }

    public Object getConfigAsRawObject(String key) {
        return configuration.get(key);
    }

    public int getDefaultOffset() {
        return configuration.getInt("default.offset");
    }

    public int getDefaultLimit() {
        return configuration.getInt("default.limit");
    }

    public int getDefaultMaxLimit() {
        return configuration.getInt("default.max.limit");
    }

    private static final class Holder {
        private static final AppConfiguration INSTANCE = new AppConfiguration();
    }

}
