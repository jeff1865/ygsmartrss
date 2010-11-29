package com.ygsoft.rss;

import java.sql.Timestamp;

public class TargetSite {
	int siteId;
	String targetUrl;
	int checkIntervalMin;
	String name;
	Timestamp regDate, latest_da;
	String regUser;
	
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
	public void setCheckInterval(int checkIntervalMin) {
		this.checkIntervalMin = checkIntervalMin;
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
	public Timestamp getLatest_da() {
		return latest_da;
	}
	public void setLatest_da(Timestamp latest_da) {
		this.latest_da = latest_da;
	}
	public String getRegUser() {
		return regUser;
	}
	public void setRegUser(String regUser) {
		this.regUser = regUser;
	}
	
}
