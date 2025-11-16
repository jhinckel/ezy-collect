package com.ezy.collect.config.database;

import java.util.Objects;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.core.env.Environment;

import com.ezy.collect.utils.DatabaseHelper;
import com.zaxxer.hikari.HikariConfig;

public class AbstractDatabaseConfig {

    public final static String[] MODEL_PACKAGES = {
            "com.ezy.collect.domain.model",
    };

    private static final String DATASOURCE_CONFIG_PATH = "spring.datasource.";

    @Autowired
    protected Environment environment;

    protected Properties getJpaProperties() {
        Properties properties = new Properties();

        for (JpaConfig property : JpaConfig.values()) {
            String configValue = environment.getProperty(property.getConfigKey());

            if (Objects.isNull(configValue)) {
                configValue = property.getConfigKey();
            }
            properties.put(property.getJpaPropertyKey(), configValue);
        }
        return properties;
    }

    protected HikariConfig getHikariConfig(DataSourceProperties properties, String persistenceUnitName) {
        HikariConfig hikariConfig = new HikariConfig();
        String configPrefixPath = DATASOURCE_CONFIG_PATH + persistenceUnitName + ".";

        hikariConfig.setJdbcUrl(properties.getUrl());
        hikariConfig.setUsername(properties.getUsername());
        hikariConfig.setPassword(properties.getPassword());

        hikariConfig.setConnectionTestQuery(DatabaseHelper.TEST_CONNECTION_SELECT);
        hikariConfig.setDriverClassName(environment.getProperty(configPrefixPath + HikariPoolProperties.DRIVER));
        hikariConfig.setSchema(environment.getProperty(configPrefixPath + HikariPoolProperties.SCHEMA));
        hikariConfig.setPoolName(environment.getProperty(configPrefixPath + HikariPoolProperties.POOL_NAME));
        hikariConfig.setMaximumPoolSize(environment.getProperty(configPrefixPath + HikariPoolProperties.MAXIMUM_POOL_SIZE, Integer.class, 20));
        hikariConfig.setMinimumIdle(environment.getProperty(configPrefixPath + HikariPoolProperties.MINIMUM_IDLE, Integer.class, 10));
        hikariConfig.setIdleTimeout(environment.getProperty(configPrefixPath + HikariPoolProperties.IDLE_TIMEOUT, Integer.class, 5000));
        hikariConfig.setMaxLifetime(environment.getProperty(configPrefixPath + HikariPoolProperties.MAX_LIFETIME, Integer.class, 1000));
        hikariConfig.setReadOnly(environment.getProperty(configPrefixPath + HikariPoolProperties.READ_ONLY, Boolean.class));

        return hikariConfig;
    }

}
