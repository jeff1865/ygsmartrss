package smartrssengine;

import java.util.Dictionary;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.ygsoft.rss.ISmartRssService;
import com.ygsoft.rss.SmartRssService;
import com.ygsoft.rss.TargetSiteManager;
import com.ygsoft.rss.data.BindHelper;
import com.ygsoft.rss.data.ISiteDao;
import com.ygsoft.rss.data.JdbcSiteDao;
import com.ygsoft.rss.data.LowDataAccess;
import com.ygsoft.rss.data.TargetSite;

import org.apache.log4j.Logger;

public class Activator implements BundleActivator {
	
	Logger log = Logger.getLogger(Activator.class);
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("Smart Rss Service Activate !!");
		
		this.activeSmartRss(context);
	}
	
	private void activeSmartRss(BundleContext bc){
		System.out.println("Active Rss Service ..");
		LowDataAccess lda = new LowDataAccess(BindHelper.getSqlSessionFactory());
		ISiteDao siteDao = new JdbcSiteDao(lda);
		
		TargetSiteManager tsm = new TargetSiteManager(siteDao);
		List<TargetSite> targetSiteList = tsm.getTargetSiteList();
		
		System.out.println("==================[ Site List ]====================");
		for(TargetSite targetSite : targetSiteList){
			System.out.println("----------------------------");
			System.out.println(targetSite);
		}
		
		System.out.println("==================[ Get New Info ]====================");
		SmartRssService srSvc = new SmartRssService(tsm);
		srSvc.loadAll(true);
//		srSvc.refreshInfo(66);	// CLIEN ID : 66
		
		Dictionary dic = null;
		//bc.registerService(SmartRssService.class.getName(), srSvc, dic);
		bc.registerService(ISmartRssService.class.getName(), srSvc, dic);
		log.info("[sRSS] SmartRSS engine service activated on the OSGi..");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Goodbye World!!");
	}

}
