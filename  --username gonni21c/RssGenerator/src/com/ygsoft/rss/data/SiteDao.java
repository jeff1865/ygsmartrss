package com.ygsoft.rss.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.log4j.Logger;

public class SiteDao {//implements ISiteDao {

	private SqlSessionFactory sqlSessionFactory = null;
	private Logger log = Logger.getLogger(SiteDao.class);
	
	public SiteDao(SqlSessionFactory sqlSessionFactory){
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public int checkUrl(NewInfo newInfo){
		log.debug("Check newInfo :" + newInfo);
		int retInt = -1;
		SqlSession session = this.sqlSessionFactory.openSession();
		
		try	{
			retInt = session.insert("com.ygsoft.rss.data.SiteRssMapper.checkInfo", newInfo);
			session.commit();
			log.debug("Check completed result code:" + retInt);
		} catch(Exception e){
			e.printStackTrace();
			throw new DataLayerException("Cannot check the new information because of DB problem..");
		} finally {
			session.close();
		}
		return retInt;
	}
	
	public List<NewInfo> checkUrls(List<NewInfo> newInfos){
		ArrayList<NewInfo> alNewInfo = new ArrayList<NewInfo>();
		int retInt = 0;
		SqlSession session = this.sqlSessionFactory.openSession();
		
		try	{
			for(NewInfo newInfo : newInfos)
			{
				retInt = session.insert("com.ygsoft.rss.data.SiteRssMapper.checkInfo", newInfo);
				if(retInt == 1){
					alNewInfo.add(newInfo);
				}
			}
			session.commit();
			
		} catch(Exception e){
			throw new DataLayerException("Cannot check the new information because of DB problem..");
		} finally {
			session.close();
		}
		return alNewInfo;
	}
	
	
	public void createSiteDataTable(String tableName){
		SqlSession session = this.sqlSessionFactory.openSession();
		
		try	{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("tableName", tableName);
			session.update("com.ygsoft.rss.data.SiteRssMapper.createDataTable", parameters);
			session.commit();
			log.debug("Table creation successfully completed.." + tableName);
		} catch(Exception e){
			e.printStackTrace();
			throw new DataLayerException("Cannot check the new information because of DB problem..");
		} finally {
			session.close();
		}
	}
	
	public void addMonitorSite(TargetSite targetSite){
		log.debug("Add target site :" + targetSite);
		this.defaultInsertAction("com.ygsoft.rss.data.SiteRssMapper.addTargetSite", 
				targetSite, "Cannot add the target site..");
	}
	
	public List<TargetSite> getRegsiteList(){
		List<TargetSite> retTargetList = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		
		try	{
			retTargetList = session.selectList("com.ygsoft.rss.data.SiteRssMapper.getRegSiteList");
			session.commit();
		} catch(Exception e){
			e.printStackTrace();
			throw new DataLayerException("Cannot get the list of target site from DB..");
		} finally {
			session.close();
		}
		
		return retTargetList;
	}
	
	public TargetSite getTargetSite(int siteId){
		TargetSite retTargetSite = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		
		try	{
			retTargetSite = (TargetSite)session.selectOne("com.ygsoft.rss.data.SiteRssMapper.getRegSite", 
					siteId);
			
			session.commit();
		} catch(Exception e){
			e.printStackTrace();
			throw new DataLayerException("Cannot get the target site from DB..");
		} finally {
			session.close();
		}
		
		return retTargetSite;
	}
	
	private void defaultInsertAction(String func, Object arg, String exceptionMsg){
		SqlSession session = this.sqlSessionFactory.openSession();
						
		try	{
			session.getConnection().setAutoCommit(false);
			session.insert(func, arg);
			
			//
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("tableName", "test_abc9");
			session.update("com.ygsoft.rss.data.SiteRssMapper.createDataTable", parameters);
			//
			session.commit();
		} catch(Exception e){
			System.out.println("--------------------------");
			e.printStackTrace();	// need to remove
			session.rollback();
			throw new DataLayerException(exceptionMsg + "\r\n" + e.getMessage());
		} finally {
			session.close();
		}
	}
	
	public static void main(String ... v){
		SiteDao siteDao = new SiteDao(BindHelper.getSqlSessionFactory());
		//TargetSite ts = siteDao.getTargetSite(1);
		//System.out.println("Target Site :" + ts);
		
		//siteDao.createSiteDataTable("test_abc7");
		{
//		NewInfo newInfo = new NewInfo();
//		newInfo.setImg("img");
//		newInfo.setAnchorText("한글이 들어갈까?");
//		newInfo.setLink("www.test.com");
//		newInfo.setSiteId("5");
//		
//		siteDao.checkUrl(newInfo);
		}
		
		{
		TargetSite ts = new TargetSite();
		ts.setCheckIntervalMin(40);
		ts.setName("한글이름");
		ts.setRegUser("한글유저");
		ts.setTargetUrl("http://www.naver000004.com/");
		
		siteDao.addMonitorSite(ts);
		}
		
		{
//		List<TargetSite> regsiteList = siteDao.getRegsiteList();
//		for(TargetSite targetSite : regsiteList){
//			System.out.println(targetSite);
//			System.out.println("-------------------------------------");
//		}
		}
	}

	
	public TargetSite getTargetSite(String url) {
		// TODO Auto-generated method stub
		return null;
	}
}
