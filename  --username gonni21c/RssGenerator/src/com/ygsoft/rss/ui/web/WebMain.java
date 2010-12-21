package com.ygsoft.rss.ui.web;

import java.util.List;

import org.ygsoft.webserver.MicroWebServer;
import org.ygsoft.webserver.PLogging;
import org.ygsoft.webserver.ServiceManager;
import org.ygsoft.webserver.ServiceMapper;
import org.ygsoft.webserver.ServiceScheduler;
import org.ygsoft.webserver.service.GdpService;
import org.ygsoft.webserver.service.Gdplet;
import org.ygsoft.webserver.service.StaticResourceService;

import com.ygsoft.rss.SmartRssService;
import com.ygsoft.rss.TargetSiteManager;
import com.ygsoft.rss.data.BindHelper;
import com.ygsoft.rss.data.ISiteDao;
import com.ygsoft.rss.data.JdbcSiteDao;
import com.ygsoft.rss.data.LowDataAccess;
import com.ygsoft.rss.data.TargetSite;

public class WebMain {
	
	private static int serverPort = 2012;
	
	public static void main(String ... v){
		System.out.println("Active Rss Service ..");
		LowDataAccess lda = new LowDataAccess(BindHelper.getSqlSessionFactory());
		ISiteDao siteDao = new JdbcSiteDao(lda);
		
		TargetSiteManager tsm = new TargetSiteManager(siteDao);
		List<TargetSite> targetSiteList = tsm.getTargetSiteList();
		
//		System.out.println("==================[ Site List ]====================");
//		for(TargetSite targetSite : targetSiteList){
//			System.out.println("----------------------------");
//			System.out.println(targetSite);
//		}
		
//		System.out.println("==================[ Get New Info ]====================");
		SmartRssService srSvc = new SmartRssService(tsm);
		srSvc.loadAll(true);
//		srSvc.refreshInfo(66);	// CLIEN ID : 66
		
		
		System.out.println("Start Service Server ..");
		// V2
		ServiceMapper fCont = new ServiceMapper();
		
		PageCache pc = new PageCache();
		ViewTemplate vt = new ViewTemplate("BASE", pc.getPageTemplate("SiteTemplate"), pc);
		
		ServiceManager<Gdplet> sMng = new ServiceManager<Gdplet>();
		sMng.addService(new GdpMain(srSvc, vt));
		sMng.addService(new GdpRegisterSite(srSvc, vt, tsm));
		sMng.addService(new GdpRssReader(srSvc));
//		sMng.addService(new Hello());
//		sMng.addService(new Exam());
//		sMng.addService(new FileTransExam());
		
		// register & active GDP service
		fCont.addContainer(new GdpService(sMng));
		
		// register & active static resource service
		fCont.addContainer(new StaticResourceService("webRoot/"));
		PLogging.printv(PLogging.DEBUG, fCont.getContainers().size() + " containers registered ..");
		
		MicroWebServer server = new MicroWebServer(serverPort, fCont, ServiceScheduler.getDefaultScheduler());
		server.startServer();
		
		PLogging.printv(PLogging.DEBUG, "YG XServer stopped..");
	}

}
