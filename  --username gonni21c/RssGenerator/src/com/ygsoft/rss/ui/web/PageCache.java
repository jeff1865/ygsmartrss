package com.ygsoft.rss.ui.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class PageCache {
	
	private static final String viewFolder = "webViews/"; 
	
	private Hashtable<String, String> pageMap = null;
	private Logger log = Logger.getLogger(PageCache.class);
	
	public PageCache(){
		this.pageMap = new Hashtable<String, String>();
		this.loadPages();
	}
	
	public void loadPages(){
		this.pageMap.clear();
		
		File file = new File(viewFolder);
		System.out.println("File >" + file.getAbsolutePath());
		String data = null;
		
		for(File sFile : file.listFiles()){
			if(sFile.getName().endsWith(".gdp")){
				System.out.println("GDP load>" + sFile.getAbsolutePath());
				if((data = this.getFileString(sFile)) != null)
					this.pageMap.put(sFile.getName().substring(0, sFile.getName().length()-4), data);
			}
		}
	}
	
	public String getPageTemplate(String templateId){
		if(this.pageMap != null && this.pageMap.containsKey(templateId))
			return this.pageMap.get(templateId);
		
		return null;
	}
	
	public void showData(){
		if(this.pageMap != null){
			Enumeration<String> keys = this.pageMap.keys();
			while(keys.hasMoreElements()){
				String key = keys.nextElement();
				System.out.println("Key Code :" + key);
				System.out.println(this.pageMap.get(key));
				System.out.println("------------------------------------------------");
			}
		}
	}
	
	private String getFileString(File file){
		String retStr = "";
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
			String line = null;
			while((line = br.readLine()) != null){
				retStr += line + "\n";
				//retStr += new String(line.getBytes("utf-8"), "utf-8");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return retStr;
	}
	
	public static void main(String ... v){
		PageCache cp = new PageCache();
		cp.loadPages();
		cp.showData();
	}
	
}
