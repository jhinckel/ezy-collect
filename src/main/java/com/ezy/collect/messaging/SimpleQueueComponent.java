package com.ezy.collect.messaging;

import java.util.Map;

import org.springframework.stereotype.Component;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SimpleQueueComponent {

    private final SqsTemplate sqsTemplate;

    public void sendMessage(String queueName, QueueType queueType, Object payload) {
        Map<String, Object> queueHeaders = getQueueHeaders(queueType);
        this.sqsTemplate.send(queueName, payload);
        
        log.info("Message {} sent to {} queue", payload, queueName);
    }

//    public void convertAndSend(String queue, Object payload, Map<String, Object> headers) {
//        try {
//            queueMessagingTemplate.convertAndSend(queue, payload, headers);
//            
//            log.debug("SimpleQueueComponent.convertAndSend successfully sent {}", queue);
//        } catch(MessagingException ex) {
//            log.debug("SimpleQueueComponent.convertAndSend failed queue={}, err={} {}", queue, ex.getMessage(), ex.getFailedMessage());
//            throw ex;
//        }
//    }

    private static Map<String, Object> getQueueHeaders(QueueType queueType) {
        if (queueType == QueueType.FIFO) {
            return SqsHelper.createDefaultQueueHeaders();
        }
        return SqsHelper.createQueueHeaders(null);
    }

}
