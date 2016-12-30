package com.whut.myMap.utils;



import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

public class NetUtil {
	/**
	 * 锟�?鏌ョ敤鎴风殑缃戠粶:鏄惁鏈夌綉锟�?
	 */
//	public static boolean checkNet(Context context) {
//		// 鍒ゆ柇锛歐IFI閾炬帴
//		boolean isWIFI = isWIFIConnection(context);
//		// 鍒ゆ柇锛歁obile閾炬帴
//		boolean isMOBILE = isMOBILEConnection(context);
//
//		// 濡傛灉Mobile鍦ㄩ摼鎺ワ紝鍒ゆ柇鏄摢涓狝PN琚拷?锟戒腑锟�?
//		if (isMOBILE) {
//			// APN琚拷?锟戒腑,鐨勪唬鐞嗕俊鎭槸鍚︽湁鍐呭锛屽鏋滄湁wap鏂瑰紡
//			readAPN(context);// 鍒ゆ柇鏄摢涓狝PN琚拷?锟戒腑锟�?
//		}
//
//		if (!isWIFI && !isMOBILE) {
//			return false;
//		}
//
//		return true;
//	}

	/**
	 * APN琚拷?锟戒腑,鐨勪唬鐞嗕俊鎭槸鍚︽湁鍐呭锛屽鏋滄湁wap鏂瑰紡
	 * 
	 * @param context
	 */
//	private static void readAPN(Context context) {
//		Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");//4.0妯℃嫙鍣ㄥ睆钄芥帀璇ユ潈锟�?
//
//		// 鎿嶄綔鑱旂郴浜虹被锟�?
//		ContentResolver resolver = context.getContentResolver();
//		// 鍒ゆ柇鏄摢涓狝PN琚拷?锟戒腑锟�?
//		Cursor cursor = resolver.query(PREFERRED_APN_URI, null, null, null, null);
//		
//		if(cursor!=null&&cursor.moveToFirst())
//		{
//			GlobalParams.PROXY=cursor.getString(cursor.getColumnIndex("proxy"));
//			GlobalParams.PORT=cursor.getInt(cursor.getColumnIndex("port"));
//		}
//		
//	}

	/**
	 * 鍒ゆ柇锛歁obile閾炬帴
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isMOBILEConnection(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}

	/**
	 * 鍒ゆ柇锛歐IFI閾炬帴
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isWIFIConnection(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}
}
