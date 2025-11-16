package com.ezy.collect.domain.service;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import com.ezy.collect.domain.model.Payment;
import com.ezy.collect.repository.PaymentRepository;
import com.ezy.collect.rest.domain.mapper.PaymentMapper;
import com.ezy.collect.rest.domain.request.PaymentDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {	
	
	private final PaymentMapper paymentMapper = Mappers.getMapper(PaymentMapper.class);

    private final PaymentRepository paymentRepository;    
    private final PaymentWebhookNotificationService paymentWebhookNotificationService;

	public void createPayment(PaymentDto paymentRequest) {
		log.info("Creating new payment.");
        Payment payment = paymentMapper.toEntity(paymentRequest);
        
		paymentRepository.save(payment);
		
		log.info("Payment created!");
		
		paymentWebhookNotificationService.notifyPayment(paymentRequest);
		
		log.info("Payment notified!");
	}
	
}
