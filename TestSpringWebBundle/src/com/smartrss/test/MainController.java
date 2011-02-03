package com.smartrss.test;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MainController {
	
	Logger log = Logger.getLogger(MainController.class); 
	
	@RequestMapping("/main.xhtm")
	public String mainPage(){
		log.info("===========> This is LOG message from main controller..");
		return "/WEB-INF/jsp/main.jsp";
	}
	
}
