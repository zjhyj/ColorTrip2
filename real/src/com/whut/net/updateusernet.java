package com.whut.net;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.whut.myMap.entites.url;
import com.whut.myMap.entites.user;
import com.whut.myMap.utils.httpclient;

public class updateusernet {
    public String updateuser(user pMessage){
    	try{
    	httpclient httpclient=new httpclient();
    	Gson gson=new Gson();
		String userstring=gson.toJson(pMessage);
		Map<String, String> param=new HashMap<String, String>();
		param.put("userstring", userstring);
		return httpclient.sendPost(new url().geturl()+"/servlet/updateuser",param);
    	}catch(Exception e){
    		e.printStackTrace();
    		throw new RuntimeException(e);	
    	}
    }	
}
