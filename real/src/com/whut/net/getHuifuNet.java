package com.whut.net;

import java.util.Map;

import com.whut.myMap.entites.url;
import com.whut.myMap.utils.httpclient;

public class getHuifuNet {
	public  String  getHuifu(Map<String, String> param){
		try {
			httpclient httpclient=new httpclient();
			return httpclient.sendPost(new url().geturl()+"/servlet/returnhuifu", param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
