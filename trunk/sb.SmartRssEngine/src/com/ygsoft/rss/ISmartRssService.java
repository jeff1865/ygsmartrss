package com.ygsoft.rss;

import java.util.List;

public interface ISmartRssService {
	public List<NewInfoExtractWorker> getMonitorSiteList();
	public TargetSiteManager getTargetSiteManager();
}
