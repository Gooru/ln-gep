package org.gooru.gep.app.components.utilities;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish 
 */
public final class DbLookupUtility {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbLookupUtility.class);

    public static DbLookupUtility getInstance() {
        return DbLookupUtility.Holder.INSTANCE;
    }

    private volatile boolean initialized = false;

    private DbLookupUtility() {
    }

    public void initialize(DataSource defaultDataSource) {
        if (!initialized) {
            synchronized (DbLookupUtility.Holder.INSTANCE) {
                if (!initialized) {
                    initializeFromDb(defaultDataSource);
                    initialized = true;
                }
            }
        }
    }

    private void initializeFromDb(DataSource dataSource) {

    }

    private static final class Holder {
        private static final DbLookupUtility INSTANCE = new DbLookupUtility();
    }

}
