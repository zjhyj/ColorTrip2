package com.whut.net;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.baidu.mapapi.model.LatLng;
import com.whut.myMap.entites.url;
import com.whut.myMap.utils.httpclient;

public class getclusterpairbynetid {
	public String getclusterpair(int netid) {
    	try {
    		//加了几个类，更改了mapframent
    		Map<String,String> bounds=new HashMap<String, String>();
			httpclient httpclient=new httpclient();
	    	bounds.put("netid",netid+"");
	    	Log.i("NETID", netid+"");
			return httpclient.sendPost(new url().geturl()+"/servlet/getclusterpair", bounds);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
  }
}
