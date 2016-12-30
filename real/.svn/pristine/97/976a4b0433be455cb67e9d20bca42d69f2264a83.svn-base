package com.whut.net;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whut.myMap.entites.picture;
import com.whut.myMap.entites.url;
import com.whut.myMap.entites.user;
import com.whut.myMap.utils.gsonutil;
import com.whut.myMap.utils.httpclient;

public class updateheadnet {
	public  String  updatehead(File file){
		try {
        	httpclient http=new httpclient();
    		Map<String ,String > parsm=new HashMap<String, String>();	
    		List<File> f=new ArrayList<File>();
    		f.add(file);
    		return http.uploadSubmit(new url().geturl()+"/servlet/updatehead", parsm, f);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
	}
}
