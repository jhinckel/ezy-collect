package com.ezy.collect.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    public static final String LOCAL_PROFILE = "local";

    public static String SERVICE_NAME;

    @Value("${service.name}")
    public void setAppName(String serviceName) {
        AppConfig.SERVICE_NAME = serviceName;
    }

}
