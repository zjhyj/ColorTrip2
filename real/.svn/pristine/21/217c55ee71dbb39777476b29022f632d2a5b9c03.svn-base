package com.whut.myMap.serverce;

import java.io.Serializable;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;

import com.whut.myMap.dao.shoucangdao;
import com.whut.myMap.dao.shoucangdao;
import com.whut.myMap.entites.url;

public class findshoucang implements Serializable {
	private int userid;
	public findshoucang(Context context) {
		super();
		SharedPreferences settings = context.getSharedPreferences("userInfo", 0);
	    userid = settings.getInt(url.SPUSER_ID,0);
	}
	public boolean findshoucanged(int sourceid,int type, Context context){
		shoucangdao shoucangdao=new shoucangdao(context);
		if(shoucangdao.findshoucanged(sourceid,type,userid)) return true;
		return false;
	}
	public void addshoucanged (int sourceid,int type, Context context) {
		shoucangdao shoucangdao=new shoucangdao(context);
		shoucangdao.addshoucanged(sourceid,type,userid);
	}
	public void deleteshoucanged(int sourceid,int type,Context context){
		shoucangdao shoucangdao=new shoucangdao(context);
		shoucangdao.deleteshoucanged(sourceid,type,userid);
	}
}
