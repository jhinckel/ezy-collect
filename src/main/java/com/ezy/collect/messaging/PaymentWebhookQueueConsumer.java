package com.ezy.collect.messaging;

import static net.logstash.logback.argument.StructuredArguments.kv;

import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import com.ezy.collect.domain.service.PaymentWebhookNotificationService;
import com.ezy.collect.rest.domain.request.PaymentWebhookRequestDto;

import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentWebhookQueueConsumer {

    private final PaymentWebhookNotificationService notificationService;

    @SqsListener(value = "${cloud.aws.sqs.queues.payment-notification}")
    public void onMessage(PaymentWebhookRequestDto paymentNotification) {
        MDC.put("payment-notification", paymentNotification.getUrl());
        
        log.info("Reading notification from queue", kv("payload", paymentNotification));

        try {
            notificationService.notifyPayment(paymentNotification);
        } catch (Exception ex) {
            log.error("Failed to process payment notification.", ex);
            throw ex;
        } finally {
            MDC.clear();
        }
    }

}