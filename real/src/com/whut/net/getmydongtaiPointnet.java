package com.whut.net;

import java.util.Map;

import com.whut.myMap.entites.url;
import com.whut.myMap.utils.httpclient;

public class getmydongtaiPointnet {
	public   String  getmydongtaiPoint(){
		try {
			httpclient httpclient=new httpclient();
			return httpclient.sendGet(new url().geturl()+"/servlet/getmydongtaiPoint");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
	}

	public String getflagmydongtaiPoint(Map<String, String> param) {
		// TODO Auto-generated method stub
		try {
			httpclient httpclient=new httpclient();
			return httpclient.sendPost(new url().geturl()+"/servlet/getflagmydongtaipoint",param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
	}
}
