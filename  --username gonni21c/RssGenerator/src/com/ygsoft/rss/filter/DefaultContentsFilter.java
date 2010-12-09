package com.ygsoft.rss.filter;

import java.net.URL;
import java.util.List;

import me.yglib.htmlparser.datasource.PageSource;
import me.yglib.htmlparser.datasource.impl.IntResManager;
import me.yglib.htmlparser.parser.Node;
import me.yglib.htmlparser.parser.impl.HtmlDomBuilder;

public class DefaultContentsFilter implements IHtmlContentsFilter {
		
	
	@Override
	public void analyse(Node rootNode) throws FilterException {
		// TODO Auto-generated method stub
		
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
		url = "http://clien.career.co.kr/cs2/bbs/board.php?bo_table=park&wr_id=4094745";
		url = "http://www.bobaedream.co.kr/board/bulletin/view.php?code=battle&No=230085";
		try {
			bufPs = IntResManager.loadStringBufferPage(new URL(url).toURI(), 3000);
			//bufPs = ResourceManager.getLoadedPage(new File("testRes\\test.html"));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		HtmlDomBuilder domBuilder = new HtmlDomBuilder(bufPs);
		List<Node> rootNode = domBuilder.build();
	}
}
