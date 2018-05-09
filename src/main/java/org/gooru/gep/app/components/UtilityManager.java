package org.gooru.gep.app.components;

import org.gooru.gep.app.components.utilities.DbLookupUtility;
//import io.vertx.core.Vertx;
//import io.vertx.core.json.JsonObject;
import org.json.JSONObject;

/**
 * @author ashish 
 * This is a manager class to initialize the utilities, Utilities initialized
 * may depend on the DB or application state. Thus their initialization sequence
 * may matter. It is advisable to keep the utility initialization for last.
 */
public final class UtilityManager implements Initializer, Finalizer {
    private static final UtilityManager ourInstance = new UtilityManager();

    public static UtilityManager getInstance() {
        return ourInstance;
    }

    private UtilityManager() {
    }

    @Override
    public void finalizeComponent() {

    }

    @Override
    public void initializeComponent(JSONObject config) {
        // TODO: Initialize tenant machinery when the dependency is included
        //        TenantInitializer.initialize(DataSourceRegistry.getInstance().getDefaultDataSource());
        DbLookupUtility.getInstance().initialize(DataSourceRegistry.getInstance().getDefaultDataSource());
    }
}
