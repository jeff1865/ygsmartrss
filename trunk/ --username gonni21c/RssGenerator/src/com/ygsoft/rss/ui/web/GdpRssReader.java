package com.ygsoft.rss.ui.web;

import org.apache.log4j.Logger;
import org.ygsoft.webserver.ProtocolHelper;
import org.ygsoft.webserver.ResourceDescription;
import org.ygsoft.webserver.XRequest;
import org.ygsoft.webserver.XResponse;
import org.ygsoft.webserver.service.Gdplet;

import com.ygsoft.rss.SmartRssService;

public class GdpRssReader extends Gdplet {
	
	protected static final String paramNameSiteId = "siteId";
	private Logger log = Logger.getLogger(GdpRssReader.class);
	private SmartRssService srSvc = null;
	
	public GdpRssReader(SmartRssService srSvc){
		this.srSvc = srSvc;
	}
	
	@Override
	public String getID() {
		return "rss";
	}
	
	@Override
	protected void service(XRequest req, XResponse res) {
		String body = "Not Available";
		try{
			ResourceDescription rd = req.getParsedMainHeader();
			String siteId = rd.getParam(paramNameSiteId);
			log.debug("request to get RSS :" + siteId);
			if(siteId != null){
				body = this.srSvc.getLatestRssData(Integer.parseInt(siteId));
			}
			
			res.setAttribute(ProtocolHelper.ContentType, "text/xml");
			res.setAttribute(ProtocolHelper.ContentLength, "" + body.getBytes("utf-8").length);
			res.writeStringToHeader();
			
			res.writeStringToBody(body);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
