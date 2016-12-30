package com.whut.net;

import java.util.Map;

import com.whut.myMap.entites.url;
import com.whut.myMap.utils.httpclient;

public class getOneSpeakPointnet {
	public  String  getOneSpeak(Map<String, String> param){
		try {
			httpclient httpclient=new httpclient();
			return httpclient.sendPost(new url().geturl()+"/servlet/returnOneSpeak", param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
}
