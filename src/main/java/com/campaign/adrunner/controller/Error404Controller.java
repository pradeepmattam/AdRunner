package com.campaign.adrunner.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.campaign.adrunner.exception.AdException;
import com.campaign.adrunner.model.ErrorResponse;
import com.campaign.adrunner.utils.CampaignURIs;

@RequestMapping(value = CampaignURIs.ERROR_URI)
@Controller
public class Error404Controller {
	
	@RequestMapping(value = CampaignURIs.ERROR_404_URI, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorResponse error404() throws AdException {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.NOT_FOUND.value());
		error.setErrorMessage("Resource Not Found");
		return error;
	}

}
