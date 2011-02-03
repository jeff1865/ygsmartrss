package com.smartrss.test;

import org.apache.log4j.Logger;

public class HelloSpringDM {
	
	Logger log = Logger.getLogger(HelloSpringDM.class);
	
	public void start(){
		log.info("=============> OSGi Bundle Start ..");
	}
	
	public void stop(){
		log.info("=============> OSGi Bundle Stop ..");
	}
}
