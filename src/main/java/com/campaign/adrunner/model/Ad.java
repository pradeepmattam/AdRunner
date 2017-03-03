package com.campaign.adrunner.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class Ad {
	
  public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partner_id) {
		this.partnerId = partner_id;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getAdContent() {
		return adContent;
	}

	public void setAdContent(String ad_content) {
		this.adContent = ad_content;
	}

	public long getCreationTimeInMillis() {
		return creationTimeInMillis;
	}

	public void setCreationTimeInMillis(long creationTimeInMillis) {
		this.creationTimeInMillis = creationTimeInMillis;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonProperty(value="partner_id")
	private String partnerId;
  
  @JsonProperty(value="duration")
  private long duration;
  
  @JsonProperty(value="ad_content")
  private String adContent;
  
  @JsonIgnore(true)
  private long creationTimeInMillis;
  
  @JsonIgnore(true)
  private long id;
  
  
  public Ad() {
  }
  
  public Ad(String partnerId, long duration, String content, long getCreationTimeInMillis, long id) {
    setPartnerId(partnerId);
    setDuration(duration);
    setAdContent(content);
    setCreationTimeInMillis(getCreationTimeInMillis);
    setId(id);
  }
  
  public String toString() {
    return "[" + getId() 
        + ", " + getPartnerId()
        + ", " + getDuration()
        + ", " + getAdContent()
        + ", " + getCreationTimeInMillis()
        + "]";
  }
  
}
