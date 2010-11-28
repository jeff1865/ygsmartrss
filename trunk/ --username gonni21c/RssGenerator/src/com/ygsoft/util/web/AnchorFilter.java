package com.ygsoft.util.web;

import java.net.URL;
import java.util.*;

import org.apache.log4j.Logger;

import me.yglib.htmlparser.TagAttribute;
import me.yglib.htmlparser.Token;
import me.yglib.htmlparser.TokenTag;
import me.yglib.htmlparser.TokenText;
import me.yglib.htmlparser.datasource.PageSource;
import me.yglib.htmlparser.datasource.impl.ResourceManager;
import me.yglib.htmlparser.parser.Node;
import me.yglib.htmlparser.parser.impl.HtmlDomBuilder;

public class AnchorFilter {
	
	private List<ContentsFilterPlugin> contFilters = null;
	private String rootUrl = null;
	private Logger log = Logger.getLogger(AnchorFilter.class);
		
	public AnchorFilter(String rootUrl){
		this.rootUrl = rootUrl;
	}
	
	public void setUrl(String url){
		this.rootUrl = url;
	}
	
	public void addFilterPlugin(ContentsFilterPlugin fPlugin){
		if(this.contFilters == null)
			this.contFilters = new ArrayList<ContentsFilterPlugin>();
		this.contFilters.add(fPlugin);
	}
	
	
	public List<Anchor> getAnchorTexts(){
		PageSource bufPs = null;
		try {
			bufPs = ResourceManager.loadStringBufferPage(new URL(this.rootUrl).toURI(), 3000);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		HtmlDomBuilder domBuilder = new HtmlDomBuilder(bufPs);
		List<Node> rootNode = domBuilder.build();
		ArrayList<Anchor> lstRes = new ArrayList<Anchor>();
		
		for(Node node : rootNode){
			this.extractAnchor(node, lstRes);
		}
		
		return lstRes;
	}
	
	// called by getAnchorTexts
	private void extractAnchor(Node node, List<Anchor> lstRes){
		// do extract anchor from node		
		if(this.isAnchorNode(node)){
			// get info 
			// TODO create anchor instance(containing URL)
			TokenTag anchorTag = (TokenTag)node.getToken();
			String strUrl = this.getAttribute(anchorTag, "href");
			if(strUrl != null){
				Anchor anchor = new Anchor(strUrl);
				this.getAnchor(node, anchor);
				log.debug("add anchor :" + anchor);
				lstRes.add(anchor);
			}
			
			return;
		}
		
		// call recursively to find anchor
		List<Node> subNodes = node.getChildren();
		if(subNodes != null){
			for(Node snode : subNodes) {
				this.extractAnchor(snode, lstRes);
			}
		}
	}
	
	//called by extractAnchor
	private void getAnchor(Node anchorNode, Anchor anchor){
		List<Node> subNodes = anchorNode.getChildren();
		Token token = null;
		
		if(subNodes != null)
			for(Node node : subNodes){
				if((token = node.getToken()) instanceof TokenText){
					// case text : add all
					anchor.addText("[T]" + ((TokenText)token).getValueText());
				} else if((token = node.getToken()) instanceof TokenTag){
					// case tag : add <img> src
					TokenTag ttag = (TokenTag)token;
					if(ttag.getTagName().trim().equals("img")){
						List<TagAttribute> attrs = ttag.getAttrs();
						for(TagAttribute attr : attrs){
							if(attr.getAttrName().equalsIgnoreCase("src")){
								anchor.addText("[I]" + attr.getAttrValue());
								break;
							}
						}
					}
				}
			}
	}
	
	//TODO need to move into HELPER
	// Called by methods..
	private String getAttribute(TokenTag tTag, String attrName){
		List<TagAttribute> attrs = tTag.getAttrs();
		for(TagAttribute attr : attrs){
			if(attr.getAttrName().trim().equalsIgnoreCase(attrName))
				return attr.getAttrValue();
		}
		return null;
	}
			
	private boolean isAnchorNode(Node node){
		
		Token token = node.getToken();
		if(token instanceof TokenTag){
			
			TokenTag tkTag = (TokenTag)token;
			String tagName = tkTag.getTagName();
			if(tagName.equalsIgnoreCase("a")){
				return true;
			}
		}
		return false;
	}
		
	public static void main(String ... v){
		AnchorFilter test = new AnchorFilter("http://clien.career.co.kr/cs2/bbs/board.php?bo_table=park");
		List<Anchor> anchorTexts = test.getAnchorTexts();
		int i=0;
		for(Anchor anchor : anchorTexts){
			System.out.println(++i + "Result>" + anchor);
		}
	}
	
}
