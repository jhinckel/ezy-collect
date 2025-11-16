package com.ezy.collect.adapter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.ezy.collect.rest.domain.request.PaymentDto;
import com.ezy.collect.rest.domain.request.PaymentWebhookRequestDto;
import com.ezy.collect.rest.domain.response.PaymentNotificationDto;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Setter
@Slf4j
public class WebhookAdapter extends DefaultAdapter {
	
    @Qualifier("webhookWebClient")
    private final WebClient webClient;

    public WebhookAdapter(@Qualifier(value = "webhookWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Async
    public void sendWebhook(PaymentWebhookRequestDto paymentnNotification) {    	
        log.info("Sending payment notification webhook to {}. Payload: {}", paymentnNotification.getUrl(), paymentnNotification.getPayment());

        try {
        	sendNotification(paymentnNotification.getPayment(), paymentnNotification.getUrl());

        	log.info("Webhook sent to {}.", paymentnNotification.getUrl());
        } catch (WebClientResponseException ex) {
        	log.error("sendWebhook WebClientResponseException url={} statusCode={} responseBody={}", paymentnNotification.getUrl(), ex.getRawStatusCode(), ex.getResponseBodyAsString());
        	throw ex;
        } catch (Exception ex) {
        	log.error("sendWebhook Exception url={} message={}", paymentnNotification.getUrl(), ex.getMessage());
        	throw ex;
        }
    }

    private void sendNotification(PaymentDto webhook, String url) {
        Mono<Void> response = webClient.post()
                .uri(url)
                //.header(HttpHeaders.AUTHORIZATION, "")
                .body(Mono.just(webhook), PaymentNotificationDto.class)
                .retrieve()
                .bodyToMono(Void.class)
                .retryWhen(DEFAULT_RETRY_FILTER);
        response.block();
    }

}
