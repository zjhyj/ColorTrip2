package com.whut.net;

import java.util.Map;

import com.whut.myMap.entites.url;
import com.whut.myMap.utils.httpclient;

public class getuserdongtainet {
	public String getflaguserdongtaiPoint(Map<String, String> param) {
		// TODO Auto-generated method stub
		try {
			httpclient httpclient=new httpclient();
			return httpclient.sendPost(new url().geturl()+"/servlet/getflaguserdongtaipoint",param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
	}
	
	public String getflaguserdongtaiRoute(Map<String, String> param) {
		// TODO Auto-generated method stub
		try {
			httpclient httpclient=new httpclient();
			return httpclient.sendPost(new url().geturl()+"/servlet/getflaguserdongtaitrack",param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
	}
}
