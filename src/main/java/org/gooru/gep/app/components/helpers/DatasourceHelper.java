package org.gooru.gep.app.components.helpers;

import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.json.JSONObject;

public final class DatasourceHelper {
    private DatasourceHelper() {
        throw new AssertionError();
    }

    private static final String DEFAULT_DATA_SOURCE_TYPE = "ds.type";
    private static final String DS_HIKARI = "hikari";

    public static DataSource initializeDataSource(JSONObject dbConfig) {
        // The default DS provider is hikari, so if set explicitly or not set
        // use it, else error out
        String dsType = dbConfig.getString(DEFAULT_DATA_SOURCE_TYPE);
        if (!Objects.equals(dsType, DS_HIKARI)) {
            // No support
            throw new IllegalStateException("Unsupported data store type");
        }
        
        final HikariConfig config = new HikariConfig();

        dbConfig.keySet().forEach(key -> {
            switch (key) {
            case "dataSourceClassName":
              config.setDataSourceClassName(dbConfig.getString(key));
              break;
            case "jdbcUrl":
              config.setJdbcUrl(dbConfig.getString(key));
              break;
            case "username":
              config.setUsername(dbConfig.getString(key));
              break;
            case "password":
              config.setPassword(dbConfig.getString(key));
              break;
            case "autoCommit":
              config.setAutoCommit(dbConfig.getBoolean(key));
              break;
            case "connectionTimeout":
              config.setConnectionTimeout(dbConfig.getLong(key));
              break;
            case "idleTimeout":
              config.setIdleTimeout(dbConfig.getLong(key));
              break;
            case "maxLifetime":
              config.setMaxLifetime(dbConfig.getLong(key));
              break;
            case "connectionTestQuery":
              config.setConnectionTestQuery(dbConfig.getString(key));
              break;
            case "minimumIdle":
              config.setMinimumIdle(dbConfig.getInt(key));
              break;
            case "maximumPoolSize":
              config.setMaximumPoolSize(dbConfig.getInt(key));
              break;
            case "metricRegistry":
              throw new UnsupportedOperationException(key);
            case "healthCheckRegistry":
              throw new UnsupportedOperationException(key);
            case "poolName":
              config.setPoolName(dbConfig.getString(key));
              break;
            case "isolationInternalQueries":
              config.setIsolateInternalQueries(dbConfig.getBoolean(key));
              break;
            case "allowPoolSuspension":
              config.setAllowPoolSuspension(dbConfig.getBoolean(key));
              break;
            case "readOnly":
              config.setReadOnly(dbConfig.getBoolean(key));
              break;
            case "registerMBeans":
              config.setRegisterMbeans(dbConfig.getBoolean(key));
              break;
            case "catalog":
              config.setCatalog(dbConfig.getString(key));
              break;
            case "connectionInitSql":
              config.setConnectionInitSql(dbConfig.getString(key));
              break;
            case "driverClassName":
              config.setDriverClassName(dbConfig.getString(key));
              break;
            case "transactionIsolation":
              config.setTransactionIsolation(dbConfig.getString(key));
              break;
            case "validationTimeout":
              config.setValidationTimeout(dbConfig.getLong(key));
              break;
            case "leakDetectionThreshold":
              config.setLeakDetectionThreshold(dbConfig.getLong(key));
              break;
            case "dataSource":
              throw new UnsupportedOperationException(key);
            case "threadFactory":
              throw new UnsupportedOperationException(key);
            case "datasource":
              JSONObject datasource = dbConfig.getJSONObject(key);
              datasource.keySet().forEach(dataKey -> {
                config.addDataSourceProperty(dataKey, datasource.getString(dataKey));
              });

              break;
            }
          });

        return new HikariDataSource(config);
    }

}
