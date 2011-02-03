package org.ygsoft.client.command;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;
import com.ygsoft.rss.*;
import com.ygsoft.rss.data.TargetSite;

public class CommandRssEngine implements CommandProvider {
	
	Logger log = Logger.getLogger(CommandRssEngine.class);
	
	private ISmartRssService iSmartRssService = null;
	
	public void setISmartRssService(ISmartRssService iSmartRssService){
		this.iSmartRssService = iSmartRssService;
	}
	
	@Override
	public String getHelp() {
		String msgHelp = "=== Smart Rss Control Command ===\n" +
				"\t sr status\n";
		return msgHelp;
	}
	
	public void _sr(CommandInterpreter ci) throws Exception {
		String cmd = ci.nextArgument();
		if(cmd.equals("status")){
			if(this.iSmartRssService != null){
				List<NewInfoExtractWorker> monitorSiteList = this.iSmartRssService.getMonitorSiteList();
//				for(NewInfoExtractWorker niew : monitorSiteList){
//					String status = niew.getTargetSite().getName() + "\n" +
//							niew.getStatus() + "\n" +
//							niew.getNewInfo().size() + "\n" +
//							"----------------------";
//					System.out.println(status);
//				}
//				System.out.println("Target Site List :" + monitorSiteList.size());
				log.info("Target Site List :" + monitorSiteList.size());
				TargetSiteManager tsm = this.iSmartRssService.getTargetSiteManager();
				List<TargetSite> targetSiteList = tsm.getTargetSiteList();
				for(TargetSite ts : targetSiteList){
//					System.out.println(ts.getName() + ":" + ts.getTargetUrl() +
//							"\n--------------------------------");
					log.info(ts.getName() + ":" + ts.getTargetUrl() +
							"\n--------------------------------");
				}
				
			} else {
				System.out.println("System State is invalid..");
			}
		}
		return ;
	}
}
