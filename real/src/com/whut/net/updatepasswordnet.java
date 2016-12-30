package com.whut.net;

import java.util.Map;

import com.whut.myMap.entites.url;
import com.whut.myMap.utils.httpclient;

public class updatepasswordnet {
	public   String  updatepassword(Map<String, String> param){
		try {
			httpclient httpclient=new httpclient();
			String result= httpclient.sendPost(new url().geturl()+"/servlet/updatepassword", param);
		    System.out.println("result");
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public   String  changepassword(Map<String, String> param){
		try {
			httpclient httpclient=new httpclient();
			String result= httpclient.sendPost(new url().geturl()+"/servlet/changepassword", param);
		    System.out.println("result");
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
