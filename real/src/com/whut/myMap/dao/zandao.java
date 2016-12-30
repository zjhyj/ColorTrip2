package com.whut.myMap.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.whut.myMap.db.sqliteopen;
import com.whut.myMap.entites.lbs;

public class zandao {
	private sqliteopen myopen;
    public zandao(Context context){
  	  myopen=new sqliteopen(context);
    }
	public boolean findzaned(int sourceid, int type, int userid) {
		// TODO Auto-generated method stub
		SQLiteDatabase	db = myopen.getWritableDatabase();	 
		if(db.isOpen()){
			try{
				Cursor	c;
			String sql="select * from zan where source_id=? and type=? and user_id=?";
			c=db.rawQuery(sql,  new String[]{sourceid+"",type+"",userid+""});
			if(c!=null&&c.getCount()>0){
				return true;
			}else{
				return false;
			}	
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			db.close();
		}
        }
		return false;
	}
	public void addzaned(int sourceid, int type, int userid) {
		// TODO Auto-generated method stub
		SQLiteDatabase	db = myopen.getWritableDatabase();
		if(db.isOpen()){	
			String sql="insert into zan(zan_id,source_id,type,user_id) values (null,?,?,?)";
			db.execSQL(sql,new String[]{sourceid+"",type+"",userid+""});
		}
			db.close();
	}
	public void deletezaned(int sourceid, int type, int userid) {
		// TODO Auto-generated method stub
		SQLiteDatabase	db = myopen.getWritableDatabase();
		if(db.isOpen()){
			String sql="delete  from zan where source_id=? and type=? and user_id=?";
			db.execSQL(sql,new String[]{sourceid+"",type+"",userid+""});
		}	
	}
	
	
}
