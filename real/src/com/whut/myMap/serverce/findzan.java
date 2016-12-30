package com.whut.myMap.serverce;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.MediaStore.Video;

import com.baidu.pano.platform.http.u;
import com.whut.myMap.dao.picturedao;
import com.whut.myMap.dao.zandao;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.entites.url;
public class findzan {
	private int userid;
	public findzan(Context context) {
		super();
		SharedPreferences settings = context.getSharedPreferences("userInfo", 0);
		userid = settings.getInt(url.SPUSER_ID, 0);
	}
	public boolean findzaned(int sourceid,int type, Context context){
		zandao zandao=new zandao(context);
		if(zandao.findzaned(sourceid,type,userid)) return true;
		return false;
	}
	public void addzaned (int sourceid,int type, Context context) {
		zandao zandao=new zandao(context);
		zandao.addzaned(sourceid,type,userid);
	}
	public void deletezaned(int sourceid,int type,Context context){
		zandao zandao=new zandao(context);
		zandao.deletezaned(sourceid,type,userid);
	}
}
