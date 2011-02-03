package com.ygsoft.smartrss.ui;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ygsoft.rss.ISmartRssService;


@Controller
public class MainController {
	
	Logger log = Logger.getLogger(MainController.class); 
	
	private ISmartRssService iSmartRssService = null;
	
	public void setISmartRssService(ISmartRssService iSmartRssService){
		this.iSmartRssService = iSmartRssService;
	}
	
	@RequestMapping("/main.xhtm")
	public String mainPage(){
		log.info("===========> This is LOG message from main controller.." + this.iSmartRssService);
		return "/WEB-INF/jsp/main.jsp";
	}
	
}