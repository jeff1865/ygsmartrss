package com.ygsoft.rss;

import java.util.ArrayList;
import java.util.List;

import com.ygsoft.rss.data.ISiteDao;
import com.ygsoft.util.web.Anchor;
import com.ygsoft.util.web.AnchorFilter;

public class NewInfoExtracter {
	
	private ISiteDao siteDao = null;
	private String targetUrl = null;
	
	public NewInfoExtracter(String url){
		this.targetUrl = url;
	}
	
	public void setSiteDao(ISiteDao siteDao) {
		this.siteDao = siteDao;
	}
	
	public List<NewInfo> getNewInfo(){
		ArrayList<NewInfo> arrList = new ArrayList<NewInfo>();
		
		AnchorFilter test = new AnchorFilter(this.targetUrl);
		List<Anchor> anchorTexts = test.getAnchorTexts();
		
		if(this.siteDao == null)
		{
			;
		}
		return null;
	}
		
	public static void main(String ... v){
		;
	}
}
