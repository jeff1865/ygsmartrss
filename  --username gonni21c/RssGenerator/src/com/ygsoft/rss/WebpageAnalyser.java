package com.ygsoft.rss;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;

import me.yglib.htmlparser.TokenTag;
import me.yglib.htmlparser.datasource.PageSource;
import me.yglib.htmlparser.datasource.impl.IntResManager;
import me.yglib.htmlparser.ex.node.ExHtmlNode;
import me.yglib.htmlparser.parser.Node;
import me.yglib.htmlparser.parser.impl.HtmlDomBuilder;

import com.ygsoft.rss.data.NewInfo;
import com.ygsoft.rss.filter.DefaultContentsFilter;
import com.ygsoft.rss.filter.FilterException;
import com.ygsoft.rss.filter.IHtmlContentsFilter;

/**
 * analysis process takes long time, this class should be used in thread
 * @author Gonni
 *
 */
public class WebpageAnalyser {
	
	private Logger log = Logger.getLogger(WebpageAnalyser.class);
	
	// Date Regular Expression
	private static final String DateRE = "(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2})";
	
	private static int DefualtTimeOut = 3000;
	private NewInfo newInfo = null;
	private ExHtmlNode rootNode = null;
	private IHtmlContentsFilter contFilter = null;
	
	public WebpageAnalyser(NewInfo newInfo){
		this.newInfo = newInfo;
		this.contFilter = new DefaultContentsFilter();
	}
	
	public void setContFilter(IHtmlContentsFilter filter){
		this.contFilter = filter;
	}
	
	private void loadData() throws CommonException {
		String targetUrl = this.newInfo.getLink();
		PageSource bufPs = null;
		try {
			bufPs = IntResManager.loadStringBufferPage(new URL(targetUrl).toURI(), 3000);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommonException("URL has some problems :" + e.getMessage());
		}
		
		HtmlDomBuilder domBuilder = new HtmlDomBuilder(bufPs);
		List<Node> rootNode = domBuilder.build();
		
		for(Node node : rootNode){
			if(node.getToken() instanceof TokenTag){
				TokenTag tTag = (TokenTag)node.getToken();
				if(tTag.getTagName().equalsIgnoreCase("html")){
					this.rootNode = new ExHtmlNode(node);
					break;
				}
			}
		}
	}
	
	//TODO need to change for TEST
	public void analyse() throws CommonException {
		this.log.debug("load data :" + this.newInfo.getLink());
		this.loadData();
		
		try {
			System.out.println("====> WebPAGEa :" + this.rootNode);
			//this.contFilter.analyse(this.rootNode, this.newInfo.getLink());
			this.contFilter.analyse(this.rootNode, null);	// null should be NULL
		} catch (FilterException e) {
			e.printStackTrace();
			throw new CommonException("Cannot analyze page..");
		}
	}
	
	public String getTitle(){
		return this.rootNode.getElementValue("title");
	}
	
	public String getContents(){
		return this.contFilter.getContents();
	}
	
	public String getLink(){
		return this.newInfo.getLink();
	}
	
	//TODO impl
	public String getDate(){
		//return this.contFilter.getDate();
		return "Unknown";
	}
	
	public String getSummarizedContents(){
		return this.contFilter.getSummarizedContetns();
	}
	
	//TODO impl
	public String getAuthor(){
		//return this.contFilter.getAuthor();
		return "Unknown";
	}
	
	public String getCategory(){
		return this.contFilter.getCategory();
	}
}
