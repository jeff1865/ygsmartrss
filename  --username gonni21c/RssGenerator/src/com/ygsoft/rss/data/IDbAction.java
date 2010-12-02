package com.ygsoft.rss.data;

import org.apache.ibatis.session.SqlSession;

public interface IDbAction<T> {
	public void action(SqlSession session);
	public T getResult();
}
