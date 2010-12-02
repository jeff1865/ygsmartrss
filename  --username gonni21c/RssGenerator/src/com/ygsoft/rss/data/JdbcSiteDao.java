package com.ygsoft.rss.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;


public class JdbcSiteDao implements ISiteDao {

	private LowDataAccess lowDataAccess = null;
	
	public JdbcSiteDao (LowDataAccess lowDataAccess){
		this.lowDataAccess = lowDataAccess;
	}
		
	public void excutedChains(AbstractChainDbAction<?> chainRoot){
		this.lowDataAccess.excuteSqlActions(chainRoot);
	}
	
	@Override
	public void addMonitorSite(final TargetSite targetSite) {
		this.lowDataAccess.excuteSqlActions(new AbstractChainDbAction<Object>(){

			@Override
			public void action(SqlSession session) {
				session.insert("com.ygsoft.rss.data.SiteRssMapper.addTargetSite", targetSite);
			}

			@Override
			public Object getResult() {
				return null;	// void function : no return value
			}
			
		});
	}
		
	
	@Override
	public void createSiteDataTable(final String tableName) {
		this.lowDataAccess.excuteSqlActions(new AbstractChainDbAction<Object>(){

			@Override
			public void action(SqlSession session) {
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("tableName", tableName);
				session.update("com.ygsoft.rss.data.SiteRssMapper.createDataTable", parameters);
			}

			@Override
			public Object getResult() {
				return null;	// void function : no return value
			}
			
		});
	}

	@Override
	public List<NewInfo> checkUrls(final List<NewInfo> newInfos) {
		AbstractChainDbAction<List<NewInfo>> chkUrlAction = new AbstractChainDbAction<List<NewInfo>>(){
			ArrayList<NewInfo> alNewInfo = new ArrayList<NewInfo>();
			@Override
			public void action(final SqlSession session) {
				int retInt = 0;
				for(NewInfo newInfo : newInfos)	{
					
					retInt = session.insert("com.ygsoft.rss.data.SiteRssMapper.checkInfo", newInfo);
					if(retInt == 1){
						alNewInfo.add(newInfo);
					}
				}
			}

			@Override
			public List<NewInfo> getResult() {
				return alNewInfo;
			}
	
		};
		this.lowDataAccess.excuteSqlAction(chkUrlAction);
		
		return chkUrlAction.getResult();
	}

	@Override
	public int checkUrl(NewInfo newInfo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TargetSite> getRegsiteList() {
		AbstractChainDbAction<List<TargetSite>> getTargetListAction = 
			new AbstractChainDbAction<List<TargetSite>>(){
			List<TargetSite> alNewInfo = null;
			@Override
			public void action(final SqlSession session) {
				alNewInfo = session.selectList("com.ygsoft.rss.data.SiteRssMapper.getRegSiteList");
			}

			@Override
			public List<TargetSite> getResult() {
				return alNewInfo;
			}
	
		};
		this.lowDataAccess.excuteSqlAction(getTargetListAction);
		
		return getTargetListAction.getResult();
	}
	
	@Override
	public TargetSite getTargetSite(final int siteId){
		//retTargetSite = (TargetSite)session.selectOne("com.ygsoft.rss.data.SiteRssMapper.getRegSite",	siteId);
		AbstractChainDbAction<TargetSite> getTargetAction =	new AbstractChainDbAction<TargetSite>(){
			TargetSite targetSite = null;
			@Override
			public void action(final SqlSession session) {
				targetSite = (TargetSite)session.selectOne("com.ygsoft.rss.data.SiteRssMapper.getRegSite",	siteId);
			}

			@Override
			public TargetSite getResult() {
				return targetSite;
			}
	
		};
		this.lowDataAccess.excuteSqlAction(getTargetAction);
		
		return getTargetAction.getResult();
	}
	
	public TargetSite getTargetSite(final String url){
		//retTargetSite = (TargetSite)session.selectOne("com.ygsoft.rss.data.SiteRssMapper.getRegSite",	siteId);
		AbstractChainDbAction<TargetSite> getTargetAction =	new AbstractChainDbAction<TargetSite>(){
			TargetSite targetSite = null;
			@Override
			public void action(final SqlSession session) {
				targetSite = (TargetSite)session
					.selectOne("com.ygsoft.rss.data.SiteRssMapper.getRegSiteFromUrl", url);
			}

			@Override
			public TargetSite getResult() {
				return targetSite;
			}
	
		};
		this.lowDataAccess.excuteSqlAction(getTargetAction);
		
		return getTargetAction.getResult();
	}
	
	public static void main(String ... v){
		LowDataAccess lda = new LowDataAccess(BindHelper.getSqlSessionFactory());
		JdbcSiteDao jsd = new JdbcSiteDao(lda);
		
		TargetSite targetSite = jsd.getTargetSite("http://www.naver000004.com");
		System.out.println(">" + targetSite);
		
//		TargetSite ts = new TargetSite();
//		ts.setCheckIntervalMin(50);
//		ts.setName("a");
//		ts.setRegUser("b");
//		ts.setTargetUrl("http://www66.naver.com/");
		
//		AbstractChainDbAction<?> chainRoot = jsd.addMonitorSite(ts);
//		//chainRoot.addAction(jsd.addMonitorSite(ts));
//		chainRoot.addAction(jsd.createSiteDataTable("test_abc9")).addAction(jsd.addMonitorSite(ts));
//		jsd.excutedChains(chainRoot);
		
//		List<IDbAction<?>> dbActionList = new ArrayList<IDbAction<?>>();
//		dbActionList.add(jsd.addMonitorSite(ts));
//		dbActionList.add(jsd.addMonitorSite(ts));
//		//dbActionList.add(jsd.createSiteDataTable("test_abc9"));
//		lda.excuteSqlActions(dbActionList);
	}
}
