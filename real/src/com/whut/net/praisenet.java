package com.whut.net;

import java.util.Map;

import com.whut.myMap.entites.url;
import com.whut.myMap.utils.httpclient;

public class praisenet {
	public static   String  praise(Map<String, String> param){
		try {
			httpclient httpclient=new httpclient();
			return httpclient.sendPost(new url().geturl()+"/servlet/praise", param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

}