package com.whut.net;

import java.util.HashMap;
import java.util.Map;

import com.whut.myMap.entites.url;
import com.whut.myMap.utils.httpclient;

public class priseservicenet {

	public void priseservice(boolean b, int i) {
		// TODO Auto-generated method stub
		try {
			Map<String,String> param=new HashMap<String, String>();
			if(b){
				param.put("prise",1+"");
			}else{
				param.put("prise",0+"");
			}
			param.put("serviceid",i+"");
			httpclient httpclient=new httpclient();
			httpclient.sendPost(new url().geturl()+"/servlet/priseservice", param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		
	}
}
