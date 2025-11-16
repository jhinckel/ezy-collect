package com.ezy.collect.rest.controller;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezy.collect.config.AppConfig;
import com.ezy.collect.domain.service.PaymentWebhookNotificationService;
import com.ezy.collect.rest.controller.doc.PaymentWebhookNotificationControllerDocumentation;
import com.ezy.collect.rest.domain.request.PaymentWebhookRequestDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/webhooks")
public class PaymentWebhookNotificationController implements PaymentWebhookNotificationControllerDocumentation {

	private final PaymentWebhookNotificationService notificationService;

    @PostMapping()
    public ResponseEntity<Void> registerNewWebhookUrl(@RequestBody @Valid PaymentWebhookRequestDto webhookRequest) {
        try {
            StopWatch stopWatch = StopWatch.createStarted();
            
            log.info("[{}] Receiving new webhook notification URL request: {}", AppConfig.SERVICE_NAME, kv("request", webhookRequest));

            notificationService.addPaymentNotificationUrl(webhookRequest);

            log.info("[{}] New webhook notification URL created. Took {}ms.", AppConfig.SERVICE_NAME, stopWatch.getTime(TimeUnit.MILLISECONDS));

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } finally {
            MDC.clear();
        }
    }

}
