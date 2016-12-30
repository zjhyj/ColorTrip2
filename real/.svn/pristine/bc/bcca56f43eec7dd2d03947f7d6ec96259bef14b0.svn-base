package com.whut.myMap.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.baidu.pano.platform.http.c;
import com.whut.myMap.db.sqliteopen;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.entites.picture;
import com.whut.myMap.entites.reds;

public class picturedao {
	  private sqliteopen myopen;
     
      public picturedao(Context context){
    	  myopen=new sqliteopen(context);
      }
	public void  insertpicture(List<picture> list,int reds_id) {
		 SQLiteDatabase db= myopen.getWritableDatabase();
		if(db.isOpen()){
			try{
			db.beginTransaction();
			for(picture picture:list){
			String sql="insert into picture(picture_id,reds_id,imgurl) values (null,?,?)";
			db.execSQL(sql, new Object[]{reds_id,picture.getImgurl()});
			}
			db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
			db.close();
			}
			
	}
		public void deletepicture(int reds_id){
			 SQLiteDatabase	 db= myopen.getWritableDatabase();
			if(db.isOpen()){
				String sql="delete from picture where reds_id=?";
				db.execSQL(sql, new Object[]{reds_id});
			}
				db.close();		
		}
		public List<picture> getpictures(int  reds_id){
			 SQLiteDatabase	db=myopen.getReadableDatabase();
		
			int pictureid;
			int redsid;
			String imgurl;
			List<picture> listp=new ArrayList<picture>();
			if(db.isOpen()){
				String sql="select * from picture where reds_id=?";
			Cursor	c=db.rawQuery(sql, new String[]{String.valueOf(reds_id)});
			if(c!=null&&c.getCount()>0){
				while(c.moveToNext()){
				 pictureid=c.getInt(0);
			     redsid=c.getInt(1);
				 imgurl=c.getString(2);
				listp.add(new picture(pictureid, redsid, imgurl));
				}
				db.close();
				c.close();
				return listp;
				}
			db.close();	
			}			
		return null;	
	}

		@SuppressWarnings("finally")
		public List<redsbean> getpictures(List<reds> listreds) {
			// TODO Auto-generated method stub
			 SQLiteDatabase db=myopen.getReadableDatabase();
			 Cursor c=null;
			 int pictureid;
			int redsid;
			String imgurl;
			List<redsbean> listbean=new ArrayList<redsbean>();
				if(db.isOpen()){
					try{
					db.beginTransaction();
					for(reds reds:listreds){		
			List<picture> listp=new ArrayList<picture>();
					String sql="select * from picture where reds_id=?";
				    c=db.rawQuery(sql, new String[]{String.valueOf(reds.getReds_id())});
				    if(c!=null&&c.getCount()>0){
				    	int i=2;
						while(c.moveToNext()){		
						 pictureid=c.getInt(0);
					     redsid=c.getInt(1);
						 imgurl=c.getString(2);
						listp.add(new picture(pictureid, redsid, imgurl));
						i++;
						}
						Log.i("i", i+"");
						redsbean redsbean=new redsbean(reds,listp);
						listbean.add(redsbean );
						Log.i("listbean", redsbean.getListmap().size()+"");
						Log.i("listbean", redsbean.getReds().getReds_id()+"");
				}else{
					listbean.add(new redsbean(reds,null));
				}  
				    }
					db.setTransactionSuccessful();
					}finally{
						db.endTransaction();	
						db.close();	
						c.close();	
						Log.i("number", listbean.size()+"");
						return listbean;
					}	
		}
		db.close();
		return null;
		}
}