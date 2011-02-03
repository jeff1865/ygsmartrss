package com.ygsoft.rss.ui.web;

import org.apache.log4j.Logger;
import org.ygsoft.webserver.ProtocolHelper;
import org.ygsoft.webserver.ResourceDescription;
import org.ygsoft.webserver.XRequest;
import org.ygsoft.webserver.XResponse;
import org.ygsoft.webserver.service.Gdplet;

import com.ygsoft.rss.CommonException;
import com.ygsoft.rss.SmartRssService;
import com.ygsoft.rss.TargetSiteManager;

public class GdpRegisterSite extends Gdplet{
	
	private Logger log = Logger.getLogger(GdpRegisterSite.class);
	
	private SmartRssService smSvc = null;
	private ViewTemplate siteTemplate = null;
	private TargetSiteManager tsm = null;
	
	public GdpRegisterSite (SmartRssService smartRssService, ViewTemplate siteTemplate, TargetSiteManager tsm){
		this.smSvc = smartRssService;
		this.siteTemplate = siteTemplate;
		this.tsm = tsm;
	}
	
	public void setTargetSiteManager(TargetSiteManager tsm){
		this.tsm = tsm;
	}
	
	@Override
	public String getID() {
		return "registerSite";
	}
	
	@Override
	protected void service(XRequest req, XResponse res) {
		try
		{
			String body = "Service is not ready..";
						
			this.siteTemplate.initData();
			this.siteTemplate.setTitle("Smart RSS Main :: Register Your Site");
			
			//String chData = this.siteTemplate.getCachedData("RegisterSite");
			//TODO value mapping
			//this.siteTemplate.setPartialTemplate("Main", chData);
			//body = this.siteTemplate.getFullText();
			
			if(this.isRegRequest(req)) {
				log.info("request to register site..");
				body = this.getRegisterBody(req);
			} else { 
				body = this.getGeneralBody();
			}
			
			res.setAttribute(ProtocolHelper.ContentType, ProtocolHelper.ValueContentTypeText);
			res.setAttribute(ProtocolHelper.ContentLength, "" + body.getBytes("utf-8").length);
			res.writeStringToHeader();
			
			res.writeStringToBody(body);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getGeneralBody(){
		String body = "Test";
		
		this.siteTemplate.initData();
		this.siteTemplate.setTitle("Smart RSS Main :: Register Your Site");
		
		String chData = this.siteTemplate.getCachedData("RegisterSite");
		//TODO value mapping
		this.siteTemplate.setPartialTemplate("Main", chData);
		body = this.siteTemplate.getFullText();
		
		return body;
	}
	
	private String getRegisterBody(XRequest req){
		String retStr = "Service is not active..";
		
		this.siteTemplate.initData();
		this.siteTemplate.setTitle("Smart RSS Main :: Registered Result of Your Site");
		
		String chData = this.siteTemplate.getCachedData("RegisterResult");
		
		if(this.tsm != null){
			ResourceDescription rd = req.getParsedMainHeader();
			
			String siteName = rd.getParam("siteName");
			String url = rd.getParam("url");
			String ci = rd.getParam("checkInterval");
			
			if(siteName != null && url != null && ci != null){
				try {
					int cid = this.tsm.createNewTargetSite(url, Integer.parseInt(ci), siteName, "guest");
					retStr = "Your site is successfully registered, asigned ID :" + cid;
					this.log.info("Your site is successfully registered, asigned ID :" + cid);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					retStr = e.getLocalizedMessage();
				} catch (CommonException e) {
					e.printStackTrace();
					retStr = e.getLocalizedMessage();
				}
			}
		}
		
		chData = chData.replaceAll("\\$\\{regResult\\}", retStr);
		this.siteTemplate.setPartialTemplate("Main", chData);
		chData = this.siteTemplate.getFullText();
		
		return chData;
	}
	
	private boolean isRegRequest(XRequest req){
		System.out.println("===========> CHECK");
		String optionValue = req.getParsedMainHeader().getParam("siteName");
		if(optionValue != null){
			System.out.println("--------> ParamValue :" + optionValue);
			return true;
		}
		return false;
	}

}
