package com.ygsoft.rss;

import java.util.Date;

import com.ygsoft.rss.data.SiteDao;

public class RssGenerator {
	
	private String mainUrl = null;
	private SiteDao siteDao = null;
	
	public RssGenerator(String url){
		this.mainUrl = url;
	}
	
	public void setSiteDao(SiteDao siteDao){
		this.siteDao = siteDao;
	}
	
	// refresh database 
	public int refresh(){
		if(this.siteDao != null){
			return -1;
		}
		
		return 0;
	}
	
	public String getLatestData(Date startDate){
		return null;
	}
	
	public String getLatestData(int topArticle){
		return null;
	}
	
	public static void main(String ... v){
		;
	}
}
