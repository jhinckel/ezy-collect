package com.ezy.collect.domain.service;

import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ezy.collect.adapter.WebhookAdapter;
import com.ezy.collect.domain.model.PaymentWebhook;
import com.ezy.collect.messaging.PaymentWebhookQueueProducer;
import com.ezy.collect.repository.PaymentWebhookRepository;
import com.ezy.collect.rest.domain.mapper.PaymentWebhookMapper;
import com.ezy.collect.rest.domain.request.PaymentDto;
import com.ezy.collect.rest.domain.request.PaymentWebhookNotificationDto;
import com.ezy.collect.rest.domain.request.PaymentWebhookRequestDto;
import com.ezy.collect.rest.domain.request.WebhookNotificationRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentWebhookNotificationService {
	
	private final PaymentWebhookMapper paymentWebhookMapper = Mappers.getMapper(PaymentWebhookMapper.class);
	
	private final WebhookAdapter webhookAdapter;
    private final PaymentWebhookRepository paymentWebhookRepository;
    private final PaymentWebhookQueueProducer paymentWebhookQueueProducer;

	public void addPaymentNotificationUrl(WebhookNotificationRequestDto webhookRequest) {
		PaymentWebhook paymentWebhook = paymentWebhookMapper.toEntity(webhookRequest);
		
		paymentWebhookRepository.save(paymentWebhook);
		
		log.info("New notification URL saved! {}", webhookRequest.getUrl());
	}
    
	@Async
	public void notifyPayment(PaymentDto paymentRequest) {
		List<PaymentWebhook> paymentWebhooks = paymentWebhookRepository.findAll();
		
		paymentWebhooks.forEach(paymentWebhook -> {
			PaymentWebhookRequestDto paymentWebhookNotification = paymentWebhookMapper.toPaymentWebhook(paymentRequest, paymentWebhook.getNotificationUrl());

			try {
				notifyPayment(paymentWebhookNotification);
			} catch (Exception e) {
				log.error("Error sending payment notification to {}. Fallback to notification queue.", paymentWebhookNotification.getUrl());
				paymentWebhookQueueProducer.sendToNotificationQueue(paymentWebhookNotification);
			}
		});
	}
	
	public void notifyPayment(PaymentWebhookRequestDto paymentRequest) {
		try {
			webhookAdapter.sendWebhook(paymentRequest);
		} catch (Exception e) {
			log.error("Error sending payment notification to {}.", paymentRequest.getUrl());
			throw e;
		}
	}


}
