package com.ezy.collect.config.property;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
@Slf4j
public class AdditionalPropertiesConfiguration {

    private static final String[] CONFIG_PATHS = {
            // "git.properties",
            //"/META-INF/build-info.properties"
    };

    @Bean
    public PropertySourcesPlaceholderConfigurer gitPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propsConfig = new PropertySourcesPlaceholderConfigurer();

        propsConfig.setIgnoreResourceNotFound(true);
        propsConfig.setIgnoreUnresolvablePlaceholders(true);

        propsConfig.setPropertiesArray(loadProperties());

        return propsConfig;
    }

    private Properties[] loadProperties() {
        List<Properties> props = new ArrayList<>();

        for (String propFile : CONFIG_PATHS) {
            try {
                Properties properties = new Properties();

                properties.load(new ClassPathResource(propFile).getInputStream());

                props.add(properties);
            } catch (IOException e) {
                log.error("Error reading property file {}", propFile, e);
            }
        }
        return props.toArray(new Properties[props.size()]);
    }

}
