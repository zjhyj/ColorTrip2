package com.whut.myMap.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whut.myMap.db.sqliteopen;
import com.whut.myMap.entites.lbs;
import com.whut.myMap.entites.lbs2;
import com.whut.myMap.entites.reds;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class lbsdao {
	private sqliteopen myopen;

      public lbsdao(Context context){
    	  myopen=new sqliteopen(context);
      }
	public void  insertlbs(List<lbs2> lat2) {
		 SQLiteDatabase	db = myopen.getWritableDatabase();	 
		if(db.isOpen()){
			try{
			db.beginTransaction();
			for(lbs2 lbs :lat2){
				Log.i("iscontain", lbs.getIscontain()+"");
			String sql="insert into lbs(lbs_id,x,y,track_id,iscontain,data) values (null,?,?,?,?,?)";
			db.execSQL(sql, new Object[]{lbs.getLbsx(),lbs.getLbsy(),lbs.getTrack_id(),lbs.getIscontain(),lbs.getDate()});
			}
			db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
		}
			db.close();
	}
	public void insert(){
		SQLiteDatabase	db = myopen.getWritableDatabase();
		if(db.isOpen()){	
			String sql="insert into lbs(lbs_id,x,y,track_id) values (null,222.33,3333.22,2)";
			db.execSQL(sql);
		}
			db.close();
	}
	public void deletelbs(int track_id) {
		 SQLiteDatabase	db = myopen.getWritableDatabase();
		if(db.isOpen()){
			String sql="delete  from lbs where track_id=?";
			db.execSQL(sql,new Object[]{String.valueOf(track_id)});
		}	
	}
	public List<lbs> getlbsbytrackid(int track_id) {
		// TODO Auto-generated method stub
		SQLiteDatabase db=myopen.getReadableDatabase();
		if(db.isOpen()){
			Cursor	c;
			List<lbs> listlbs=new ArrayList<lbs>();
	    		String sql="select lbs_id,x,y,track_id,iscontain from lbs where track_id=? order by data";
	    		c=db.rawQuery(sql, new String[]{track_id+""});
	    		if(c!=null&&c.getCount()>0){
	    			while(c.moveToNext()){
						listlbs.add(new lbs(c.getInt(0),c.getDouble(1),c.getDouble(2),track_id,c.getInt(4)));
						Log.i("lbsdao2", c.getDouble(2)+"  "+c.getDouble(1));
	    			}
	    			db.close();
	    			c.close();
	    			return listlbs;
	    			}
	    		Log.i("lbsdao", 0+"");
			}
			return null;	
	    }
	public List<lbs> getallbs(int i) {
		// TODO Auto-generated method stub
		SQLiteDatabase db=myopen.getReadableDatabase();
		if(db.isOpen()){
			Cursor	c;
			List<lbs> listlbs=new ArrayList<lbs>();
	    		String sql="select lbs_id,x,y,track_id,iscontain from lbs where track_id in(select sectrack_id from alltrack where firtrack_id=?) or   track_id=?  order by data";
	    		c=db.rawQuery(sql,  new String[]{i+"",i+""});
	    		if(c!=null&&c.getCount()>0){
	    			while(c.moveToNext()){
						listlbs.add(new lbs(c.getInt(0),c.getDouble(1),c.getDouble(2),c.getInt(3),c.getInt(4)));
						//Log.i("lbsdao2", c.getDouble(2)+"  "+c.getDouble(1));
	    			}
	    			db.close();
	    			c.close();
	    			return listlbs;
	    			}
	    		Log.i("lbsdao", 0+"");
			}
			return null;	
	}	
}
