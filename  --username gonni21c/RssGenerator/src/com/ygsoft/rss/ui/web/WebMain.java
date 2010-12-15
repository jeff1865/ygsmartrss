package com.ygsoft.rss.ui.web;

import org.ygsoft.webserver.MicroWebServer;
import org.ygsoft.webserver.PLogging;
import org.ygsoft.webserver.ServiceManager;
import org.ygsoft.webserver.ServiceMapper;
import org.ygsoft.webserver.ServiceScheduler;
import org.ygsoft.webserver.service.GdpService;
import org.ygsoft.webserver.service.Gdplet;
import org.ygsoft.webserver.service.StaticResourceService;

public class WebMain {
	
	private static int serverPort = 2012;
	
	public static void main(String ... v){
		System.out.println("Start RssGen WebMain..");
		// V2
		ServiceMapper fCont = new ServiceMapper();
		
		ServiceManager<Gdplet> sMng = new ServiceManager<Gdplet>();
		sMng.addService(new GdpMain(null));
//		sMng.addService(new Hello());
//		sMng.addService(new Exam());
//		sMng.addService(new FileTransExam());
		
		// register & active GDP service
		fCont.addContainer(new GdpService(sMng));
		
		// register & active static resource service
		//fCont.addContainer(new StaticResourceService("HttpRoot/"));
		PLogging.printv(PLogging.DEBUG, fCont.getContainers().size() + " containers registered ..");
		
		MicroWebServer server = new MicroWebServer(serverPort, fCont, ServiceScheduler.getDefaultScheduler());
		server.startServer();
		
		PLogging.printv(PLogging.DEBUG, "YG XServer stopped..");
	}

}
