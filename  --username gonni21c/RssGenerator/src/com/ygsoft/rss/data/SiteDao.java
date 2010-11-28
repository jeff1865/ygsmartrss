package com.ygsoft.rss.data;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SiteDao {

	SqlSessionFactory sqlSessionFactory = null;
	
	public SiteDao(SqlSessionFactory sqlSessionFactory){
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public boolean existUrl(String url){
		return false;
	}
	
	public void updateCount(String url){
		return ;
	}
	
	public static void main(String ... v){
		SiteDao siteDao = new SiteDao(BindHelper.getSqlSessionFactory());
	}
}
