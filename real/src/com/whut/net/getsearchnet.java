package com.whut.net;

import java.util.Map;

import com.whut.myMap.entites.url;
import com.whut.myMap.utils.httpclient;

public class getsearchnet {
	public String getsearch(Map<String, String> param) {
		// TODO Auto-generated method stub
		try {
			httpclient httpclient=new httpclient();
			return httpclient.sendPost(new url().geturl()+"/servlet/getsearch",param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public String getLsearch(Map<String, String> param) {
		// TODO Auto-generated method stub
		try {
			httpclient httpclient=new httpclient();
			return httpclient.sendPost(new url().geturl()+"/servlet/getsearch",param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
