package com.ygsoft.rss;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.*;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.ygsoft.rss.data.NewInfo;
import com.ygsoft.rss.data.TargetSite;

public class RssXmlBuilder {
	
	private Logger log = Logger.getLogger(RssXmlBuilder.class);
	
	private Element rootElem = null;
	private TargetSite targetSite = null;
	private List<NewInfo> newInfoLst = null;
	private File targetFile = null;
	
	public RssXmlBuilder(TargetSite targetSite, List<NewInfo> newInfoLst, File targetFile){
		this.targetSite = targetSite;
		this.newInfoLst = newInfoLst;
		this.targetFile = targetFile;
	}
	
	public static void main(String ... v){
		try {
			new RssXmlBuilder(null, null, null).build();
		} catch (CommonException e) {
			e.printStackTrace();
		}
	}
	
	public void build() throws CommonException{
		this.rootElem = new Element("rss");
		List<Attribute> newAttributes = new ArrayList<Attribute>();
		newAttributes.add(new Attribute("version", "2.0"));
		//newAttributes.add(new Attribute("dc", "http://purl.org/dc/elements/1.1/").setNamespace(Namespace.getNamespace("xxx", "xxx")));
		newAttributes.add(new Attribute("dc", "http://purl.org/dc/elements/1.1/", Namespace.XML_NAMESPACE));
		this.rootElem.setAttributes(newAttributes);
		
		Element channel = this.getChannel();
		this.rootElem.addContent(channel);
		
		if(this.newInfoLst != null)
			for(NewInfo newInfo : newInfoLst){
				this.linkItems(newInfo, channel);
			}
		
		try {
		    XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		    outputter.output(new Document(this.rootElem), System.out);
		} catch (java.io.IOException e) {
		    e.printStackTrace();
		}
	}
	
	private Element getChannel(){
		Element channel = new Element("channel");
		channel.addContent(this.createSimpleElement("title", this.targetSite.getName()));
		channel.addContent(this.createSimpleElement("description", "RSS from " + this.targetSite.getName()));
		channel.addContent(this.createSimpleElement("lastBuildDate", Calendar.getInstance().getTime().toString()));
		channel.addContent(this.createSimpleElement("copyright", this.targetSite.getTargetUrl()));
		channel.addContent(this.createSimpleElement("webMaster", "Unknown"));	//TODO
		channel.addContent(this.createSimpleElement("language", "ko"));			//TODO
		
		Element image = new Element("image");
		{
			image.addContent(this.createSimpleElement("title", this.targetSite.getName()));
			image.addContent(this.createSimpleElement("url", null));
			image.addContent(this.createSimpleElement("link", null));
			image.addContent(this.createSimpleElement("description", null));
			image.addContent(new Element("description").addContent(".."));
		}
		channel.addContent(image);
		
		return channel;
	}
	
	private Element createSimpleElement(String name, String value){
		Element elem = new Element(name);
		if(value == null){
			elem.addContent("N/A");
		} else {
			elem.addContent(value);
		}
		return elem;
	}
	
	public void linkItems(NewInfo newInfo, Element channel){
		WebpageAnalyser wa = new WebpageAnalyser(newInfo);
		try {
			wa.analyse();
		} catch (CommonException e) {
			log.error("Cannot analyse site (" + e.getMessage() + "):" + wa.getLink());
			return ;
		}
		
		Element item = new Element("item");
		{
			item.addContent(new Element("title").addContent(new CDATA(wa.getTitle())));
			item.addContent(new Element("link").addContent(wa.getLink()));
			item.addContent(new Element("description").addContent(new CDATA(wa.getContents())));
			item.addContent(new Element("dc:date").addContent(wa.getDate()));
			item.addContent(new Element("author").addContent(new CDATA(wa.getAuthor())));
			item.addContent(new Element("category").addContent(new CDATA(wa.getCategory())));
		}
		channel.addContent(item);
	}
}
