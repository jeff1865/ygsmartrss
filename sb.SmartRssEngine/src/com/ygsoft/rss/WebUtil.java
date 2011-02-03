package com.ygsoft.rss;

import java.io.File;
import java.util.ArrayList;

public class WebUtil {

	public String convertAbsAddr(String rootAddress, String relativeAddr) {
		System.out.println("\n---Convert--->" + rootAddress + "\nR:" + relativeAddr);
		if(relativeAddr.startsWith("http://")) return relativeAddr;
				
		String retStr = "http://";
		
		String rawType = null;//rootAddress.substring("http://".length(), rootAddress.lastIndexOf("/"));
		if(rootAddress.lastIndexOf("/") > 8){
			rawType = rootAddress.substring("http://".length(), rootAddress.lastIndexOf("/"));
		} else {
			rawType = rootAddress.substring("http://".length());
		}
		
		if(!relativeAddr.contains("/")){
			return retStr + rawType + "/" + relativeAddr;
		}
				
		String[] baseDir = rawType.split("/");
		
		int count = 0;
		String[] relDir = relativeAddr.split("/");
		for(String str : relDir){
			if(str.equals("..")) count ++;
			else break;
		}
		
		if(relativeAddr.startsWith("/")) { 
			retStr += baseDir[0] + "/";
		} else {
			for(int i=0;i<baseDir.length - count;i++){
				retStr += baseDir[i] + "/";
			}
		}
				
		for(int i=count;i<relDir.length;i++){
			retStr += relDir[i];
			if(i < relDir.length - 1 && relDir[i].length() > 0)
				retStr += "/";
		}
		
		return retStr;
	}

	public static void main(String... v) {
		String strRoot = "http://clien.career.co.kr/cs2/bbs/board.php?bo_table=park";
		String strRel = "../bbsa/board.php?bo_table=park&wr_id=4083325";
		WebUtil webUtil = new WebUtil();
		String absAddr = webUtil.convertAbsAddr(strRoot, strRel);
		
		System.out.println("->" + absAddr);
	}

}
