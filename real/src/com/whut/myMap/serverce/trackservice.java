package com.whut.myMap.serverce;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import android.R.integer;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.baidu.mapapi.model.LatLng;
import com.whut.myMap.dao.lbsdao;
import com.whut.myMap.dao.redsdao;
import com.whut.myMap.dao.trackdao;
import com.whut.myMap.db.sqliteopen;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.domain.trackbean;
import com.whut.myMap.entites.alltrack;
import com.whut.myMap.entites.lbs;
import com.whut.myMap.entites.lbs2;
import com.whut.myMap.entites.reds;
import com.whut.myMap.entites.track;
import com.whut.myMap.entites.url;
import com.whut.net.returnfans;

public class trackservice extends sqliteopen{
	private int userid;
	public trackservice(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		SharedPreferences settings = context.getSharedPreferences("userInfo", 0);
		userid = settings.getInt(url.SPUSER_ID, 0);
	}
	public int addtrack(track track,Context context){
		trackdao trackdao=new trackdao(context);
		track.setUser_id(userid);
		trackdao.addtrack(track);
		return trackdao.getmaxid();
	}	
	public void addlbs(List<lbs2> lat2,Context context) {
		lbsdao lbsdao=new lbsdao(context);
		for(lbs2 lbs:lat2){
			Log.i("lbsxy", lbs.getLbsx()+" "+lbs.getLbsy()+" "+lbs.getTrack_id()+" "+lbs.getLbs_id());
		}
	    lbsdao.insertlbs(lat2);	
	}
	public List<trackbean> gettrack(Context context){
		try {
		trackdao trackdao = new trackdao(context);
		lbsdao lbsdao=new lbsdao(context);
		Redservice redservice=new Redservice(context);
		List<trackbean> listtrackbean=new ArrayList<trackbean>();
		List<track> listtrack=trackdao.gettrackbyuser(userid);
		for(track track:listtrack){
			Log.i("trackid", track.getTrack_id()+"");
			List<redsbean> listredsbean=redservice.getredsbeanbytrackid(track.getTrack_id(), context);
	        List<lbs> listlbs=lbsdao.getlbsbytrackid(track.getTrack_id());
			listtrackbean.add(new trackbean(track,listredsbean,listlbs));
		}
		Log.i("cunzai", listtrackbean.size()+"");
  		return listtrackbean;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Log.i("bucunzai", 0+"");
			return null;
		}
			}
	public trackbean gettrack(Context context,int trackid){
		try {
		trackdao trackdao = new trackdao(context);
		lbsdao lbsdao=new lbsdao(context);
		Redservice redservice=new Redservice(context);
//		List<trackbean> listtrackbean=new ArrayList<trackbean>();
		track track=trackdao.gettrack(trackid);
//		for(track track:listtrack){
			List<redsbean> listredsbean=redservice.getredsbeanbytrackid(track.getTrack_id(), context);
	        List<lbs> listlbs=lbsdao.getlbsbytrackid(track.getTrack_id());
	        Log.i("id", track.getTrack_id()+"");
	        return new trackbean(track,listredsbean,listlbs);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Log.i("bucunzai", 0+"");
			return null;
		}
			}
	public void deletetrack(Context context) {
		// TODO Auto-generated method stub
		trackdao dao=new trackdao(context);
		dao.deletetrack();	
	}
	public void deletemaxtrack(Context context){
		trackdao dao=new trackdao(context);
		dao.deletemaxtrack();		
	}
	public void updatetrackwords(String str, int trackid, Context context) {
		// TODO Auto-generated method stub
		trackdao dao=new trackdao(context);
		dao.updatetrackwords(str,trackid,context);		
	}
	public void deletetrack(Context context, int track_id) {
		// TODO Auto-generated method stub
		trackdao dao=new trackdao(context);
		dao.deletetrack(track_id);
	}
	public void updatetrackimage(String imageurl, int trackid,
			Context context) {
		// TODO Auto-generated method stub
		trackdao dao=new trackdao(context);
		dao.updatetrackimage(imageurl,trackid,context);		
	}
	public void deletelbs(Context context, double x, double y) {
		// TODO Auto-generated method stub
		trackdao dao=new trackdao(context);
		dao.deletelbs(x,y);
	}
	public List<trackbean> gettrackbyfir(Context context, int track_id) {
		// TODO Auto-generated method stub
		try {
			trackdao dao=new trackdao(context);
			lbsdao lbsdao=new lbsdao(context);
			Redservice redservice=new Redservice(context);
			List<trackbean> listtrackbean=new ArrayList<trackbean>();
			trackservice service=new trackservice(context);
			List<alltrack> list= dao.gettrackbyfir(context, track_id);
			list.add(new alltrack(100,11,track_id));
			for(alltrack all:list){
				List<trackbean> trackbean=new ArrayList<trackbean>();
				List<track> listtrack=dao.gettrackbysec(context, all.getSec());
				for(track track:listtrack){
					Log.i("trackid", track.getTrack_id()+"");
					List<redsbean> listredsbean=redservice.getredsbeanbytrackid(track.getTrack_id(), context);
			        List<lbs> listlbs=lbsdao.getlbsbytrackid(track.getTrack_id());
					trackbean.add(new trackbean(track,listredsbean,listlbs));
					listtrackbean.addAll(trackbean);
				}
			}
		
			
			
			
			return listtrackbean;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	public void addtrack(Context  context,int firtrack_id,int sectrack_id) {
		trackdao trackdao=new trackdao(context);
		trackdao.addtrack(firtrack_id,sectrack_id);
	}
	public void deletetrack(Context  context,int firtrack_id,int sectrack_id) {
		trackdao trackdao=new trackdao(context);
		trackdao.deletetrack(firtrack_id,sectrack_id);
	}
}
