package com.campaign.adrunner.service;

import java.util.List;
import java.util.Map;

import com.campaign.adrunner.model.Ad;

public interface AdService {

    /**
     * Adds an ad entry if no active ads present for the partner_id.
     * @param ad    The ad to be added
     * @return  The added or existing ad entry for that partner_id
     */
    public Ad add(Ad ad);

    /**
     * Deletes an ad entry.
     * @param partner_id    The partner_id of the ad.
     * @return  The deleted ad entry
     */
    public List<Ad> deleteByParnterId(String partner_id);

    /**
     * Get all ad entries.
     * @return  The Map of ad entries
     */
    public Map<String, List<Ad>> getAll();

    /**
     * Finds an ad entry.
     * @param partner_id    The partner_ui of the ad.
     * @return  The found ad entry
     */
    public List<Ad> findByPartnerId(String partner_id);


}
