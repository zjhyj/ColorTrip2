package com.whut.net;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.baidu.mapapi.model.LatLng;
import com.whut.myMap.entites.url;
import com.whut.myMap.utils.httpclient;

public class getservicelbsnet {

	public String getservicelbs() {
		// TODO Auto-generated method stub
		try {
			httpclient httpclient=new httpclient();
			return httpclient.sendGet(new url().geturl()+"/servlet/getcom");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}	
	}
	public String getservicelbsAll(LatLng ne, LatLng sw) {
		// TODO Auto-generated method stub
		try {
			Map<String,String> bounds=new HashMap<String, String>();
			httpclient httpclient=new httpclient();
	    	bounds.put("lef", sw.latitude+"");
	    	bounds.put("rig", ne.latitude+"");
	    	bounds.put("top", sw.longitude+"");
	    	bounds.put("bot",ne.longitude+"");
	    	Log.i("weidu32432324323",sw.latitude+""+ne.latitude+""+ne.longitude+""+sw.longitude);
			return httpclient.sendPost(new url().geturl()+"/servlet/getcomAll", bounds);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}	
	}
	public String getinfoThree() {
		// TODO Auto-generated method stub
		try {
			httpclient httpclient=new httpclient();
			return httpclient.sendGet(new url().geturl()+"/servlet/getmymapinfo");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}	
	}
	public String getinfoCal() {
		// TODO Auto-generated method stub
		try {
			httpclient httpclient=new httpclient();
			return httpclient.sendGet(new url().geturl()+"/servlet/getmyscore");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}	
	}
}
