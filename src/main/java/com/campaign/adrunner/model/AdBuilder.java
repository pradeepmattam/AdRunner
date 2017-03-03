package com.campaign.adrunner.model;

public class AdBuilder {
	  private Ad ad;
	  public AdBuilder() {
		  ad = new Ad();
	  }
	  public Ad build() {
		  return ad;
	  }
	  public AdBuilder partnerId(String partnerId){
		  ad.setPartnerId(partnerId);
		  return this;
	  }
	  public AdBuilder duration(long duration){
		  ad.setDuration(duration);
		  return this;
	  }
	  public AdBuilder adcontent(String  adContent){
		  ad.setAdContent(adContent);
		  return this;
	  }
}
