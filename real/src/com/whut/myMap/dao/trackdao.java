package com.whut.myMap.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;

import android.R.integer;
import android.content.ContentResolver;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;





import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.whut.myMap.db.sqliteopen;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.domain.trackbean;
import com.whut.myMap.entites.alltrack;
import com.whut.myMap.entites.lbs;
import com.whut.myMap.entites.picture;
import com.whut.myMap.entites.reds;
import com.whut.myMap.entites.track;
import com.whut.net.returnfans;


public class trackdao {
	  private sqliteopen myopen;
	
      public trackdao(Context context){
    	  myopen=new sqliteopen(context);
      }
  
	public trackdao() {
		super();
		// TODO Auto-generated constructor stub
		myopen=new sqliteopen(null);
	}
	public void updatewords(String words,int track_id){
		 SQLiteDatabase db= myopen.getWritableDatabase();
		if(db.isOpen()){
			String sql="update track set words=? where track_id=?";
			db.execSQL(sql, new Object[]{words,track_id});
		}
		db.close();
}		
	public void addtrack(track track){
		 SQLiteDatabase	db = myopen.getWritableDatabase();
			if(db.isOpen()){
				String sql="insert into track values (null,?,?,?,?,?,?,?,?,?,?)";
				db.execSQL(sql, new Object[]{track.getUser_id(),track.getWords(),track.getDate(),track.getType(),track.getZan(),track.getBgurl(),track.getLength(),track.getLocal(),0,null});				
			}
				db.close();
	}
	public int  getmaxid(){
		 SQLiteDatabase	db = myopen.getReadableDatabase();
		 Cursor c;
			if(db.isOpen()){
				try{
				String sql="select * from track where track_id=(select max(track_id) from track)";
				c=db.rawQuery(sql,null);
				if(c!=null&&c.getCount()>0){
					while(c.moveToNext()){
						Log.i("c", c.getInt(0)+"");
				return c.getInt(c.getColumnIndex("track_id"));}
					}
			}finally{
				db.close();
				}}else {
					return (Integer) null;
				}
			return 0;
	}
	public void deletetrack(int track_id){
		 SQLiteDatabase db= myopen.getWritableDatabase();
		if(db.isOpen()){
			try{
				db.beginTransaction();
			String sql1="delete from track where track_id=?";
			String sql2="delete from lbs where track_id=?";
			db.execSQL(sql1, new Object[]{track_id});
			db.execSQL(sql2);
			db.setTransactionSuccessful();
		}finally{
		db.endTransaction();
		}}
			db.close();	}
	
	public List<track> gettrackbyuser(int userid){
		 SQLiteDatabase db= myopen.getReadableDatabase();
		 if(db.isOpen()){
			 try {
			 Cursor	c ;
			 List<track> listtrack=new ArrayList<track>();
			 String sql ="select * from track where user_id=? order by track_id desc";
			 c=db.rawQuery(sql, new String[]{userid+""});
				 if(c!=null&&c.getCount()>0){
						while(c.moveToNext()){
							Log.i("trackid", c.getString(c.getColumnIndex("track_id")));
		    			String str=c.getString(c.getColumnIndex("date"));
						listtrack.add(new track(c.getInt(0),c.getInt(1),c.getString(2),str,c.getInt(4),c.getInt(5),c.getString(6),c.getDouble(7),c.getString(8),0,null));
						}
						db.close();
						c.close();
						return listtrack;	
						}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		} 
			 db.close();		 
		 }
		return null;
	}
	public track gettrack(int trackid){		 
		 SQLiteDatabase db= myopen.getReadableDatabase();
		 track track;
		 Cursor	c ;
		 if(db.isOpen()){
			 try {
//			 List<track> listtrack=new ArrayList<track>();
			 String sql ="select * from track where track_id=?";
			 c=db.rawQuery(sql, new String[]{trackid+""});
				 if(c!=null&&c.getCount()>0){
						while(c.moveToNext()){
		    			String str=c.getString(c.getColumnIndex("date"));
		    			track= new track(c.getInt(0),c.getInt(1),c.getString(2),str,c.getInt(4),c.getInt(5),c.getString(6),c.getDouble(7),c.getString(8),0,null);	
		    			c.close();
		    			return track;
						}
						}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}finally{
			db.close();
		} 	 
		 }
		return null;
	}
	public void deletetrack() {
		// TODO Auto-generated method stub
		SQLiteDatabase db= myopen.getWritableDatabase();
		if(db.isOpen()){
			try{
				db.beginTransaction();
			String sql1="delete from track ";
			String sql2="delete from lbs ";
			db.execSQL(sql1);
			db.execSQL(sql2);
			db.setTransactionSuccessful();
		}finally{
		db.endTransaction();
		}}
			db.close();
	}
	public void deletemaxtrack() {
		// TODO Auto-generated method stub
		SQLiteDatabase db= myopen.getWritableDatabase();
		if(db.isOpen()){
			try{
				db.beginTransaction();
			String sql1="delete from track where track_id=(select max(track_id) from track)";
			db.execSQL(sql1);
			db.setTransactionSuccessful();
		}finally{
		db.endTransaction();
		}}
			db.close();
	}

	public void updatetrackwords(String str, int trackid, Context context) {
		// TODO Auto-generated method stub
			SQLiteDatabase db=myopen.getWritableDatabase();
			if(db.isOpen()){
				try{
					String sql="update track set words=? where track_id=?";
					db.execSQL(sql,new Object[]{str,trackid});
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		
	}
	public void updatetrackimage(String imageurl, int trackid, Context context) {
		// TODO Auto-generated method stub
		SQLiteDatabase db=myopen.getWritableDatabase();
		if(db.isOpen()){
			try{
				String sql="update track set bgurl=? where track_id=?";
				db.execSQL(sql,new Object[]{imageurl,trackid});
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	
	}
	public void deletelbs(double x, double y) {
		// TODO Auto-generated method stub
		SQLiteDatabase db= myopen.getWritableDatabase();
		if(db.isOpen()){
			String sql2="delete from lbs where x=? and y=?";
			db.execSQL(sql2, new Object[]{x,y});
		}
			db.close();	
	}

	public List<alltrack> gettrackbyfir(Context context, int track_id) {
		// TODO Auto-generated method stub
		SQLiteDatabase db= myopen.getWritableDatabase();
		List<alltrack> listalltrack=new ArrayList<alltrack>();
		 Cursor	c ;
		if(db.isOpen()){
			try{
			 String sql ="select * from alltrack where firtrack_id=?";
			 c=db.rawQuery(sql, new String[]{track_id+""});
				 if(c!=null&&c.getCount()>0){
						while(c.moveToNext()){
						alltrack alltrack = new alltrack(c.getInt(0),c.getInt(1),c.getInt(2));	
		    			listalltrack.add(alltrack);
						}
						}
			    return listalltrack;
		}finally{
			db.close();
		}
			}
		return null;
	}

	public List<track> gettrackbysec(Context context, int track_id) {
		// TODO Auto-generated method stub
		 SQLiteDatabase db= myopen.getReadableDatabase();
		 if(db.isOpen()){
			 try {
			 Cursor	c ;
			 List<track> listtrack=new ArrayList<track>();
			 String sql ="select * from track where track_id=?";
			 c=db.rawQuery(sql, new String[]{track_id+""});
				 if(c!=null&&c.getCount()>0){
						while(c.moveToNext()){
							Log.i("trackid", c.getString(c.getColumnIndex("track_id")));
		    			String str=c.getString(c.getColumnIndex("date"));
						listtrack.add(new track(c.getInt(0),c.getInt(1),c.getString(2),str,c.getInt(4),c.getInt(5),c.getString(6),c.getDouble(7),c.getString(8),0,null));
						}
						db.close();
						c.close();
						return listtrack;	
						}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		} 
			 db.close();		 
		 }
		return null;
	}

	public void addtrack(int firtrack_id, int sectrack_id) {
		// TODO Auto-generated method stub
		SQLiteDatabase	db = myopen.getWritableDatabase();
		if(db.isOpen()){
			String sql="insert into alltrack values (null,?,?)";
			db.execSQL(sql, new Object[]{firtrack_id,sectrack_id});				
		}
			db.close();
	}

	public void deletetrack(int firtrack_id, int sectrack_id) {
		// TODO Auto-generated method stub
		SQLiteDatabase	db = myopen.getWritableDatabase();
		if(db.isOpen()){
			String sql="delete from alltrack where firtrack_id=? and sectrack_id=?";
			db.execSQL(sql, new Object[]{firtrack_id,sectrack_id});				
		}
			db.close();
	}
}

