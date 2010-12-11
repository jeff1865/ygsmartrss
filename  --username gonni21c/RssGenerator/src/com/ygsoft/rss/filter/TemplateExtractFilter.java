package com.ygsoft.rss.filter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import me.yglib.htmlparser.datasource.PageSource;
import me.yglib.htmlparser.datasource.impl.IntResManager;
import me.yglib.htmlparser.ex.filter.BasicFilter;
import me.yglib.htmlparser.ex.filter.NodeGroup;
import me.yglib.htmlparser.ex.node.NodePathUtil;
import me.yglib.htmlparser.parser.Node;
import me.yglib.htmlparser.parser.impl.HtmlDomBuilder;

public class TemplateExtractFilter {
	
	private Node mainNode = null;
	private List<Node> patternedNodes = null;
	private List<NodeGroup> extractedNoes = null;
	
	public TemplateExtractFilter(Node targetNode, List<Node> samePatternedNodes){
		this.mainNode = targetNode;
		this.patternedNodes = samePatternedNodes;
	}
	
	public void analyze(){
		this.extractedNoes = new ArrayList<NodeGroup>();
		
		BasicFilter mainFilter = new BasicFilter(this.mainNode);
		List<NodeGroup> mainGrp = mainFilter.getExtractedNodeGroups();
		
		BasicFilter subFilter = new BasicFilter(this.patternedNodes.get(0));
		List<NodeGroup> subGrp = subFilter.getExtractedNodeGroups();
		
		for(NodeGroup nodeGrp : mainGrp){
			if(!this.existGrp(nodeGrp, subGrp)) this.extractedNoes.add(nodeGrp); 
		}
	}
	
	public List<NodeGroup> getResultNodeGroup(){
		return this.extractedNoes;
	}
	
	private boolean existGrp(NodeGroup nodeGrp, List<NodeGroup> subGrp){
		String mainRule = nodeGrp.getGroups().get(0);
		String subRule = null;
		for(NodeGroup ng : subGrp){
			subRule = ng.getGroups().get(0);
			if(NodePathUtil.isSameLogic(mainRule, subRule, true)){	// Tree Path가 같은 경우
				if(nodeGrp.getCharCount() == ng.getCharCount()) return true;	// Char Count 값이 같은 경우
				if(nodeGrp.getNodes().size() == ng.getNodes().size()){
					//TODO need to impl pattern checker
//					System.out.println("X--------------------------------------------------");
//					System.out.println("Node Grp>" + nodeGrp);
//					System.out.println("Sode Grp>" + ng);
//					System.out.println("Length:" + nodeGrp.getNodes().size() + ":" + 
//							ng.getNodes().size());
					return true;
				}
				
			}
		}
		return false;
	}
	
	/**
	 * ex) 2010-10-11 fall in love -> dddd-dd-dd cccc cc cccc
	 * @param source
	 * @return
	 */
	public String getPatternedRule(String source, boolean checkOnlyNum){
		return null;
	}
	
	public String getRule(){
		return null;
	}
	
	public String getFilteredContents(){
		return null;
	}
	
	public static void main(String ... v){
		PageSource bufPs = null, bufPs2 = null;
		String url = "http://www.asiae.co.kr/news/view.htm?idxno=2010082112172067284";
		url = "http://www.asiae.co.kr/news/view.htm?idxno=2010082215175501055";
		url = "http://clien.career.co.kr/cs2/bbs/board.php?bo_table=park&wr_id=4168671";
		//url = "http://www.bobaedream.co.kr/board/bulletin/view.php?code=battle&No=230085";	// not stable
		//url = "http://jakarta.tistory.com/68";
		String url2 = "http://clien.career.co.kr/cs2/bbs/board.php?bo_table=park&wr_id=4168731";
		//url = "http://bobaedream.co.kr/board/bulletin/view.php?code=battle&No=230598";
		//url2 = "http://bobaedream.co.kr/board/bulletin/view.php?code=battle&No=230594";
		try {
			bufPs = IntResManager.loadStringBufferPage(new URL(url).toURI(), 3000);
			bufPs2 = IntResManager.loadStringBufferPage(new URL(url2).toURI(), 3000);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		HtmlDomBuilder domBuilder = new HtmlDomBuilder(bufPs);
		List<Node> mainNodes = domBuilder.build();
				
		List<Node> subNodes = new HtmlDomBuilder(bufPs2).build();
		ArrayList<Node> sNodes = new ArrayList<Node>();
		sNodes.add(subNodes.get(0));
		
		TemplateExtractFilter tef = new TemplateExtractFilter(mainNodes.get(0), sNodes);
		tef.analyze();
		
		for(NodeGroup ng : tef.getResultNodeGroup()){
			System.out.println("R -------------------------------");
			System.out.println(ng);
		}
	}
}
