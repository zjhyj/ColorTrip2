package com.whut.myMap.serverce;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.whut.myMap.dao.picturedao;
import com.whut.myMap.dao.redsdao;
import com.whut.myMap.db.sqliteopen;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.entites.picture;
import com.whut.myMap.entites.reds;
import com.whut.myMap.entites.url;

public class Redservice extends sqliteopen {
	private int userid;
	public Redservice(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		SharedPreferences settings = context.getSharedPreferences("userInfo", 0);
		userid = settings.getInt(url.SPUSER_ID, 0);
	}
	public void addreds(redsbean redsbean, Context context){
		redsdao redsdao=new redsdao(context);
		picturedao picturedao=new picturedao(context);
		redsbean.getReds().setUser_id(userid);
		redsdao.insertreds(redsbean);
		int redsid=redsdao.getmaxid();
		if(redsbean.getListmap()!=null){
			picturedao.insertpicture(redsbean.getListmap(), redsid);
		}	
	}	
	public List<redsbean> getreds(Context context) {
		try {
		redsdao redsdao=new redsdao(context);
		picturedao picturedao=new picturedao(context);
	    List<reds> listreds=redsdao.getredsbyuser(userid);
	    return  picturedao.getpictures(listreds); 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
    }
	public List<redsbean> getredsbeanbytrackid(int track_id, Context context) {
		// TODO Auto-generated method stub
		redsdao redsdao=new redsdao(context);
		picturedao picturedao=new picturedao(context);
	    List<reds> listreds=redsdao.getreds(track_id);
	    if(listreds!=null){    
	      return  picturedao.getpictures(listreds); 
	      }	else{ 
	    	  Log.i("reds", "weikong");
	         return null; 
	          }
	}
	public void deletereds(Context context, redsbean redsbean) {
		// TODO Auto-generated method stub
		redsdao dao=new redsdao(context);
		dao.seleteredsbyid(redsbean.getReds().getReds_id());
		if(redsbean.getListmap()!=null&&redsbean.getListmap().size()>0){
		for(picture  picture:redsbean.getListmap()){
			new File(picture.getImgurl()).delete();
		}
		}
	}
	public void delete_redsintrack(Context context ,redsbean redsbean) {
		try {
		redsdao redsdao=new redsdao(context);
		redsdao.updatetrackid(0, redsbean.getReds().getReds_id());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
	public void update_trackaddreds(Context context ,int trackid,redsbean redsbean) {
		try {
		redsdao redsdao=new redsdao(context);
		redsdao.updatetrackid(trackid, redsbean.getReds().getReds_id());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
}
