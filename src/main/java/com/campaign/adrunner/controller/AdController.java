package com.campaign.adrunner.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.campaign.adrunner.exception.AdException;
import com.campaign.adrunner.model.Ad;
import com.campaign.adrunner.model.ErrorResponse;
import com.campaign.adrunner.service.AdService;
import com.campaign.adrunner.utils.CampaignURIs;

@Controller
public class AdController {

	private static final Logger logger = LoggerFactory
			.getLogger(AdController.class);

	@Autowired
    private AdService adServiceImpl;

	public AdService getAdServiceImpl() {
		return adServiceImpl;
	}

	public void setAdServiceImpl(AdService adServiceImpl) {
		this.adServiceImpl = adServiceImpl;
	}

	@RequestMapping(value = CampaignURIs.HOME_URI, 
			method = RequestMethod.GET, 
			produces=MediaType.TEXT_HTML_VALUE)
	public String home(Locale locale, Model model) throws AdException {
		try
		{
			logger.info("Welcome home! The client locale is {}.", locale);
			Date date = new Date();
			DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
					DateFormat.LONG, locale);
			String formattedDate = dateFormat.format(date);
			model.addAttribute("serverTime", formattedDate);
			return "home";
		}catch(Exception e) {
			throw new AdException(e.getMessage());
		}
	}
	
	@RequestMapping(value = CampaignURIs.AD_POST_URI, 
			method = RequestMethod.POST , 
			produces=MediaType.APPLICATION_JSON_VALUE, 
			consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody 
	public Ad createAd(@RequestBody Ad ad)  throws AdException {
		try{
			logger.info("AdController.createAd:"+ ad);
			return adServiceImpl.add(ad);
		}catch(Exception e) {
			throw new AdException(e.getMessage());
		}
	}

	@RequestMapping(value = CampaignURIs.ADS_GET_URI, 
			method = RequestMethod.GET, 
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, List<Ad>> getAllAds()  throws AdException  {
		try{		
			logger.info("Inside AdController.getAllAds() method...");
			return adServiceImpl.getAll();
		}catch(Exception e) {
			throw new AdException(e.getMessage());
		}
	}

	@RequestMapping(value = CampaignURIs.AD_GET_URI, 
			method = RequestMethod.GET, 
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Ad> getAdsByParnerId(@PathVariable("partner_id") String partnerId)  throws AdException {
		try{
			logger.info("Inside AdController.getAdsByParnerId() method...");
			return adServiceImpl.findByPartnerId(partnerId);
		}catch(Exception e) {
			throw new AdException(e.getMessage());
		} 
	}

	@RequestMapping(value = CampaignURIs.AD_DELETE_URI, 
			method = RequestMethod.DELETE , 
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Ad> deleteAdsByPartnerId(@PathVariable("partner_id") String partnerId) throws AdException {
		try{		
			logger.info("Inside AdController.deleteAdsByPartnerId() method..."+partnerId);
			return adServiceImpl.deleteByParnterId(partnerId);
		}catch(Exception e) {
			throw new AdException(e.getMessage());
		} 		
	}
	
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(AdException.class)
	@ResponseBody
	public ErrorResponse exceptionHandler(Exception ex) throws AdException {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setErrorMessage(ex.getMessage());
		return error;
	}
	

}
