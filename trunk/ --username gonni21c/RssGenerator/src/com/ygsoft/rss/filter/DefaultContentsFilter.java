package com.ygsoft.rss.filter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import me.yglib.htmlparser.datasource.PageSource;
import me.yglib.htmlparser.datasource.impl.IntResManager;
import me.yglib.htmlparser.ex.filter.BasicFilter;
import me.yglib.htmlparser.ex.filter.NodeGroup;
import me.yglib.htmlparser.parser.Node;
import me.yglib.htmlparser.parser.impl.HtmlDomBuilder;

public class DefaultContentsFilter implements IHtmlContentsFilter {
	
	private static double minimumContAvg = 1.1;
	private static int minCharAvg = 17;	// date time format filtering
	private List<NodeGroup> resGroup = null;
	
	@Override
	public void analyse(Node rootNode, String extRule) throws FilterException {
		BasicFilter bFilter = new BasicFilter(rootNode);
		List<NodeGroup> extNgs = bFilter.getExtractedNodeGroups();
		
		if(extRule != null){	// Rule Exist !
			;
		} else {	// in case that rule doesn't exist !
			// Single Page Filtering
			this.getLengthFilteredNodeGroup(extNgs);
			List<NodeGroup> mng = getMergedNodeGroup(extNgs);
			for(NodeGroup ng : mng){
				System.out.println("Final! ------------------------------");
				System.out.println(ng);
			}
		}
	}
	
	private void getLengthFilteredNodeGroup(List<NodeGroup> extNgs){
		System.out.println("== Check! ==");
		// 1. 문장포함량 검사
		for(NodeGroup ng : extNgs){
			int sentCount = ng.getSentCount();
			int validSentCount = ng.getCountContainValidCont();
			int charCount = ng.getCharCount();
			
			if((validSentCount == 0 || (double)sentCount / validSentCount < minimumContAvg)){
				if(validSentCount == 0 || (double)charCount / ng.getNodes().size() < minCharAvg) {
					ng.setCleaned(true);
				} 
			}
		}
	}
	
	private List<NodeGroup> getMergedNodeGroup(List<NodeGroup> lengthFiltered){
		ArrayList<NodeGroup> lstMerged = new ArrayList<NodeGroup>();
		
		NodeGroup newNg = null;
		int index = 0;
		for(NodeGroup ng : lengthFiltered){
			
			if(!ng.isCleaned() && !ng.isDuplicated()){
//				System.out.println("Final----------------");
//				System.out.println(ng);
				ng.setDuplicated(true);
				
				newNg = new NodeGroup();
				newNg.addNodes(ng.getNodes());
				newNg.setLogicGroup("Group #" + index++);
				
				for(NodeGroup sub : lengthFiltered){
					if(!sub.isDuplicated() && this.isDuplicatedRange(ng, sub)){
						newNg.addNodes(sub.getNodes());
						sub.setDuplicated(true);
					}
				}
				
				lstMerged.add(newNg);
			}
		}
		
		return lstMerged;
	}
	
	private boolean isDuplicatedRange(NodeGroup mainNg, NodeGroup subNg){
		List<Node> nodes = mainNg.getNodes();
		int min = nodes.get(0).getToken().getIndex();
		int max = nodes.get(nodes.size()-1).getToken().getIndex();
		
		for(Node nd : subNg.getNodes()){
			int ndIndex = nd.getToken().getIndex();
			if(ndIndex > min && ndIndex < max)
				return true;
		}
		
		return false;
	}
	
	
	@Override
	public String getContents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSummarizedContetns() {
		// TODO Auto-generated method stub
		return "TBD";
	}

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "TBD";
	}

	
	public static void main(String ... v){
		PageSource bufPs = null;
		String url = "http://www.asiae.co.kr/news/view.htm?idxno=2010082112172067284";
		//url = "http://clien.career.co.kr/cs2/bbs/board.php?bo_table=park&wr_id=4094745";
		url = "http://www.bobaedream.co.kr/board/bulletin/view.php?code=battle&No=230668";
		//url = "http://clien.career.co.kr/cs2/bbs/board.php?bo_table=park&wr_id=4163961";
		//url = "http://gall.dcinside.com/list.php?id=stock&no=5182820&page=1&bbs=";
		//url = "http://news.naver.com/main/read.nhn?mode=LSD&mid=sec&sid1=110&oid=029&aid=0000092796";
		
		try {
			bufPs = IntResManager.loadStringBufferPage(new URL(url).toURI(), 3000);
			//bufPs = ResourceManager.getLoadedPage(new File("testRes\\test.html"));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		HtmlDomBuilder domBuilder = new HtmlDomBuilder(bufPs);
		List<Node> rootNode = domBuilder.build();
		
		DefaultContentsFilter dcf = new DefaultContentsFilter();
		try {
			dcf.analyse(rootNode.get(0), null);
		} catch (FilterException e) {
			e.printStackTrace();
		}
		
	}
}
