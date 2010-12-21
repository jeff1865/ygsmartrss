package com.ygsoft.rss;

import java.util.*;

import com.ygsoft.rss.data.BindHelper;
import com.ygsoft.rss.data.ISiteDao;
import com.ygsoft.rss.data.JdbcSiteDao;
import com.ygsoft.rss.data.LowDataAccess;
import com.ygsoft.rss.data.TargetSite;

public class TargetSiteManager extends Observable {
	
	private ISiteDao siteDao = null;
	public static String siteNameTable = "site_data_";
	
	public TargetSiteManager(ISiteDao siteDao){
		this.siteDao = siteDao;
	}
	
	public NewInfoExtractWorker createExtractor(TargetSite ts){
		try {
			return new NewInfoExtractWorker(this.siteDao, ts.getSiteId());
		} catch (CommonException e) {
			return null;
		}
	}
	/**
	 * 	
	 * @param url
	 * @param checkInterval
	 * @param siteName
	 * @param regUserName
	 * @return created site ID value
	 * @throws CommonException
	 */
	public int createNewTargetSite(String url, int checkInterval, String siteName, String regUserName)
		throws CommonException {
		// check if URL is duplicated
		TargetSite targetSite = this.siteDao.getTargetSite(url);
		if(targetSite != null)
			throw new CommonException("Requested URL:" + url + " already exists, id :" 
					+ targetSite.getSiteId());
		// add to db
		this.addNewTargetSiteToDB(url, checkInterval, siteName, regUserName);
		
		// get siteID
		TargetSite regSite = this.siteDao.getTargetSite(url);
		System.out.println("Reg ID :" + regSite);
		
		// create datatable
		if(regSite != null)
		{
			this.siteDao.createSiteDataTable(siteNameTable + regSite.getSiteId());
			return regSite.getSiteId();
		}
		
		return -1;
	}
		
	private void addNewTargetSiteToDB(String url, int checkInterval, String siteName, String regUserName){
		TargetSite targetSite = new TargetSite();
		targetSite.setTargetUrl(url);
		targetSite.setCheckIntervalMin(checkInterval);
		targetSite.setName(siteName);
		targetSite.setRegUser(regUserName);
		
		this.siteDao.addMonitorSite(targetSite);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public List<TargetSite> getTargetSiteList(){
		return this.siteDao.getRegsiteList();
	}
	
	public void removeSite(int id){
		this.siteDao.removeMonitorSite(id);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public static void main(String ... v){
		LowDataAccess lda = new LowDataAccess(BindHelper.getSqlSessionFactory());
		JdbcSiteDao jsd = new JdbcSiteDao(lda);
		TargetSiteManager tsm = new TargetSiteManager(jsd);
		try {
			int id = tsm.createNewTargetSite("http://clien.career.co.kr/cs2/bbs/board.php?bo_table=park",
					100, "[Ŭ����] ����ǰ���", "Admin_YG");
			System.out.println("Created ID :" + id);
		} catch (CommonException e) {
			e.printStackTrace();
		}
	}
}
