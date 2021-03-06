package com.whut.myMap.dao;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.StaticLayout;

import com.whut.myMap.db.sqliteopen;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.entites.picture;
import com.whut.myMap.entites.reds;
public class redsdao {
	private sqliteopen myopen;
     
     public redsdao(Context context){
   	  myopen=new sqliteopen(context);
     }
	public void insertreds(redsbean redsbean){
		SQLiteDatabase  db= myopen.getWritableDatabase();
		if(db.isOpen()){
			String sql="insert into reds(reds_id,user_id,words,x,y,zan,date,track_id,type,intrack) values (null,?,?,?,?,0,?,?,?,?)";
			db.execSQL(sql, new Object[]{redsbean.getReds().getUser_id(),redsbean.getReds().getWords(),redsbean.getReds().getRedsx(),redsbean.getReds().getRedsy(),redsbean.getReds().getDate(),redsbean.getReds().getTrack_id(),redsbean.getReds().getType(),redsbean.getReds().getIntrack()});		    
		}
			db.close();
	}
	public int  getmaxid(){
		 SQLiteDatabase	db = myopen.getReadableDatabase();
		 Cursor c=null;
			if(db.isOpen()){
				String sql="select * from reds where reds_id=(select max(reds_id) from reds)";
				c=db.rawQuery(sql,null);
				if(c.moveToNext()){
					db.close();
				return c.getInt(0);
				}
			}
				db.close();
				return (Integer) null;
	}
	public void deletereds(int redsid){
		SQLiteDatabase	 db= myopen.getWritableDatabase();
	 if(db.isOpen()){
			String sql="delete from reds where reds_id=?";
			db.execSQL(sql, new Object[]{redsid});
		}
			db.close();	
	}
	public List<reds> getredsbyuser(int userid){
		SQLiteDatabase	db=myopen.getReadableDatabase();
    	if(db.isOpen()){ 
    		try {
    			Cursor c;
       		 List<reds> listreds =new ArrayList<reds>() ;
       		String sql="select * from reds where user_id=? order by reds_id desc";
       		c=db.rawQuery(sql, new String[]{userid+""});
       		if(c!=null&&c.getCount()>0){
       			while(c.moveToNext()){
       				String str=c.getString(c.getColumnIndex("date"));
       				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   					listreds.add(new reds(c.getInt(0),c.getInt(1),c.getString(2),c.getDouble(3),c.getDouble(4),c.getInt(5),str,c.getInt(7),c.getInt(8),c.getInt(9)));
       			}
       			db.close();
       			c.close();
       			return listreds;
       		}
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}
    		db.close();
    		}
		return null;	
    }
	public List<reds> getreds(int trackid){
		SQLiteDatabase	db=myopen.getReadableDatabase();
    	if(db.isOpen()){ 
    		try {
    			Cursor c;
       		 List<reds> listreds =new ArrayList<reds>() ;
       		String sql="select * from reds where track_id=?";
       		c=db.rawQuery(sql, new String[]{trackid+""});
       		if(c!=null&&c.getCount()>0){
       			while(c.moveToNext()){
       				Date  date =null;
       				String str=c.getString(c.getColumnIndex("date"));
       				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       				try {
       				 	date = (Date) format.parse(str); 	
       				} catch (ParseException e) {
       					// TODO Auto-generated catch block
       					e.printStackTrace();
       				}
   				listreds.add(new reds(c.getInt(0),c.getInt(1),c.getString(2),c.getDouble(3),c.getDouble(4),c.getInt(5),str,c.getInt(7),c.getInt(8),c.getInt(9)));
       			}
       			db.close();
       			c.close();
       			return listreds;
       		}
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}
    		db.close();
    		}
		return null;	
    }
    public void updatewords(int redsid,String words) {
    	SQLiteDatabase	db=myopen.getWritableDatabase();
		if(db.isOpen()){
		
			
			String sql="update  reds set words=? where reds_id=?";
		
			db.execSQL(sql, new Object[]{words,redsid});
		
			db.close();	
		}
	}
    public void updatetrackid(int trackid,int redsid){
    	SQLiteDatabase	db=myopen.getWritableDatabase();
		if(db.isOpen()){
			try {
				db.beginTransaction();
				String sql="update  reds set track_id=? where reds_id=?";
				String sql1 ="update reds set intrack=1 where reds_id=? ";
				db.execSQL(sql, new Object[]{trackid,redsid});
				db.execSQL(sql1, new Object[]{redsid});
				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				db.endTransaction();
			}
			db.close();	
		}
    }
	public void seleteredsbyid(int reds_id) {
		// TODO Auto-generated method stub
		SQLiteDatabase db=myopen.getWritableDatabase();
		if(db.isOpen()){
			try {
				db.beginTransaction();
				String sql="delete from reds where reds_id=?";
				String sql2="delete from picture where  reds_id=?";
			db.execSQL(sql,new Object[]{reds_id});
			db.execSQL(sql2,new Object[]{reds_id});
			db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				db.endTransaction();
			}
		}
		db.close();
	}	
}
