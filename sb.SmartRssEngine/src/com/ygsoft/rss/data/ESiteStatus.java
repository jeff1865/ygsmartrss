package com.ygsoft.rss.data;

public enum ESiteStatus {
	Registered,
	ReadyToService,
	Active,
	InChecking,
	//----------------
	StoppedByAdmin,
	ValidDateExpired,
	InternalError,
	Unknown;
	
	public static ESiteStatus getEnumValue(int index){
		for(ESiteStatus siteStatus : ESiteStatus.values()){
			if(siteStatus.ordinal() == index){
				return siteStatus;
			}
		}
		return ESiteStatus.Unknown ;
	}
}
