package com.ygsoft.smartrss.ui;
import org.apache.log4j.*;
import chapter11.ITempService;

public class HelloGetter {
	
	Logger log = Logger.getLogger(HelloGetter.class);
	ITempService iTempService = null;
	
	public void setITempService(ITempService iTempService){
		log.info("XXX======SET message OBJECT======>" + iTempService.getMessage());
		this.iTempService = iTempService;
	}
	
	public void start(){
		log.info("XXX======serivce message======>" + iTempService.getMessage());
	}
}