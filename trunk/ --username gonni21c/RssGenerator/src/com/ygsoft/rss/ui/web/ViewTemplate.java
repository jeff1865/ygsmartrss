package com.ygsoft.rss.ui.web;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewTemplate {
	
	private final String tempPattern = "\\@\\{[a-zA-Z0-9]*\\}";
	
	private PageCache originalCache = null;
	private String id = null, title = null;
	private String baseTemp = null;
	
	private Hashtable<String, String> rtCache = null;
	
	public ViewTemplate(String id, String baseTemplate, PageCache pCache){
		this.originalCache = pCache;
		this.id = id;
		this.baseTemp = baseTemplate;
	}
	
	public void initData(){
		if(this.rtCache == null) 
			this.rtCache = new Hashtable<String, String>();
		
		Pattern pattern = Pattern.compile(this.tempPattern);
		Matcher matcher = pattern.matcher(this.baseTemp);
		
		while(matcher.find()){
			String group = matcher.group(0);
			group = group.substring(2, group.length() -1);
			
			String templateContext = this.getTemplateContext(group);
			if(templateContext != null)
				this.rtCache.put(group, templateContext);
		}
	}
	
	private String getTemplateContext(String tempName){
		//TODO need to implement biz logic
		String pageTemplate = this.originalCache.getPageTemplate(tempName);
		return pageTemplate;
	}
	
	public void setPartialTemplate(String tempName, String cont){
		this.rtCache.put(tempName, cont);
	}
	
	public void setPartialTemplate(String tempName, Hashtable<String, String> htAttributes){
		return ;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getCachedData(String key){
		return this.originalCache.getPageTemplate(key);
	}
	
	public String getFullText(){
		String result = null;
		result = this.baseTemp.replaceFirst("\\$\\{title\\}", this.title);
		
		Enumeration<String> keys = this.rtCache.keys();
		String key = null;
		while(keys.hasMoreElements()){
			key = keys.nextElement();
			System.out.println("key > " + key);
			result = result.replaceAll("\\@\\{" + key + "\\}", this.rtCache.get(key));
		}
				
		return result;
	}
	
	public static void main(String ... v){
//		Pattern pattern = Pattern.compile("\\@\\{[a-zA-Z0-9]*\\}");
//		Matcher matcher = pattern.matcher("abcde @{header} fjalsdkjfdalks @{main}aaa");
//		while(matcher.find()){
//			System.out.println(matcher.group(0));
//		}
		
		PageCache pc = new PageCache();
		//pc.loadPages();
		//pc.showData();
		
		ViewTemplate vt = new ViewTemplate("main", pc.getPageTemplate("SiteTemplate"), pc);
		vt.initData();
		vt.setTitle("TestTITLE");
		System.out.println(vt.getFullText());
		
	}
	
}
