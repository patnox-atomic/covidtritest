package com.patnox.covidtritest;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

//import org.junit.Before;
//import org.junit.Test;
import com.patnox.covidtritest.patients.PatientController;
import com.patnox.covidtritest.patients.PatientService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.web.reactive.function.client.*;

@AutoConfigureMockMvc
@WithMockUser
@Slf4j
@TestPropertySource(locations = "classpath:test.properties")
public class TestPatients extends CovidTriTestApplicationTests
{
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PatientService productService;
	
	@MockBean
	private PatientController productController;
	
//	Product prd1 = new Product(
//			"ButterToast",
//			"Milk Bread",
//			"3727182734584",
//			732.00D,
//			500L,
//			30L,
//			600L,
//			false
//	);
	
	String newProduct = "{\n"
			+ "    \"name\": \"TUZO 500ML\",\n"
			+ "    \"description\": \"Tuzo super milk\",\n"
			+ "    \"barcode\": \"473654733344\",\n"
			+ "    \"price\": 50.0,\n"
			+ "    \"quantity\": 7000,\n"
			+ "    \"reorder_level\": 400,\n"
			+ "    \"reorder_quantity\": 5600,\n"
			+ "    \"is_deleted\": false\n"
			+ "}";

	@Test
	public void testAddingOrder() throws Exception 
	{
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		//Mockito.when(productService.getProduct(Mockito.anyLong())).thenReturn(prd1);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/product")
				.content(newProduct)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		log.info("Got post 1 response as: {}", response.getStatus());
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@TestConfiguration
    public static class WebClientTestConfiguration 
    {
        @Bean
        public WebClient getWebClient(final WebClient.Builder builder) 
        {
            WebClient webClient = builder
                    .baseUrl("http://localhost")
                    .build();
            log.info("WebClient Instance Created During Testing, using static inner class: {}", webClient);
            return webClient;
        }
    }
	
}
