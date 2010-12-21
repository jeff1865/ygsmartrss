package com.ygsoft.rss.filter;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.ygsoft.rss.WebUtil;

import me.yglib.htmlparser.CommonException;
import me.yglib.htmlparser.TagAttribute;
import me.yglib.htmlparser.Token;
import me.yglib.htmlparser.TokenTag;
import me.yglib.htmlparser.TokenText;
import me.yglib.htmlparser.datasource.PageSource;
import me.yglib.htmlparser.datasource.impl.IntResManager;
import me.yglib.htmlparser.ex.filter.BasicFilter;
import me.yglib.htmlparser.ex.filter.NodeGroup;
import me.yglib.htmlparser.parser.Node;
import me.yglib.htmlparser.parser.impl.HtmlDomBuilder;

public class DefaultContentsFilter implements IHtmlContentsFilter {
	
	Logger log = Logger.getLogger(DefaultContentsFilter.class);
	
	private static double minimumContAvg = 1.1;
	private static int minCharAvg = 15;	// date time format filtering
	private List<NodeGroup> resGroup = null;
	private HashSet<String> filteredTag = null;
	private HashSet<String> viewIgnoredTag = null;
	private String targetUrl = null;
	
	public DefaultContentsFilter(String url){
		this.filteredTag = new HashSet<String>();
		this.filteredTag.add("a");
		this.filteredTag.add("br");
		this.filteredTag.add("p");
		this.filteredTag.add("img");
		
		this.viewIgnoredTag = new HashSet<String>();
		this.viewIgnoredTag.add("form");
		this.viewIgnoredTag.add("input");
		this.viewIgnoredTag.add("textarea");
		
		this.targetUrl = url;
	}
	
	@Override
	public void analyse(Node rootNode, String extRule) throws FilterException {
		BasicFilter bFilter = new BasicFilter(rootNode);
		List<NodeGroup> extNgs = bFilter.getExtractedNodeGroups();
		
		if(extRule != null){	// Rule Exist !
			;	//TODO rule based extract
		} else {	// in case that rule doesn't exist !
			// Single Page Filtering
			this.getLengthFilteredNodeGroup(extNgs);
//			List<NodeGroup> mng = getMergedNodeGroup(extNgs);

			this.resGroup = extNgs;
		}
	}
	
	@Override
	public String getContents() {
		if(this.resGroup == null)
			return "Cannot get the contents from site..";
		String retStr = "";
		
		int i = 1;
		//TODO 1. extract Range
		for(NodeGroup ng : this.resGroup){
			if(!ng.isCleaned())
			{
//				System.out.println("Final! ------------------------------");
//				System.out.println(ng);
				// 2. extract contents
				retStr += "<p>Group #"+ i++ + "</p>\n"; 
				retStr += this.getContents(ng, 0, ng.getNodes().size()-1);
				
			}
		}
		
		return retStr;
	}
	
	private String getContents(NodeGroup ndGrp, int startIndex, int end){
		String retStr = "";
		
		List<Node> nodes = ndGrp.getNodes();
		Node node = nodes.get(startIndex);
		//System.out.println("Node Index :" + node.getToken().getIndex());
		//TODO ÀüÃ³¸® : Image Getting
 		;
		 		
 		//Node sNode = node.getParent();
 		Node sNode = this.getRootNode(node);
 		System.out.println("Node Index :" + sNode.getToken().getIndex());
		retStr = this.nodeRead(sNode, nodes.get(0).getToken().getIndex(), 
				nodes.get(nodes.size()-1).getToken().getIndex());
 		
		return retStr;
	}
	
	private Node getRootNode(Node node){
		Node pNode = node;
		while(pNode.getParent() != null)
			pNode = pNode.getParent();
		return pNode;
	}
	
	private String nodeRead(Node node, int tokenStart, int tokenEnd){
		String result = "";
		Token token = node.getToken();
		String aroundTag = null;
				
		if(token.getIndex() <= tokenEnd && token.getIndex() >= tokenStart){
			if(token instanceof TokenTag){
				TokenTag tTag = (TokenTag)token;
				if(this.filteredTag.contains(tTag.getTagName().toLowerCase())){
					//System.out.println("[TAG]>>>" + tTag.toHtml());
					if(tTag.getTagName().equalsIgnoreCase("img")){
						result += "<img ";
						for(TagAttribute attr : tTag.getAttrs()){
							if(attr.getAttrName().equalsIgnoreCase("src"))
								result += "src=\"" 
									+ new WebUtil().convertAbsAddr(this.targetUrl, attr.getAttrValue()) + "\""; 
						}
						result += ">";
						aroundTag = tTag.getTagName();
					} else {
						result += tTag.toHtml();
						aroundTag = tTag.getTagName();
					}
				} else{
					//if(tTag.isClosedTag())
						//System.out.println("[xTAG]" + tTag.getTagName());
				}
					
			} else if(token instanceof TokenText){
				TokenText tText = (TokenText)token;
				//System.out.println("[TEXT]>>>" + tText.getValueText());
				if(!this.isIgnoredTagValue(node))
					result += tText.getValueText() + "<br>\n";
			}
			//System.out.println(">>>sNode :" + node.getToken().getIndex());
		}
		
		List<Node> cNodes = node.getChildren();
		if(cNodes != null){
			for(Node cn : cNodes){
				result += this.nodeRead(cn, tokenStart, tokenEnd);
			}
		}
		
		if(aroundTag != null) result += "</" + aroundTag + ">";
		
		return result;
	}
	
	private boolean isIgnoredTagValue(Node node){
		Node tNode = node;
		while(tNode != null){
			if(tNode.getToken() instanceof TokenTag){
				TokenTag tt = (TokenTag)tNode.getToken();
				if(this.viewIgnoredTag.contains(tt.getTagName().toLowerCase())) return true;
			}
			tNode = tNode.getParent();
		}
		
		return false;
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
	
	// Filtering based on Character Length
	private void getLengthFilteredNodeGroup(List<NodeGroup> extNgs){
		log.debug("check node length ..");
		
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
	
//	private void checkWhiteSpace(List<NodeGroup> ngLst){
//		for(NodeGroup ng : ngLst){
//			List<Node> nodes = ng.getNodes();
//			for(Node node : nodes){
//				Token token = node.getToken();
//				if(token instanceof TokenText){
//					TokenText tt = (TokenText)token;
//					tt.getValueText().re
//				}
//			}
//		}
//	}
	
	// Merge duplicated token index range
	private List<NodeGroup> getMergedNodeGroup(List<NodeGroup> lengthFiltered){
		ArrayList<NodeGroup> lstMerged = new ArrayList<NodeGroup>();
		
		NodeGroup newNg = null;
		int index = 0;
		for(NodeGroup ng : lengthFiltered){
			
			if(!ng.isCleaned() && !ng.isDuplicated()){
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
		
	public static void main(String ... v){
		PageSource bufPs = null;
		String url = "http://www.asiae.co.kr/news/view.htm?idxno=2010082112172067284";
		//url = "http://clien.career.co.kr/cs2/bbs/board.php?bo_table=park&wr_id=4094745";
		url = "http://www.bobaedream.co.kr/board/bulletin/view.php?code=battle&No=230668";
		//url = "http://clien.career.co.kr/cs2/bbs/board.php?bo_table=park&wr_id=4163961";
		//url = "http://gall.dcinside.com/list.php?id=stock&no=5182820&page=1&bbs=";
		//url = "http://news.naver.com/main/read.nhn?mode=LSD&mid=sec&sid1=110&oid=029&aid=0000092796";
		url = "http://clien.career.co.kr/cs2/bbs/board.php?bo_table=park&wr_id=4184791";
		//url = "http://todayhumor.co.kr/board/view.php?table=humorbest&no=316931&page=1&keyfield=&keyword=&sb=";
		//url = "http://biz.chosun.com/site/data/html_dir/2010/12/10/2010121001200.html?Dep1=news&Dep2=biz&Dep3=biz_news";
		//url = "http://www.bobaedream.co.kr/board/bulletin/view.php?code=battle&No=230755";
		
		try {
			bufPs = IntResManager.loadStringBufferPage(new URL(url).toURI(), 3000);
			//bufPs = ResourceManager.getLoadedPage(new File("testRes\\test.html"));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		HtmlDomBuilder domBuilder = new HtmlDomBuilder(bufPs);
		List<Node> rootNode = null;
		try {
			rootNode = domBuilder.build();
		} catch (CommonException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		DefaultContentsFilter dcf = new DefaultContentsFilter(url);
		try {
			dcf.analyse(rootNode.get(0), null);
		} catch (FilterException e) {
			e.printStackTrace();
		}
		
		String contents = dcf.getContents();
		
		System.out.println("Result -------------------------\n" + contents);
		
	}
}
