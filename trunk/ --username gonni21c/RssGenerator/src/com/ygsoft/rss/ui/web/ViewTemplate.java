package com.ygsoft.rss.ui.web;

public class ViewTemplate {
	
	private String title = "NoTitle";
	private String mainSource = null;
	
	public ViewTemplate(){
		;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setMainSource(String source){
		this.mainSource = source;
	}
	
	public String getSourceText(){
		String strRet = "<html><head><title>" + this.title + "</title>" +
				"</head><body>" +
				"<table boder=1 width=600>" +
				"<tr>" +
				"<td bgcolor=grey>Smart RSS</td>" +
				"</tr>" +
				"<tr>" +
				"<td>" +
				"" + this.mainSource + "" +
				"</td></tr></table>" + 
				"</body></html>";
		
		return strRet;
	}
}
