package com.whut.net;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.whut.myMap.entites.url;
import com.whut.myMap.utils.httpclient;

public class getlocaltracknet {

	public String getlocaltrack(Map<String, String> param) {
		// TODO Auto-generated method stub
		try {
    		//加了几个类，更改了mapframent
			httpclient httpclient=new httpclient();
			return httpclient.sendPost(new url().geturl()+"/servlet/getlocaltrack", param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
