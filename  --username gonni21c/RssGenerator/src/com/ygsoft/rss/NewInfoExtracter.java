package com.ygsoft.rss;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ygsoft.rss.data.BindHelper;
import com.ygsoft.rss.data.ISiteDao;
import com.ygsoft.rss.data.JdbcSiteDao;
import com.ygsoft.rss.data.LowDataAccess;
import com.ygsoft.rss.data.NewInfo;
import com.ygsoft.util.web.Anchor;
import com.ygsoft.util.web.AnchorFilter;

public class NewInfoExtracter {
	
	Logger log = Logger.getLogger(NewInfoExtracter.class);
	
	private ISiteDao siteDao = null;
	private String targetUrl = null;
	
	public NewInfoExtracter(String url){
		this.targetUrl = url;
	}
	
	public String getSiteId(){
		return "6";
	}
	
	public void setSiteDao(ISiteDao siteDao) {
		this.siteDao = siteDao;
	}
	
	public List<NewInfo> getNewInfo(){
		List<NewInfo> arrList = new ArrayList<NewInfo>();
		
		AnchorFilter test = new AnchorFilter(this.targetUrl);
		List<Anchor> anchorTexts = test.getAnchorTexts();
		this.printObjectList(anchorTexts);
		
		List<NewInfo> lstNewInfo = this.convertNewInfoList(anchorTexts);
		log.debug("Total extracted Info count : " + lstNewInfo.size());

		arrList = this.siteDao.checkUrls(lstNewInfo);
		
		return arrList;
	}
	
	private List<NewInfo> convertNewInfoList(List<Anchor> anchs){
		ArrayList<NewInfo> lstNewInfo = new ArrayList<NewInfo>();
		
		NewInfo niTemp = null;
		for(Anchor an : anchs){
			if(an.getText() != null && an.getText().size() > 0 && an.getUrl().length() < 362)
			{
				niTemp = new NewInfo();
				niTemp.setSiteId(this.getSiteId());
				for(String strV : an.getText()){
					if(strV.startsWith("[T]")){
						niTemp.setAnchorText(strV);
					} else if(strV.startsWith("[I]")){
						niTemp.setImg("img");
					}
				}
				niTemp.setLink(an.getUrl());
				lstNewInfo.add(niTemp);
			} else {
				if(an.getUrl().length() > 361)
					System.out.println("--->>> Cannot proc because of long path :" + an);
			}
		}
		
		return lstNewInfo;
	}
	
	private void printObjectList(List<? extends Object> objList){
		for(Object o : objList){
			System.out.println(o);
		}
	}
		
	public static void main(String ... v){
		NewInfoExtracter niExt = new NewInfoExtracter("http://www.daum.net");
		//ISiteDao siteDao = new SiteDao(BindHelper.getSqlSessionFactory());
		LowDataAccess lda = new LowDataAccess(BindHelper.getSqlSessionFactory());
		ISiteDao siteDao = new JdbcSiteDao(lda);
		niExt.setSiteDao(siteDao);
		
		List<NewInfo> newInfos = niExt.getNewInfo();
		System.out.println("-------------------------------------------");
		for(NewInfo ni : newInfos){
			System.out.println(ni);
		}
		
		System.out.println("New Data Count :" + newInfos.size());
	}
}
