package com.ygsoft.rss.data;

import java.sql.Timestamp;

public class TargetSite {
	int siteId;
	String targetUrl;
	int checkIntervalMin;
	String name;
	Timestamp regDate, latestDate;
	String regUser;
	int checkStatus;
	
	public int getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String toString(){
		return "SiteID :" + this.siteId + "\n" + 
			    "TargetUrl :" + this.targetUrl + "\n" + 
			    "CheckIntervalMin :" + this.checkIntervalMin + "\n" +
			    "Name :" + this.name + "\n" +
			    "RegDate :" + this.regDate + "\n" +
			    "LatestDate :" + this.latestDate + "\n" +
			    "RegUser :" + this.regUser + "\n" +
			    "Status : " + this.checkStatus;
	}
	
	public Timestamp getLatestDate() {
		return latestDate;
	}

	public void setLatestDate(Timestamp latestDate) {
		this.latestDate = latestDate;
	}

	public void setCheckIntervalMin(int checkIntervalMin) {
		this.checkIntervalMin = checkIntervalMin;
	}
	
	public int getSiteId() {
		return siteId;
	}
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	public String getTargetUrl() {
		return targetUrl;
	}
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	public int getCheckIntervalMin() {
		return checkIntervalMin;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getRegDate() {
		return regDate;
	}
	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	public String getRegUser() {
		return regUser;
	}
	public void setRegUser(String regUser) {
		this.regUser = regUser;
	}
}
