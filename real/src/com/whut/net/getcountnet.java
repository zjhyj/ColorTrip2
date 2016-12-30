package com.whut.net;

import java.util.HashMap;
import java.util.Map;

import com.whut.myMap.entites.url;
import com.whut.myMap.utils.httpclient;

public class getcountnet {

	public String getlocal(int clusterid) {
		// TODO Auto-generated method stub
		try {
			Map<String,String> param=new HashMap<String, String>();
			param.put("cluster",clusterid+"");
			httpclient httpclient=new httpclient();
			return httpclient.sendPost(new url().geturl()+"/servlet/getcount", param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
    
	
	
	
}
