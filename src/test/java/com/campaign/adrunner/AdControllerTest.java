package com.campaign.adrunner;


import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.campaign.adrunner.model.Ad;
import com.campaign.adrunner.model.AdBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
		locations = { "classpath:root-context.xml",
		"classpath:appServlet/servlet-context.xml" }
		)
public class AdControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    
    
	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void findAd() throws Exception{
		this.mockMvc.perform(get("/ad/ORG-ID").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$.[0].duration").value(10000))
        .andExpect(jsonPath("$.[0].partner_id").value("ORG-ID"))
        .andExpect(jsonPath("$.[0].ad_content").value("This is Organizations Ad"));
		
     }
	
	@Test
	public void deleteAd() throws Exception{
		this.mockMvc.perform(delete("/ad/ORG-ID").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$.[0].duration").value(10000))
        .andExpect(jsonPath("$.[0].partner_id").value("ORG-ID"))
        .andExpect(jsonPath("$.[0].ad_content").value("This is Organizations Ad"));
     }
	
	@Test
	public void postAd() throws Exception {
		
		Ad ad = new AdBuilder().duration(1).adcontent("JUNIT Spring Integration in progress").partnerId("INT-TEST").build();
		Ad ad2 = new AdBuilder().duration(1).adcontent("JUNIT Spring Integration in progress again").partnerId("INT-TEST").build();
	
		
		ObjectMapper objectMapper = new ObjectMapper();
        byte[] requestJson =  objectMapper.writeValueAsBytes(ad);

        ResultActions result = this.mockMvc.perform(post("/ad").content(requestJson).contentType(MediaType.APPLICATION_JSON).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk()) ;

        System.out.println(result);
        
        requestJson =  objectMapper.writeValueAsBytes(ad2);
        // the content should remain unchanged for JUNIT partner id
        result = this.mockMvc.perform(post("/ad").content(requestJson).contentType(MediaType.APPLICATION_JSON).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());
        System.out.println(result);
     
        this.mockMvc.perform(get("/ad/INT-TEST").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$.[0].ad_content").value("JUNIT Spring Integration in progress"));
        
        Thread.sleep(10000);
        
        // the content should remain unchanged for JUNIT partner id
        result = this.mockMvc.perform(post("/ad").content(requestJson).contentType(MediaType.APPLICATION_JSON).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());
        System.out.println(result);
        
        this.mockMvc.perform(get("/ad/INT-TEST").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.[0].ad_content").value("JUNIT Spring Integration in progress again"));
        
	}
	

}
