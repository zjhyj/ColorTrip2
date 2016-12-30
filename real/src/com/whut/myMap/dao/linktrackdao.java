package com.whut.myMap.dao;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.whut.myMap.db.sqliteopen;
import com.whut.myMap.entites.lbs2;

public class linktrackdao {
	private sqliteopen myopen;

     public linktrackdao(Context context) {
		// TODO Auto-generated constructor stub
  	  myopen=new sqliteopen(context);
    }
	public void  insertalltrack(int fir,int sec) {
		 SQLiteDatabase	db = myopen.getWritableDatabase();	 
		if(db.isOpen()){
			try{
			String sql="insert into alltrack values (null,?,?)";
			db.execSQL(sql, new Object[]{fir,sec});
		}finally{
			db.close();
		}
		}
			db.close();
	}
	public void deletelbs(int sec) {
		 SQLiteDatabase	db = myopen.getWritableDatabase();
		if(db.isOpen()){
			String sql="delete  from alltrack where sectrack_id=?";
			db.execSQL(sql,new Object[]{String.valueOf(sec)});
		}	
	}
}
