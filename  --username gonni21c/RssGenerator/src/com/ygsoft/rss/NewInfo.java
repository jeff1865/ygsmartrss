package com.ygsoft.rss;

public class NewInfo {
	
	String sideId;
	String anchorText;
	String img;
	String link;
	
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
	public String getSideId() {
		return sideId;
	}
	public void setSideId(String sideId) {
		this.sideId = sideId;
	}
}
