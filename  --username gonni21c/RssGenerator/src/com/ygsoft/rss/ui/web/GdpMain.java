package com.ygsoft.rss.ui.web;

import java.io.IOException;
import java.util.List;

import org.ygsoft.webserver.ProtocolHelper;
import org.ygsoft.webserver.XRequest;
import org.ygsoft.webserver.XResponse;
import org.ygsoft.webserver.service.Gdplet;

import com.ygsoft.rss.NewInfoExtractWorker;
import com.ygsoft.rss.SmartRssService;
import com.ygsoft.rss.data.TargetSite;

public class GdpMain extends Gdplet {
	
	private SmartRssService smSvc = null;
	private ViewTemplate siteTemplate = null;
	
	public GdpMain (SmartRssService smartRssService, ViewTemplate siteTemplate){
		this.smSvc = smartRssService;
		this.siteTemplate = siteTemplate;
	}
	
	@Override
	public String getID() {
		return "main";
	}
	
	private String getSiteListString() {
		String strRet = "";
		if(this.smSvc != null){
			List<NewInfoExtractWorker> msList = this.smSvc.getMonitorSiteList();
			for(NewInfoExtractWorker nieWorker : msList){
				TargetSite ts = nieWorker.getTargetSite();
				strRet += "<tr>" +
						"<td>"+ts.getSiteId()+"</td>" +
						"<td>"+ts.getName()+"</td>" +
						"<td>"+ts.getLatestDate()+"</td>" +
						"<td>"+ts.getCheckIntervalMin()+"</td>" +
						"<td>"+ts.getCheckStatus()+"</td>" +
						"<td>N/A</td>" +
						"<td><img src=\"img/rss.gif\" /></td>" +
						"<td>N/A</td>" +
						"</tr>" +
						"<tr>" +
						"<td>URL</td><td colspan=\"7\">" + ts.getTargetUrl() +
						"</td>" +
						"</tr>";
			}
		}
		return strRet;
	}
	
	// ����ó���� �ܺο��� �� �� �ֵ��� ��������!
	
	@Override
	protected void service(XRequest req, XResponse res) {
		try {
			
			
			if(this.siteTemplate != null){
				this.siteTemplate.initData();
				this.siteTemplate.setTitle("Smart RSS Main");
				//this.siteTemplate.setPartialTemplate("Main", "Hi, Template Service~");
				
				String chData = this.siteTemplate.getCachedData("Main");
				String list = this.getSiteListString();
				if(list.length() > 0) {
					chData = chData.replaceAll("\\$\\{siteList\\}", list);
				} else {
					chData = chData.replaceAll("\\$\\{siteList\\}", 
						"<tr><td colspan=\"8\" align=\"center\">Empty</td></tr>");
				}
				this.siteTemplate.setPartialTemplate("Main", chData);
				
				String body = this.siteTemplate.getFullText();
				//System.out.println(body);
				
				
				res.setAttribute(ProtocolHelper.ContentType, ProtocolHelper.ValueContentTypeText);
				res.setAttribute(ProtocolHelper.ContentLength, "" + body.getBytes("utf-8").length);
				res.writeStringToHeader();
				
				res.writeStringToBody(body);
			} else {
				res.writeStringToBody("Error~");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
