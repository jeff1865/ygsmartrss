package com.ygsoft.util.web;

import java.util.ArrayList;
import java.util.List;

public class Anchor {
	
	private String url;
	private List<String> texts;
	private String xPath;
	
	public Anchor(String anchorUrl){
		this.url = anchorUrl;
	}
	
	public void addText(String text){
		if(this.texts == null)
			this.texts = new ArrayList<String>();
		this.texts.add(text);
	}
	
	public String getUrl() {
		return url;
	}

	public List<String> getText() {
		return this.texts;
	}
	
	public String toString(){
		String tx = "";
		if(this.texts != null)
			for(String st : this.texts) tx += st + "\n";
		return this.url + "---" + tx;
	}

	public void setxPath(String xPath) {
		this.xPath = xPath;
	}

	public String getxPath() {
		return xPath;
	}
}
