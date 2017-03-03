package com.campaign.adrunner.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.campaign.adrunner.model.Ad;

@Service
public class AdServiceImpl implements AdService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdServiceImpl.class);

	private Map<String, List<Ad>> ads = new HashMap <String, List<Ad>>();
	
	public AdServiceImpl(){
		List<Ad> orgAds = new ArrayList<Ad>();
		orgAds.add(new Ad("ORG-ID",10000,"This is Organizations Ad", new Date().getTime(),0));
		List<Ad> spnsrAds = new ArrayList<Ad>();
		spnsrAds.add(new Ad("SPNSR-ID",10000,"This is Sponsors Ad", new Date().getTime(),0));

		ads.put("ORG-ID",orgAds );
		ads.put("SPNSR-ID",spnsrAds);

	}
	
	@Override
	public Ad add(Ad ad) {
        LOGGER.info("Received a new ad entry with information: {} for add", ad);
        List<Ad> xAds = ads.get(ad.getPartnerId());
		ad.setCreationTimeInMillis(new Date().getTime());
		Ad xAd = null;
		if(xAds!=null && xAds.size()>0){
			xAd = xAds.get(0);
			if((ad.getCreationTimeInMillis() - xAd.getCreationTimeInMillis())/1000 > xAd.getDuration()){
					xAds.remove(0);
					ad.setId(xAds.size());
					xAds.add(ad);
					ads.put(ad.getPartnerId(), xAds);
			        LOGGER.info("Ad Postedd for : {} as the past one expired", ad.getPartnerId());

				return ad;
			}
		}else {
			List<Ad> adList = new ArrayList<Ad>();
			adList.add(ad);
			ads.put(ad.getPartnerId(), adList);
			LOGGER.info("Brand new Ad Postedd for : {} ", ad.getPartnerId());
			return ad;
		}
		return xAd;
	}

	@Override
	public List<Ad> deleteByParnterId(String partner_id) {
		List<Ad> adList = ads.remove(partner_id);
		if (ads != null) {
			LOGGER.info("Inside deleteAdByPartnerId, delete attemptedd= \n Latest List of Ads: - "	+ ads.toString());
		}
		return adList;
	}

	@Override
	public Map<String, List<Ad>> getAll() {
		LOGGER.info("Inside getAll*****");

		return ads;
	}

	@Override
	public List<Ad> findByPartnerId(String partner_id) {
		List<Ad> adList = ads.get(partner_id);
		if (ads != null) {
			LOGGER.info("Inside getAdByParnerId, returned: "+ adList.toString());
		} else {
			LOGGER.info("Inside getAdByParnerId, partnerId: " + partner_id	+ ", NOT FOUND!");
		}

		for (Ad ad: adList) {
			if((ad.getCreationTimeInMillis() + ad.getDuration()*1000)< new Date().getTime())
				ad.setExpired(true);
		}
		return adList;
	}

}
