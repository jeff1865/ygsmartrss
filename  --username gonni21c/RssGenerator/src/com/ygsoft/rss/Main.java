package com.ygsoft.rss;

import java.util.List;

import com.ygsoft.rss.data.BindHelper;
import com.ygsoft.rss.data.ISiteDao;
import com.ygsoft.rss.data.JdbcSiteDao;
import com.ygsoft.rss.data.LowDataAccess;
import com.ygsoft.rss.data.NewInfo;
import com.ygsoft.rss.data.TargetSite;

/**
 * Console Main
 * @author gonni21c@gmail.com
 *
 */
public class Main {
	
	public static void main(String ... v){
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
		srSvc.refreshInfo(66);	// CLIEN ID : 66
		
		System.out.println("==================[ Result ]====================");
		System.out.println(srSvc.getLatestRssData(66));
		
//		// New Info Extract Module
//		NewInfoExtractWorker niExt = null;
//		
//		try {
//			niExt = new NewInfoExtractWorker(siteDao, 66);
//		} catch (CommonException e) {
//			e.printStackTrace();
//		}
//		
//		List<NewInfo> newInfos = niExt.getNewInfo();
//		System.out.println("-------------------------------------------");
//		for(NewInfo ni : newInfos){
//			System.out.println(ni);
//		}
//		
//		System.out.println("New Data Count :" + newInfos.size());
	}
	
}
