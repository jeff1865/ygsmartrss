package com.ygsoft.rss.ui.web;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.ygsoft.webserver.ProtocolHelper;
import org.ygsoft.webserver.ResourceDescription;
import org.ygsoft.webserver.XRequest;
import org.ygsoft.webserver.XResponse;
import org.ygsoft.webserver.service.Gdplet;

import com.ygsoft.rss.NewInfoExtractWorker;
import com.ygsoft.rss.NewInfoExtractWorker.EWorkerStatus;
import com.ygsoft.rss.SmartRssService;
import com.ygsoft.rss.data.ESiteStatus;
import com.ygsoft.rss.data.TargetSite;

public class GdpMain extends Gdplet {
	
	Logger log = Logger.getLogger(GdpMain.class);
	
	private SmartRssService smSvc = null;
	private ViewTemplate siteTemplate = null;
	
	private static String removeRequestCommand = "removeSite";
	private static String refreshSiteCommand = "refreshSite";
	
	
	public GdpMain (SmartRssService smartRssService, ViewTemplate siteTemplate){
		this.smSvc = smartRssService;
		this.siteTemplate = siteTemplate;
	}
	
	@Override
	public String getID() {
		return "main";
	}
	
	private String getTimeFormat(Timestamp ts) {
		Calendar cld = Calendar.getInstance();
		cld.setTimeInMillis(ts.getTime());
		
		return cld.get(Calendar.YEAR) + "-" + (cld.get(Calendar.MONTH) + 1) + "-" 
			+ cld.get(Calendar.DAY_OF_MONTH)
			+ " " + cld.get(Calendar.HOUR_OF_DAY) + ":" + cld.get(Calendar.MINUTE);
	}
	
	private String getSiteListString() {
		String strRet = "";
		if(this.smSvc != null){
			List<NewInfoExtractWorker> msList = this.smSvc.getMonitorSiteList();
			String status = null;
			for(NewInfoExtractWorker nieWorker : msList){
				TargetSite ts = nieWorker.getTargetSite();
				
				//ESiteStatus ss = ts.getCheckStatusValue();
				EWorkerStatus ss = nieWorker.getStatus();
				if(ss.ordinal() >= 3) status = "<font color=\"red\">" + ss + "</font>";
				else status = "<font color=\"green\">" + ss + "</font>";
				
				strRet += "<tr valign=\"middle\" class=\"subj2cont\">" +
						"<td valign=\"middle\">"+ts.getSiteId()+"" +
								"<a href=\"main.gdp?" + removeRequestCommand + "=" + ts.getSiteId() + "\">" +
										"<img src=\"img/btn_delete_s.jpg\"/></a></td>" +
						"<td>"+ts.getName()+"<b>[<font color=\"red\">"+nieWorker.getLatestNewInfos().size()
						+"</font>]</b></td>" +
						"<td>"+this.getTimeFormat(ts.getLatestDate())+"</td>" +
						"<td  valign=\"middle\">"+ts.getCheckIntervalMin()+
							"<a href=\"main.gdp?" + refreshSiteCommand + "=" + ts.getSiteId() + "\">" +
									"<img src=\"img/btn_refresh_s.jpg\"/></a>" +
							"<img src=\"img/btn_play.gif\"/>" +
							"<img src=\"img/btn_stop.gif\"/>" +
							"</td>" +
						"<td>" + status + "</td>" +
						"<td>N/A</td>" +
						"<td>" +
						"<a href=\"rss.gdp?"+GdpRssReader.paramNameSiteId + "=" + ts.getSiteId()
						+"\"><img src=\"img/rss.gif\" /></a></td>" +
						"<td>" +
						"<a href=\"http://reader.mac.com/mobile/v1/" +
						"http://gonni.iptime.org:2012/rss.gdp?siteId=" + ts.getSiteId() + "\">" +
						"<img src=\"img/iphoneRss.jpg\" /></a></td>" +
						"</tr>" +
						"<tr class=\"subj2\">" +
						"<td bgcolor=\"white\">URL</td><td align=\"left\" colspan=\"7\">" + ts.getTargetUrl() +
						"</td>" +
						"</tr>";
			}
		}
		return strRet;
	}
	
	private boolean procCommand(XRequest req){
		ResourceDescription rd = req.getParsedMainHeader();
		String param = rd.getParam(GdpMain.removeRequestCommand);
		if(param != null){
			log.debug("Request Remove Site :" + param);
			int id = Integer.parseInt(param);
			this.smSvc.removeSite(id);
		}
		
		param = rd.getParam(GdpMain.refreshSiteCommand);
		if(param != null){
			log.debug("Request Refresh Site :" + param);
			int id = Integer.parseInt(param);
			this.smSvc.refreshInfo(id);
		}
		return false;
	}
	
	
	// 예외처리를 외부에서 할 수 있도록 구조개선!
	
	@Override
	protected void service(XRequest req, XResponse res) {
		try {
			//this.hasRemoveRequest(req);
			this.procCommand(req);
			
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
