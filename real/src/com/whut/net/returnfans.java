package com.whut.net;

import java.util.Map;

import com.whut.myMap.entites.url;
import com.whut.myMap.utils.httpclient;

public class returnfans {
	 public static String returnListFans(){
		 try {
				httpclient httpclient=new httpclient();
				return httpclient.sendGet(new url().geturl()+"/servlet/returnlistfans");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
	    }	
}
