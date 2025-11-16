package com.ezy.collect.config.database;

import javax.sql.DataSource;

import org.apache.commons.lang3.BooleanUtils;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class FlywayConfig {

    private final DataSource readWriteDataSource;

    @Value("${spring.flyway.enabled}")
    private Boolean isFlywayEnabled;

    public FlywayConfig(@Qualifier("readWriteDataSource") DataSource readWriteDataSource) {
        this.readWriteDataSource = readWriteDataSource;
    }

    @Bean
    public Flyway configureFlyway() {
        if (BooleanUtils.isTrue(isFlywayEnabled)) {
            Flyway flyway = Flyway.configure()
                    .dataSource(readWriteDataSource)
                    .locations("database/flyway")
                    .load();

            try {
                flyway.migrate();
            } catch (Exception e) {
                log.error("Failed to apply migrations :( . Check migrations files...", e);
                throw e;
            }
            return flyway;
        }
        return null;
    }

}
