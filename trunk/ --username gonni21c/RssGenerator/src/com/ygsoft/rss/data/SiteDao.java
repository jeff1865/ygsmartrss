package com.ygsoft.rss.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import com.ygsoft.rss.NewInfo;

public class SiteDao implements ISiteDao {

	private SqlSessionFactory sqlSessionFactory = null;
	private Logger log = Logger.getLogger(SiteDao.class);
	
	public SiteDao(SqlSessionFactory sqlSessionFactory){
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public int checkUrl(NewInfo newInfo){
		int retInt = -1;
		SqlSession session = this.sqlSessionFactory.openSession();
		
		try	{
			retInt = session.insert("com.ygsoft.rss.data.SiteRssMapper.checkInfo", newInfo);
			session.commit();
		} catch(Exception e){
			throw new DataLayerException("Cannot check the new information because of DB problem..");
		} finally {
			session.close();
		}
		return retInt;
	}
	
	public int checkUrls(List<NewInfo> newInfos){
		int retInt = 0;
		SqlSession session = this.sqlSessionFactory.openSession();
		
		try	{
			for(NewInfo newInfo : newInfos)
			{
				retInt =+ session.insert("com.ygsoft.rss.data.SiteRssMapper.checkInfo", newInfo);
			}
			session.commit();
		} catch(Exception e){
			throw new DataLayerException("Cannot check the new information because of DB problem..");
		} finally {
			session.close();
		}
		return retInt;
	}
	
	
	public void createSiteDataTable(String tableName){
		SqlSession session = this.sqlSessionFactory.openSession();
		Connection conn = session.getConnection();
		try {
			Statement stmt = conn.createStatement();
			stmt.execute("CREATE TABLE " + tableName + " (" +
					"anc_url VARCHAR(767) NOT NULL," +
					"anc_text VARCHAR(256) NOT NULL," +
					"anc_img VARCHAR(256) NOT NULL," +
					"dup_cnt INTEGER UNSIGNED NOT NULL," +
					"regDate TIMESTAMP default 0," +
					"latestDate TIMESTAMP default current_timestamp on update current_timestamp," +
					"PRIMARY KEY (anc_url)" +
					");");
			stmt.close();
			conn.commit();
			//conn.close();
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} finally {
			session.close();
		}
	}
	
	public static void main(String ... v){
		SiteDao siteDao = new SiteDao(BindHelper.getSqlSessionFactory());
		siteDao.createSiteDataTable("test_abc1");
	}
}
