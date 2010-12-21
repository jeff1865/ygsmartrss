package com.ygsoft.rss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import org.apache.log4j.Logger;

public class RssCache {
	
	private Logger log = Logger.getLogger(RssCache.class);
	
	private static final String filePath = "rss/SiteRSS";
	
	private volatile Hashtable<Integer, String> htMain = null;
	
	public RssCache(){
		this.htMain = new Hashtable<Integer, String>();
	}
	
	public synchronized void setNewData(int id, String data){
		this.htMain.put(id, data);
	}
	
	public synchronized String getData(int id){
		if(this.htMain.contains(id)){
			return this.htMain.get(id);
		} else {
			this.loadCurrentFile(id);
			return this.htMain.get(id);
		}
	}
	
	public void loadCurrentFile(int id){
		File file = new File(filePath + id + ".xml");
		if(file.exists()){
			String data = "", line = null;
			BufferedReader br = null;
			try {
				//new InputStreamReader(new FileInputStream(file), "utf-8")
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
				while((line = br.readLine()) != null){
					data += line + "\n";
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(br != null){
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			this.htMain.put(id, data);
		} else {
			log.debug("RSS file doesn't exist, cannot load to Cache");
		}
	}
	
	public static void main(String ... v){
		;
	}
}
