package com.ygsoft.rss.filter;

import me.yglib.htmlparser.parser.Node;

public interface IHtmlContentsFilter {
	public void analyse(Node rootNode, String url) throws FilterException;
	public String getContents();
	public String getSummarizedContetns();
	public String getCategory();
//	public String getDate();
//	public String getAuthor();
}
