package com.ygsoft.rss;

import java.io.File;
import java.util.*;

import org.apache.log4j.Logger;

import com.ygsoft.rss.data.TargetSite;

/**
 * Scheduler
 * @author Gonni
 *
 */
public class SmartRssService extends Observable implements Runnable {
	
	private Logger log = Logger.getLogger(SmartRssService.class);
	
	private TargetSiteManager targetSiteManager = null;
	private ArrayList<NewInfoExtracter> lstTargetStie = null;
	private Thread thScheduler = null;
	private volatile boolean runShc = false;
	private long timeLine = 0;
	
	public SmartRssService(TargetSiteManager tsm){
		this.targetSiteManager = tsm;
		this.lstTargetStie = new ArrayList<NewInfoExtracter> ();
	} 
	
	public void loadAll(){
		List<TargetSite> tsl = this.targetSiteManager.getTargetSiteList();
		for(TargetSite ts : tsl){
			NewInfoExtracter ce = this.targetSiteManager.createExtractor(ts);
			if(ce != null)
				this.lstTargetStie.add(ce);
			else
				log.debug("Invalide site information ..");
		}
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
	
	public int refreshInfos(){
		log.debug("Refresh SITE info ..");
		
		long initTime = Calendar.getInstance().getTimeInMillis();
		
		for(NewInfoExtracter nie : this.lstTargetStie){
			String strFileName = "sampleTest.xml";
			try {
				new RssXmlBuilder(nie.getTargetSite(), nie.getNewInfo(), new File(strFileName)).build();
			} catch (CommonException e) {
				e.printStackTrace();
			}
		}
		
		initTime = Calendar.getInstance().getTimeInMillis() - initTime;
		return (int)(initTime / (60 * 1000));
	}
	
	
	public static void main(String ... v){
		Calendar.getInstance().getTimeInMillis();
	}

	
}
