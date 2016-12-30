package com.whut.myMap.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class sqliteopen extends SQLiteOpenHelper {

	public sqliteopen(Context context) {
		super(context, "zjh.db", null, 2);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql1="create table reds(reds_id INTEGER PRIMARY KEY AUTOINCREMENT,user_id int,  words  VARCHAR(1000),  x  DOUBLE,y DOUBLE,zan INT , date String,track_id int,type bit,intrack bit);";
		String sql2="create table picture( picture_id INTEGER PRIMARY KEY AUTOINCREMENT, reds_id int, imgurl   varchar(100));";
		String sql3="create table lbs( lbs_id INTEGER PRIMARY KEY AUTOINCREMENT, x double, y double, track_id int,iscontain int ,data date );";	
		String sql4="create table track( track_id INTEGER PRIMARY KEY AUTOINCREMENT,user_id int ,words  varchar(300), date String,type bit,zan int,bgurl varchar(100) ,length int,local  varchar(100),theme int,musicstring varchar(300));";
        String sql5="create table zan(zan_id INTEGER PRIMARY KEY AUTOINCREMENT,source_id int,type it,user_id int );";
        String sql6="create table shoucang(shoucang_id INTEGER PRIMARY KEY AUTOINCREMENT,source_id int ,type int,user_id int );";
        String sql7="create table alltrack(alltrack_id INTEGER PRIMARY KEY AUTOINCREMENT,firtrack_id int ,sectrack_id int );";
		db.execSQL(sql7);
        db.execSQL(sql1);
        db.execSQL(sql2);
		db.execSQL(sql3);
		db.execSQL(sql4); 
		db.execSQL(sql5);
		db.execSQL(sql6);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
//		String sql7="create table alltrack(alltrack_id INTEGER PRIMARY KEY AUTOINCREMENT,firtrack_id int ,sectrack_id int );";
//		db.execSQL(sql7);
//		Log.w("log_tag","Upgrading database frome version "+oldVersion+"to"+newVersion+",which destroy all old data");   
	}
}