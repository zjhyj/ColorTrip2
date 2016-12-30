package com.whut.myMap.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.whut.myMap.domain.redsbean;

public class gsonutil {
	
	 public gsonutil() {
		super();
		// TODO Auto-generated constructor stub
	}
	public static String createJsonString(Object value)
	    {
	        Gson gson = new Gson();
	        String str = gson.toJson(value);
	        return str;
	    }
	 public static <T> T getobject(String jsonString, Class<T> cls) {
	        T t = null;
	        try {
	            Gson gson = new Gson();
	            t = gson.fromJson(jsonString, cls);
	        } catch (Exception e) {
	            // TODO: handle exception
	        }
	        return t;
	    }
	 public <T> List<T> fromJsonList(String json, Class<T> cls) {  
	        List<T> mList =  new ArrayList<T>();  
	        JsonArray array = new JsonParser().parse(json).getAsJsonArray();  
	        for(final JsonElement elem : array){ 
	        	Gson gson = new Gson();
	            mList.add(gson.fromJson(elem, cls));  
	        } 
	        return  mList;  
	    }  
	public static <T> List<T> getlistobject(String jsonString, Class<T> cls) {
	        		ArrayList<T> list = new ArrayList<T>();
	        try {
	            Gson gson = new Gson();
	            list = gson.fromJson(jsonString, new TypeToken<ArrayList<T>>() {
	            }.getType());
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	throw new RuntimeException(e);
	        }
	        return list;
	    }
	public static List<Map<String, Object>> listmapobject(String jsonString) {
	        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	        try {
	            Gson gson = new Gson();
	            list = gson.fromJson(jsonString,
	                    new TypeToken<List<Map<String, Object>>>() {
	                    }.getType());
	        } catch (Exception e) {
	            // TODO: handle exception
	        }
	        return list;
	    }
	
	

}
