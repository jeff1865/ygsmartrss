package com.ygsoft.rss;

import java.io.File;
import java.util.*;

import org.apache.log4j.Logger;

import com.ygsoft.rss.data.NewInfo;
import com.ygsoft.rss.data.TargetSite;

/**
 * Scheduler
 * @author Gonni
 *
 */
public class SmartRssService extends Observable implements Runnable {
	
	private Logger log = Logger.getLogger(SmartRssService.class);
	
	private TargetSiteManager targetSiteManager = null;
	private ArrayList<NewInfoExtractWorker> lstTargetSite = null;
	private Thread thScheduler = null;
	private volatile boolean runShc = false;
	private long timeLine = 0;
	
	public SmartRssService(TargetSiteManager tsm){
		this.targetSiteManager = tsm;
		this.lstTargetSite = new ArrayList<NewInfoExtractWorker> ();
	} 
	
	public void loadAll(){
		List<TargetSite> tsl = this.targetSiteManager.getTargetSiteList();
		for(TargetSite ts : tsl){
			NewInfoExtractWorker ce = this.targetSiteManager.createExtractor(ts);
			if(ce != null)
				this.lstTargetSite.add(ce);
			else
				log.debug("Invalide site information ..");
		}
	}
	
	public List<NewInfoExtractWorker> getMonitorSiteList(){
		return this.lstTargetSite;
	}
	
	public void start(){
		if(thScheduler == null){
			this.thScheduler = new Thread(this);
		}
		this.thScheduler.start();
	}
		
	public void stop(){
		this.runShc = false;
		this.thScheduler.interrupt();
	}
	
	@Override
	public void run() {
		int checkTime = 0;
		
		while(this.runShc){
			checkTime = refreshInfos();
			
			if(checkTime > 1) this.timeLine += checkTime;
			else this.timeLine ++;
			
			try {
				Thread.sleep(60 * 1000);
			} catch (InterruptedException e) {
				this.runShc = false;
			}
		}
	}
	
	// TODO PageTemplate 추출루틴 구현 - 사이트별로 가능하도록 변형
	public int refreshInfos(){
		log.debug("Refresh SITE info ..");
		
		long initTime = Calendar.getInstance().getTimeInMillis();
		
		for(NewInfoExtractWorker nie : this.lstTargetSite){
			//TODO need to set property value
			String strFileName = "/rss/SiteRSS" + nie.getTargetSite().getSiteId() + ".xml";
			try {
				new RssXmlBuilder(nie.getTargetSite(), nie.getNewInfo(), new File(strFileName)).build();
			} catch (CommonException e) {
				log.debug("" + e.getMessage());
				e.printStackTrace();
			}
		}
		
		initTime = Calendar.getInstance().getTimeInMillis() - initTime;
		return (int)(initTime / (60 * 1000));
	}
	
	//TODO template 규칙 검사 - thread scheduling 필요
	public void checkTemplate(List<NewInfo> newInfos){
		;
	}
	
	public int refreshInfo(int siteId){
		int res = -1;
		for(NewInfoExtractWorker niew : this.lstTargetSite){
			if(niew.getTargetSite().getSiteId() == siteId) {
				log.info("Refresh RSS Information :" + niew.getTargetSite().getName());
				
				String strFileName = "rss/SiteRSS" + niew.getTargetSite().getSiteId() 
							+ "_" + Calendar.getInstance().getTimeInMillis() +".xml";
				try {
					List<NewInfo> newInfos = niew.getNewInfo();
					//TODO need to cache
					System.out.println("========> EXTed New Info Count : " + newInfos.size());
					
					new RssXmlBuilder(niew.getTargetSite(), newInfos, new File(strFileName)).build();
					
					res = newInfos.size();
				} catch (CommonException e) {
					log.debug("" + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return res;
	}
	
	public static void main(String ... v){
		Calendar.getInstance().getTimeInMillis();
	}
	
}
