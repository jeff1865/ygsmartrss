package com.ygsoft.rss.data;

public class NewInfo {
	
	String siteId;
	String anchorText;
	String img;
	String link;
	
	public NewInfo(){
		this.anchorText = "null";
		this.img = "null";
	}
	
	public String getAnchorText() {
		return anchorText;
	}
	public void setAnchorText(String anchorText) {
		this.anchorText = anchorText;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String toString(){
		return "[NewInfo]" + this.anchorText + "---" + this.link;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
}
