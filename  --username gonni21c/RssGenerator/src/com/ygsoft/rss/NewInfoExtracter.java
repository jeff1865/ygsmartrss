package com.ygsoft.rss;

import java.util.ArrayList;
import java.util.List;

import com.ygsoft.rss.data.BindHelper;
import com.ygsoft.rss.data.ISiteDao;
import com.ygsoft.rss.data.SiteDao;
import com.ygsoft.util.web.Anchor;
import com.ygsoft.util.web.AnchorFilter;

public class NewInfoExtracter {
	
	private ISiteDao siteDao = null;
	private String targetUrl = null;
	
	public NewInfoExtracter(String url){
		this.targetUrl = url;
	}
	
	public String getSiteId(){
		return "5";
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
//		if(this.siteDao != null)
//		{
//			for(NewInfo ni : lstNewInfo){
//				int checkUrl = this.siteDao.checkUrl(ni);
//				if(checkUrl == 1){
//					arrList.add(ni);
//				}
//			}
//		}
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
				niTemp.setAnchorText(an.getText().get(0));
				niTemp.setLink(an.getUrl());
				niTemp.setImg("img");
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
		SiteDao siteDao = new SiteDao(BindHelper.getSqlSessionFactory());
		niExt.setSiteDao(siteDao);
		
		List<NewInfo> newInfos = niExt.getNewInfo();
		System.out.println("-------------------------------------------");
		for(NewInfo ni : newInfos){
			System.out.println(ni);
		}
		
		System.out.println("New Data Count :" + newInfos.size());
	}
}
