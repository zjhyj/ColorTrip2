package com.whut.net;

import java.util.Map;

import com.whut.myMap.entites.url;
import com.whut.myMap.utils.httpclient;

public class gettrackbyTravelmaster {
	public  static String  gettrackbyscore(){
		try {
			httpclient httpclient=new httpclient();
			return httpclient.sendGet(new url().geturl()+"/servlet/getfamtrack");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}	
	}
}