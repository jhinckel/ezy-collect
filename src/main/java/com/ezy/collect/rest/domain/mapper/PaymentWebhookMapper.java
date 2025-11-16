package com.ezy.collect.rest.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.ezy.collect.domain.model.PaymentWebhook;
import com.ezy.collect.rest.domain.request.PaymentDto;
import com.ezy.collect.rest.domain.request.PaymentWebhookRequestDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentWebhookMapper {
	
    @Mapping(source = "url", target = "notificationUrl")
	PaymentWebhook toEntity(PaymentWebhookRequestDto webhookRequest);
	
    @Mapping(source = "paymentRequest.firstName", target = "payment.firstName")
    @Mapping(source = "paymentRequest.lastName", target = "payment.lastName")
    @Mapping(source = "paymentRequest.zipCode", target = "payment.zipCode")
    @Mapping(source = "paymentRequest.cardNumber", target = "payment.cardNumber")
    @Mapping(source = "url", target = "url")
    PaymentWebhookRequestDto toPaymentWebhook(PaymentDto paymentRequest, String url);
    
//    @Named("toPaymentWebhookRequestDto")
//    default PaymentWebhookRequestDto toPaymentWebhookRequestDto(String url) {
//    	return PaymentWebhookRequestDto.builder().url(url).build();
//    }

}
