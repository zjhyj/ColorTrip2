package com.whut.net;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;

import com.baidu.mapapi.cloud.BoundSearchInfo;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Projection;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.whut.myMap.entites.reds;
import com.whut.myMap.entites.url;
import com.whut.myMap.utils.httpclient;
public class getlocalredsnet {  
	private MapView mapview;
	private LatLngBounds latLngBounds;
	public String getlocal(LatLng ne, LatLng sw) {
    	try {
    		//加了几个类，更改了mapframent
    		Map<String,String> bounds=new HashMap<String, String>();
			httpclient httpclient=new httpclient();
	    	bounds.put("lef", sw.latitude+"");
	    	bounds.put("rig", ne.latitude+"");
	    	bounds.put("top", sw.longitude+"");
	    	bounds.put("bot",ne.longitude+"");
	    	Log.i("weidu32432324323",sw.latitude+""+ne.latitude+""+ne.longitude+""+sw.longitude);
			return httpclient.sendPost(new url().geturl()+"/servlet/getCluster", bounds);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}  
}
