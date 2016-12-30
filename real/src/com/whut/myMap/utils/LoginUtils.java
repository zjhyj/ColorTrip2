package com.whut.myMap.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

public class LoginUtils {
	public static boolean saveUserInfo(Context context, String number,
			String password, Boolean check) {

		try {
			SharedPreferences sp = context.getSharedPreferences("log",
					Context.MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putString("password", password);
			editor.putString("number", number);
			editor.putBoolean("check", check);
			editor.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static Map<String, String> getUserInfo(Context context) {

		try {
			SharedPreferences sp = context.getSharedPreferences("log",
					Context.MODE_PRIVATE);
			String number = sp.getString("number", null);
			String password = sp.getString("password", null);
			if (!TextUtils.isEmpty(number) && (!TextUtils.isEmpty(password))) {
				Map<String, String> uerInfoMap = new HashMap<String, String>();
				uerInfoMap.put("number", number);
				uerInfoMap.put("password", password);
				return uerInfoMap;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
