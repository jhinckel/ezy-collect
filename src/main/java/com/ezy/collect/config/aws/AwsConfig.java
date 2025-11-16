package com.ezy.collect.config.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.ezy.collect.utils.JsonHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

@Configuration
@RequiredArgsConstructor
//@EnableSqs
public abstract class AwsConfig {

    @Value("${cloud.aws.region.static}")
    protected String region;

    @Value("${cloud.aws.credentials.access-key}")
    protected String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    protected String secretKey;

    @Value("${cloud.aws.localstack.uri:}")
    protected String localstackUrl;

    @Bean(name ="awsCredentialsConfig")
    @Primary
    public AwsCredentialsProvider credentialsProvider() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(this.accessKey, this.secretKey));
    }

    protected ObjectMapper awsObjectMapper( ) {
    	return JsonHelper.getObjectMapper();
    }

}
