package com.whut.myMap.entites;

import android.os.Environment;
public class url {
	private String url="http://172.18.22.253:8080/zjh2";
	public String geturl(){
		return url;
	}
	public final static String SPUSER_ID="user_id";
	public final static String SPUSER_NAME="username";
	public final static String SPUSER_IMAGE="userimage";
	public final static String SPUSER_PERSONAL="personal";
	public final static String SPUSER_CLIENTID="clientid";
	public final static String SPUSER_MEDAL="medal";
	public static String SDPATH = Environment.getExternalStorageDirectory()
			+ "/Myimage/";
	 public static boolean externalMemoryAvailable() {
		 System.out.println(android.os.Environment.getExternalStorageState().equals(
	                android.os.Environment.MEDIA_MOUNTED));
	        return android.os.Environment.getExternalStorageState().equals(
	                android.os.Environment.MEDIA_MOUNTED);
	    }
}
