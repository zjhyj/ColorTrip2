package com.whut.net;

import com.whut.myMap.entites.url;
import com.whut.myMap.utils.httpclient;

public class gettracklbsnet {
	public String gettracklbs() {
		// TODO Auto-generated method stub
		try {
			httpclient httpclient=new httpclient();
			return httpclient.sendGet(new url().geturl()+"/servlet/gettracklbs");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}	
	}
}