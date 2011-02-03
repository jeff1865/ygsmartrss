package com.ygsoft.smartrss.ui;

import org.apache.log4j.Logger;
import com.ygsoft.rss.ISmartRssService;



public class ServiceController {
	
	private Logger log = Logger.getLogger(ServiceController.class);
	private ISmartRssService iSmartRssService = null;
	
	public void setISmartRssService(ISmartRssService iSmartRssService){
		this.iSmartRssService = iSmartRssService;
		log.info(">>> Engine successfully Injected ..");
	}
	
	public ISmartRssService getSmartRssService(){
		return this.iSmartRssService;
	}
	
}
