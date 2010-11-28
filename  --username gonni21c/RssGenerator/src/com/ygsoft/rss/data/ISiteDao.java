package com.ygsoft.rss.data;

import java.util.List;
import com.ygsoft.rss.NewInfo;

public interface ISiteDao {
	public void createSiteDataTable(String tableName);
	public int checkUrls(List<NewInfo> newInfos);
	public int checkUrl(NewInfo newInfo);
}
