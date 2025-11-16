package com.ezy.collect.rest.domain.request;

import org.hibernate.validator.constraints.URL;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebhookNotificationRequestDto {

	@URL
	private String url;
	
}
