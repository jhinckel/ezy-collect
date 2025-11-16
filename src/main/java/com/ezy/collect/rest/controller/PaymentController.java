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
import com.ezy.collect.domain.service.PaymentService;
import com.ezy.collect.rest.controller.doc.PaymentControllerDocumentation;
import com.ezy.collect.rest.domain.request.PaymentDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/payments")
public class PaymentController implements PaymentControllerDocumentation {

	private final PaymentService paymentService;

    @PostMapping()
    public ResponseEntity<Void> createPayment(@RequestBody @Valid PaymentDto paymentRequest) {

        try {
            StopWatch stopWatch = StopWatch.createStarted();
            
            log.info("[{}] Receiving payment request: {}", AppConfig.SERVICE_NAME, kv("request", paymentRequest));

            paymentService.createPayment(paymentRequest);

            log.info("[{}] Payment created. Took {}ms.", AppConfig.SERVICE_NAME, stopWatch.getTime(TimeUnit.MILLISECONDS));

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } finally {
            MDC.clear();
        }
    }

}
