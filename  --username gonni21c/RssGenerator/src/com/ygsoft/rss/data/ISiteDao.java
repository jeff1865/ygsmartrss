package com.ygsoft.rss.data;

import java.util.List;

public interface ISiteDao {
	public void addMonitorSite(TargetSite targetSite);
	public void createSiteDataTable(String tableName);
	public List<NewInfo> checkUrls(List<NewInfo> newInfos);
	public int checkUrl(NewInfo newInfo);
	public List<TargetSite> getRegsiteList();
	public TargetSite getTargetSite(int siteId);
	public TargetSite getTargetSite(String url);
}
