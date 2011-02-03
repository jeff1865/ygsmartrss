package com.ygsoft.rss.data;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

public class LowDataAccess {
	private SqlSessionFactory sqlSessionFactory = null;
	private Logger log = Logger.getLogger(LowDataAccess.class);
	
	
	public LowDataAccess (SqlSessionFactory sqlSessionFactory){
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public void excuteSqlAction(IDbAction<?> dbAction) throws DataLayerException {
		SqlSession session = this.sqlSessionFactory.openSession();
		
		try	{
			dbAction.action(session);
			session.commit();
			log.debug("Transcation Successfully Excuted.. ");
		} catch(Exception e){
			e.printStackTrace();
			session.rollback();	// roll back
			log.error("Cannot excute DB actions..\n" + e.getLocalizedMessage());
			throw new DataLayerException("Cannot excute DB actions..\n" + e.getLocalizedMessage());
		} finally {
			session.close();
		}
	}
	
	public void excuteSqlActions(List<IDbAction<?>> dbActionList) throws DataLayerException {
		log.debug("Transaction Excute actions : " + dbActionList.size());
		
		SqlSession session = this.sqlSessionFactory.openSession();
		
		try	{
			for(IDbAction<?> action : dbActionList){
				if(action != null){
					action.action(session);
				}
			}
			
			session.commit();
			log.debug("Transcation Successfully Excuted.. ");
		} catch(Exception e){
			e.printStackTrace();
			session.rollback();	// roll back
			log.error("Cannot excute DB actions..\n" + e.getLocalizedMessage());
			throw new DataLayerException("Cannot excute DB actions..\n" + e.getLocalizedMessage());
		} finally {
			session.close();
		}
	}
	
	public void excuteSqlActions(AbstractChainDbAction<?> nodeAction) throws DataLayerException {
		log.debug("Transaction Excute node actions : " + nodeAction);
		
		SqlSession session = this.sqlSessionFactory.openSession(false);
		
		try	{
			if(nodeAction.excuteNodeAction(session)){
				System.out.println("======================= NOT !=====================");
				session.commit();
				log.debug("Transcation actions are successfully excuted.. ");
			} else {
				throw new Exception("Transaction Fail..");
			}
		} catch(Exception e){
			e.printStackTrace();
			session.rollback();	// roll back
			log.error("Cannot excute DB actions..\n" + e.getLocalizedMessage());
			throw new DataLayerException("Cannot excute DB actions..\n" + e.getLocalizedMessage());
		} finally {
			session.close();
		}
	}
	
	
}
