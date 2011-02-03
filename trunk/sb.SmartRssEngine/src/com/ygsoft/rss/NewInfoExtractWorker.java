package com.ygsoft.rss;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;

import org.apache.log4j.Logger;

import com.ygsoft.rss.data.BindHelper;
import com.ygsoft.rss.data.ISiteDao;
import com.ygsoft.rss.data.JdbcSiteDao;
import com.ygsoft.rss.data.LowDataAccess;
import com.ygsoft.rss.data.NewInfo;
import com.ygsoft.rss.data.TargetSite;
import com.ygsoft.util.web.Anchor;
import com.ygsoft.util.web.AnchorFilter;

public class NewInfoExtractWorker extends Observable {
	
	public enum EWorkerStatus{
		Checking,
		ManualRefresh,
		Active,
		Stopped,
		Error,
		Unknown
	}
	
	Logger log = Logger.getLogger(NewInfoExtractWorker.class);
	
	private ISiteDao siteDao = null;
	private int siteId = -1;
	private TargetSite targetSite = null;
	private volatile EWorkerStatus status = EWorkerStatus.Stopped;
	private List<NewInfo> latestNewInfos = null;
	
	public NewInfoExtractWorker(ISiteDao siteDao, int id) throws CommonException {
		this.siteDao = siteDao;
		this.siteId = id;
		
		this.refreshTargetSiteInfo();
		
		this.latestNewInfos = new ArrayList<NewInfo>();
	}
	
	public List<NewInfo> getLatestNewInfos(){
		return this.latestNewInfos;
	}
	
	public synchronized EWorkerStatus getStatus() {
		return this.status;
	}
	
	public TargetSite getTargetSite() {
		return targetSite;
	}
	
	private void refreshTargetSiteInfo() throws CommonException {
		this.targetSite = this.siteDao.getTargetSite(this.siteId);
		if(this.targetSite.getCheckStatus() < 4)
			this.status = EWorkerStatus.ManualRefresh;
		else
			this.status = EWorkerStatus.Stopped;
	}
	
	public synchronized List<NewInfo> getNewInfo(){
		this.status = EWorkerStatus.Checking;
		
		List<NewInfo> arrList = new ArrayList<NewInfo>();
		try
		{
			AnchorFilter test = new AnchorFilter(this.targetSite.getTargetUrl());
			List<Anchor> anchorTexts = test.getAnchorTexts();
			this.printObjectList(anchorTexts);
			
			List<NewInfo> lstNewInfo = this.convertNewInfoList(anchorTexts);
			log.debug("Total extracted Info count : " + lstNewInfo.size());
	
			arrList = this.siteDao.checkUrls(lstNewInfo);
			this.convertAbsLink(arrList);
			
			log.debug("Total new Information count :" + arrList.size());
			arrList = this.filterDuplication(arrList);
			
			this.status = EWorkerStatus.Active;
			this.latestNewInfos = arrList;
		} catch(Exception e) {
			e.printStackTrace();
			this.status = EWorkerStatus.Error;
		}
		return arrList;
	}
	
	public List<NewInfo> filterDuplication(List<NewInfo> lstNewInfo){
		ArrayList<NewInfo> filteredInfos = new ArrayList<NewInfo>();
		HashSet<String> hsChecker = new HashSet<String>();
		for(NewInfo newInfo : lstNewInfo){
			if(hsChecker.contains(newInfo.getLink())){
				;//alToBeRemoved.add(lstNewInfo.indexOf(newInfo));
			} else {
				filteredInfos.add(newInfo);
				hsChecker.add(newInfo.getLink());
			}
		}
		
		return filteredInfos;		
	}
	
	private void convertAbsLink(List<NewInfo> lstInfo){
		WebUtil webUtil = new WebUtil();
		String tmp = null;
		for(NewInfo ni : lstInfo){
			tmp = ni.getLink();
			if(!tmp.startsWith("http://")){
				ni.setLink(webUtil.convertAbsAddr(this.targetSite.getTargetUrl(), tmp));
			}
		}
	}
	
	private List<NewInfo> convertNewInfoList(List<Anchor> anchs){
		ArrayList<NewInfo> lstNewInfo = new ArrayList<NewInfo>();
		
		NewInfo niTemp = null;
		for(Anchor an : anchs){
			if(an.getText() != null && an.getText().size() > 0 && an.getUrl().length() < 362)
			{
				niTemp = new NewInfo();
				niTemp.setSiteId("" + this.targetSite.getSiteId());
				for(String strV : an.getText()){
					if(strV.startsWith("[T]")){
						niTemp.setAnchorText(strV);
					} else if(strV.startsWith("[I]")){
//TODO need to change
						//niTemp.setImg("img");
						niTemp.setImg(strV);	// need to change abs path
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
		LowDataAccess lda = new LowDataAccess(BindHelper.getSqlSessionFactory());
		ISiteDao siteDao = new JdbcSiteDao(lda);
		
		NewInfoExtractWorker niExt = null;
		
		try {
			niExt = new NewInfoExtractWorker(siteDao, 66);
		} catch (CommonException e) {
			e.printStackTrace();
		}
		
		List<NewInfo> newInfos = niExt.getNewInfo();
		System.out.println("-------------------------------------------");
		for(NewInfo ni : newInfos){
			System.out.println(ni);
		}
		
		System.out.println("New Data Count :" + newInfos.size());
	}
}
