package com.ezy.collect.rest.controller

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc

import com.ezy.collect.domain.service.PaymentService
import com.ezy.collect.rest.domain.request.PaymentDto
import com.ezy.collect.utils.JsonHelper

import mock.MvcMock
import spock.lang.Shared
import spock.lang.Specification

class PaymentControllerTest extends Specification {

	@Shared
	MockMvc mockMvc
	@Shared
	PaymentService paymentService

	void setup() {
		paymentService = Mock(PaymentService)
		mockMvc = MvcMock.getMockMvc(new PaymentController(paymentService))
	}


	def "Create success payment"() {
		given:
		    def paymentDto = mockPaymentDto()
		when:
		    def mvcResult = mockMvc.perform(post("/v1/payments")
				.content(JsonHelper.writeValueAsString(paymentDto))
				.contentType(MediaType.APPLICATION_JSON))
		then:
			mvcResult.andExpect(status().isCreated())
	}

	def mockPaymentDto() {
		return new PaymentDto().tap {
            firstName = "John"
            lastName = "Doe"
            zipCode = "100200"
            cardNumber = "1234123412341234"
        }
	}

}
