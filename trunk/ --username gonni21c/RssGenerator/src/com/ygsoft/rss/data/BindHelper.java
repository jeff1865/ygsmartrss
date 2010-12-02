package com.ygsoft.rss.data;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class BindHelper {
	
	public static SqlSessionFactory getSqlSessionFactory() {
		SqlSessionFactory sqlMapper = null;
		try {
			String resource = "com/ygsoft/rss/data/mybatis-config.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			sqlMapper = new SqlSessionFactoryBuilder().build(reader);
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sqlMapper;
	}
	
	public static void main(String ... v){
		;
	}
}
