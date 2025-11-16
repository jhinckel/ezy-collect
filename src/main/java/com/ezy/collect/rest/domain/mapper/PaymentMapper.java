package com.ezy.collect.rest.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.ezy.collect.domain.model.Payment;
import com.ezy.collect.domain.model.PaymentCard;
import com.ezy.collect.rest.domain.request.PaymentDto;
import com.ezy.collect.rest.domain.response.PaymentNotificationDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {
	
	@Mapping(target = "paymentCard", source = "cardNumber", qualifiedByName = "getPaymentCard")
    Payment toEntity(PaymentDto payment);

    PaymentNotificationDto toPaymentNotification(PaymentDto payment);

    @Named("getPaymentCard")
    default PaymentCard getPaymentCard(String cardNumber) {
    	return new PaymentCard(cardNumber);
    }

}
