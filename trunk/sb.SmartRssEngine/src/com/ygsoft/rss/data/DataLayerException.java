package com.ygsoft.rss.data;

public class DataLayerException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private String strMsg = null;
	
	public DataLayerException(String message){
		this.strMsg = message;
	}
	
	public String getLogicMessage(){
		return this.strMsg;
	}
}
