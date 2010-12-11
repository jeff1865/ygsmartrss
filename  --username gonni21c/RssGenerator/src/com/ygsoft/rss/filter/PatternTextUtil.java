package com.ygsoft.rss.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PatternTextUtil {
	
	public static String getPatternedUrl(String url){
		if(!(url.startsWith("http://") && url.indexOf("/", "http://".length()) > 0))
			return null;
		
		StringBuffer retStr = new StringBuffer("");
		
		boolean digitFlow = false;
		int counter = 0;
		
		for(char ch : url.toCharArray()){
			if(Character.isDigit(ch)){
				if(digitFlow){
					counter ++;
				} else {
					digitFlow = true;
					counter = 1;
				}
			} else {
				if(digitFlow){
					retStr.append("[d:" + counter + "]");
					digitFlow = false;
					counter = 0;
				}
				retStr.append(ch);
			}
		}
		
		if(counter > 0){
			retStr.append("[d:" + counter + "]");
		}
		
		return retStr.toString();
	}
	
	public static boolean isSamePatternUrl(String pattern, String url, boolean ignoreDigitNumber){
		List<String> lstBase = convertTokenToList(pattern);
		List<String> lstUrl = convertTokenToList(getPatternedUrl(url));
		
		if(lstBase.size() == lstUrl.size()){
			int i = 0;
			String urlTk = null;
			for(String tk : lstBase){
				urlTk = lstUrl.get(i++);
				if(tk.startsWith("d:")){
					if(ignoreDigitNumber){
						if(!urlTk.startsWith("d:")) return false;
					} else {
						if(!tk.equals(urlTk))
							return false;
					}
				} else {
					if(!tk.equals(urlTk))
						return false;
				}
			}
		}
		
		return true;
	}
	
	private static List<String> convertTokenToList(String pattern){
		ArrayList<String> retAl = new ArrayList<String>();
		StringTokenizer patSt = new StringTokenizer(pattern,"[|]");
		while(patSt.hasMoreTokens()){
			retAl.add(patSt.nextToken());
		}
		return retAl;
	}
	
	public static void main(String ... v){
		String source = "http://star.mt.co.kr/view/stview.php?no=2010121010390179571&type=1&outlink=1";
		
//		System.out.println(source);
//		System.out.println(getPatternedUrl(source));
		
//		StringTokenizer stkn = new StringTokenizer(getPatternedUrl(source),"[|]");
//		while(stkn.hasMoreTokens()){
//			System.out.println(stkn.nextToken());
//		}
		
		String source1 = "http://star.mt.co.kr/view/stview.php?no=12010121010390179571&type=1&outlink=1";
		
		String pUrl = getPatternedUrl(source);
		System.out.println(isSamePatternUrl(pUrl, source1, true));
	}
}
