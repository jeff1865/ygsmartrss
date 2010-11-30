package com.ygsoft.rss.data;

import java.util.List;
import com.ygsoft.rss.NewInfo;
import com.ygsoft.rss.TargetSite;

public interface ISiteDao {
	public void createSiteDataTable(String tableName);
	public List<NewInfo> checkUrls(List<NewInfo> newInfos);
	public int checkUrl(NewInfo newInfo);
	public List<TargetSite> getRegsiteList();
}
