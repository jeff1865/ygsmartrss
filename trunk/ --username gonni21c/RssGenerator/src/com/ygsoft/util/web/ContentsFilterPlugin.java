package com.ygsoft.util.web;

import java.util.List;

public interface ContentsFilterPlugin {
	
	public List<Anchor> getAnchors(String javascriptValue);
	
	/**
	 * Get regular expression value : site name pattern
	 * @return
	 */
	public String getSiteNameReguex();
}
