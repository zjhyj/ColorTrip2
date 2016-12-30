package com.whut.net;

import com.whut.myMap.entites.url;
import com.whut.myMap.utils.httpclient;

public class getOtherCommentnet {
	public   String  getothercommentnet(){
		try {
			httpclient httpclient=new httpclient();
			return httpclient.sendGet(new url().geturl()+"/servlet/returnothercomment");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
}
