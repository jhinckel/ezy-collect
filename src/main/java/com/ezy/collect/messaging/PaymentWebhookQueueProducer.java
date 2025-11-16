package com.ezy.collect.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ezy.collect.domain.exception.MessagePublishingException;
import com.ezy.collect.rest.domain.request.PaymentWebhookRequestDto;
import com.ezy.collect.utils.JsonHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentWebhookQueueProducer {

    @Value("${cloud.aws.sqs.queues.payment-notification}")
    private String paymentNotificationQueue;
    @Value("${cloud.aws.sqs.queues.payment-notification-queue-type}")
    private String paymentNotificationQueueType;

    private final SimpleQueueComponent queueComponent;

    public void sendToNotificationQueue(PaymentWebhookRequestDto paymentWebhookNotification) {
        sendToQueue(paymentWebhookNotification, this.paymentNotificationQueue, this.paymentNotificationQueueType);
    }

    private void sendToQueue(PaymentWebhookRequestDto paymentWebhookNotification, String queue, String queueType) {
        try {
            queueComponent.sendMessage(queue, QueueType.fromValue(queueType), JsonHelper.writeValueAsString(paymentWebhookNotification));
        } catch (Exception e) {
            log.error("Error publishing message on queue {}", queue, e);
            throw new MessagePublishingException();
        }
    }
}
