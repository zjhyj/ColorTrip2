package com.whut.net;

import java.util.Map;

import com.whut.myMap.entites.url;
import com.whut.myMap.utils.httpclient;

public class returnhistorybuy {
	 public static String returnListhistorybuy(){
		 try {
				httpclient httpclient=new httpclient();
				return httpclient.sendGet(new url().geturl()+"/servlet/returnhistorybuy");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
	    }	
	 public static String addbuy(Map<String, String> param){
		 try {
				httpclient httpclient=new httpclient();
				return httpclient.sendPost(new url().geturl()+"/servlet/addbuy", param);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
	    }	
}
