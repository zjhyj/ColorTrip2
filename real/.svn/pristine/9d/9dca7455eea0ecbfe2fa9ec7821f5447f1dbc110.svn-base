package com.whut.net;

import java.util.Map;

import com.whut.myMap.entites.url;
import com.whut.myMap.utils.httpclient;

public class getusernet {
	public  String  getuser(){
	try {
		httpclient httpclient=new httpclient();
		return httpclient.sendGet(new url().geturl()+"/servlet/returnuser");
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		throw new RuntimeException(e);
	}
	}
	
	public  String  getuserother(Map<String, String> param){
		try {
			httpclient httpclient=new httpclient();
			return httpclient.sendPost(new url().geturl()+"/servlet/returnuserbyuserid",param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		}
}
