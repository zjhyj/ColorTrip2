package com.whut.net;

import java.util.Map;

import android.R.integer;

import com.whut.myMap.entites.url;
import com.whut.myMap.utils.httpclient;

public class getredsbyhotnet {
	public  String  getredsbyhot(){
		try {
			httpclient httpclient=new httpclient();
			return httpclient.sendGet(new url().geturl()+"/servlet/returnhotreds");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public String getLredsbyhot(Map<String, String> param) {
		try {
			httpclient httpclient=new httpclient();
			//!!!!!!!!
			return httpclient.sendPost(new url().geturl()+"/servlet/returnhotLreds",param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	public String getPredsbyhot(Map<String, String> param) {
		try {
			httpclient httpclient=new httpclient();
			//!!!!!!!!
			return httpclient.sendPost(new url().geturl()+"/servlet/returnhotPreds",param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
