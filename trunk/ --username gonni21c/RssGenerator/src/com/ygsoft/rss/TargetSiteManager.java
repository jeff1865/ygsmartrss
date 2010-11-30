package com.ygsoft.rss;

import java.util.*;

import com.ygsoft.rss.data.ISiteDao;

public class TargetSiteManager {
	
	private ISiteDao siteDao = null;
	
	public TargetSiteManager(ISiteDao siteDao){
		this.siteDao = siteDao;
	}
	
	public int addNewTargetSite(String url, int checkInterval, String siteName, String regUserName){
		return 0;
	}
	
	public List<TargetSite> getTargetSiteList(){
		return this.siteDao.getRegsiteList();
	}
}
