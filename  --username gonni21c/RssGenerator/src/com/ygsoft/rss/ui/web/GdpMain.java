package com.ygsoft.rss.ui.web;

import java.io.IOException;

import org.ygsoft.webserver.ProtocolHelper;
import org.ygsoft.webserver.XRequest;
import org.ygsoft.webserver.XResponse;
import org.ygsoft.webserver.service.Gdplet;

import com.ygsoft.rss.SmartRssService;

public class GdpMain extends Gdplet {
	
	private SmartRssService smSvc = null;
	
	public GdpMain (SmartRssService smartRssService){
		this.smSvc = smartRssService;
	}
	
	@Override
	protected void service(XRequest req, XResponse res) {
		try {
			res.setAttribute(ProtocolHelper.ContentType, ProtocolHelper.ValueContentTypeText);
			res.writeStringToHeader();
			
			ViewTemplate vt = new ViewTemplate();
			vt.setTitle("Smart RSS :: Main");
			String bodyText = "This is test Message";
			vt.setMainSource(bodyText);
			
			res.writeStringToBody(vt.getSourceText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
