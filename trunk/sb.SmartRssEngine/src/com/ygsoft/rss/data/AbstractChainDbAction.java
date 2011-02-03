package com.ygsoft.rss.data;

import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.apache.ibatis.session.SqlSession;

public abstract class AbstractChainDbAction<T> implements IDbAction<T>{
	
	private AbstractChainDbAction<?> nextAction = null;
	
	public AbstractChainDbAction<?> addAction(AbstractChainDbAction<?> nextAction){
		this.nextAction = nextAction;
		return this.nextAction;
	}
	
	public boolean excuteNodeAction(SqlSession session){
		try	{
			System.out.println("===========>" + session);
			this.action(session);
			if(this.nextAction != null){
				if(!this.nextAction.excuteNodeAction(session))
					return false;
			}
			return true;
		} catch(RuntimeSqlException re) {
			re.printStackTrace();
			return false;
		}
	}
	
	
	
}
