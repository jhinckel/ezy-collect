package com.ezy.collect.config;

import java.time.Duration;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.ezy.collect.adapter.WebhookAdapter;
import com.ezy.collect.utils.JsonHelper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
@Getter
@Slf4j
@RequiredArgsConstructor
public class WebClientConfig {

	private static ConnectionProvider defaultConnectionProvider(String name) {
		return ConnectionProvider.builder(name)
				.maxConnections(500)
				.maxIdleTime(Duration.ofSeconds(30))
				.maxLifeTime(Duration.ofSeconds(60))
				.pendingAcquireTimeout(Duration.ofSeconds(60))
				.evictInBackground(Duration.ofSeconds(120)).build();
	}

	@Bean("webhookWebClient")
	public WebClient webhookWebClient() {
	    ReactorClientHttpConnector httpClientConnector = getHttpClientConnector(WebhookAdapter.class);

		return getWebClientBuilder().clientConnector(httpClientConnector).build();
	}

    private static WebClient.Builder getWebClientBuilder() {
        return WebClient.builder().exchangeStrategies(getExchangeStrategies()).defaultHeaders(getDefaultHttpHeaders());
    }

    private static Consumer<HttpHeaders> getDefaultHttpHeaders() {
        return headers -> {
            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        };
    }

    private static ExchangeStrategies getExchangeStrategies() {
        Jackson2JsonEncoder encoder = new Jackson2JsonEncoder(JsonHelper.getObjectMapper());
        Jackson2JsonDecoder decoder = new Jackson2JsonDecoder(JsonHelper.getObjectMapper());

        return ExchangeStrategies
                .builder()
                .codecs(configurer -> {
                    configurer.defaultCodecs().jackson2JsonEncoder(encoder);
                    configurer.defaultCodecs().jackson2JsonDecoder(decoder);
                }).build();
    }


    private static ReactorClientHttpConnector getHttpClientConnector(Class<?> clazz) {
        HttpClient httpClient = HttpClient.create(defaultConnectionProvider(clazz.getSimpleName()));
        ReactorClientHttpConnector httpConnector = new ReactorClientHttpConnector(httpClient);

        return httpConnector;
    }

}
