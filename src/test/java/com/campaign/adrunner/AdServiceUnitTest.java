package com.campaign.adrunner;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.campaign.adrunner.controller.AdController;
import com.campaign.adrunner.model.Ad;
import com.campaign.adrunner.model.AdBuilder;
import com.campaign.adrunner.service.AdService;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class AdServiceUnitTest {

	@Mock
	AdService adServiceMock;
	private MockMvc mockMvc;
	private Ad ad;
	
	@Before
	public void setUp() throws Exception {
		AdController adController = new AdController();
		MockitoAnnotations.initMocks(this);
		adController.setAdServiceImpl(adServiceMock);
		ad = new AdBuilder()
        .partnerId("JUNIT")
        .duration(100)
        .adcontent("This is junit testcase")
        .build();
		List<Ad> adList = new ArrayList<Ad>();
		adList.add(ad);
        mockMvc = MockMvcBuilders.standaloneSetup(adController)
                .build();
		
	}

	@Test
	public void test() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
        byte[] requestJson =  objectMapper.writeValueAsBytes(ad);
		when(adServiceMock.add(isA(Ad.class))).thenReturn(ad);
		this.mockMvc.perform(post("/ad").content(requestJson).contentType(MediaType.APPLICATION_JSON).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
        .andExpect(status().isOk());
        
		ArgumentCaptor<Ad> argument = ArgumentCaptor.forClass(Ad.class);
        verify(adServiceMock, times(1)).add(argument.capture());
        verifyNoMoreInteractions(adServiceMock);
	}

}
