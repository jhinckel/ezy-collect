package com.ezy.collect.config.aws;

import java.net.URI;
import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode;
import io.awspring.cloud.sqs.operations.SqsAsyncOperations;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import io.awspring.cloud.sqs.support.converter.SqsMessagingMessageConverter;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class SqsConfig extends AwsConfig {

    @Bean
    @Primary
    @Profile("!local")
    public SqsAsyncClient sqsAsyncClient() {
        return SqsAsyncClient
                .builder()
                .credentialsProvider(super.credentialsProvider())
                .endpointOverride(URI.create(super.localstackUrl))
                .region(Region.of(super.region))
                .build();
    }
    
    @Bean
    @Primary
    @Profile("local")
    public SqsAsyncClient sqsLOcalAsyncClient() {
        return SqsAsyncClient
                .builder()
                .credentialsProvider(super.credentialsProvider())
                .endpointOverride(URI.create(super.localstackUrl))
                .region(Region.of(super.region))
                .build();
    }
    
    @Bean
    public SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory(SqsAsyncClient sqsAsyncClient) {
        return SqsMessageListenerContainerFactory
            .builder()
            .sqsAsyncClient(sqsAsyncClient)
            .configure(options -> options
                .maxMessagesPerPoll(5)
                .pollTimeout(Duration.ofSeconds(10))
                .acknowledgementMode(AcknowledgementMode.ON_SUCCESS)
            )
            .build();
    }
    
    @Bean
    public SqsMessagingMessageConverter sqsMessageConverter() {
        SqsMessagingMessageConverter converter = new SqsMessagingMessageConverter();
        
        converter.setObjectMapper(awsObjectMapper());
        
        return converter;
    }

    @Bean
    public SqsAsyncOperations template(SqsAsyncClient sqsAsyncClient) {
        return SqsTemplate.newAsyncTemplate(sqsAsyncClient);
    }
    
}
