package com.whut.net;

import com.whut.myMap.entites.url;
import com.whut.myMap.utils.httpclient;

public class getredslbsnet {

	public String getredslbs() {
		// TODO Auto-generated method stub
		try {
			httpclient httpclient=new httpclient();
			return httpclient.sendGet(new url().geturl()+"/servlet/getredslbs");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}	
	}

}
