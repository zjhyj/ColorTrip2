package com.whut.myMap.entites;

import java.io.Serializable;
import java.util.Date;

public class track implements Serializable{
	private int track_id;
	private int user_id;
	private String words;
	private String date;
	private int  type;
	private int zan;
	private String bgurl;
	private   double  length;
	private String local;
	private int theme;
	private String musicstring;
	public String getBgurl() {
		return bgurl;
	}
	public void setBgurl(String bgurl) {
		this.bgurl = bgurl;
	}
	public  int getTrack_id() {
		return track_id;
	}
	public void setTrack_id(int track_id) {
		this.track_id = track_id;
	}
	public String getWords() {
		return words;
	}
	public void setWords(String words) {
		this.words = words;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public track() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public int getZan() {
		return zan;
	}
	public void setZan(int zan) {
		this.zan = zan;
	}
	
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	
	public String getMusicstring() {
		return musicstring;
	}
	public void setMusicstring(String musicstring) {
		this.musicstring = musicstring;
	}
	public int getTheme() {
		return theme;
	}
	public void setTheme(int theme) {
		this.theme = theme;
	}
	public track(int track_id, int user_id, String words, String date, int type, int zan, String bgurl, double length,
			String local, int theme, String musicstring) {
		super();
		this.track_id = track_id;
		this.user_id = user_id;
		this.words = words;
		this.date = date;
		this.type = type;
		this.zan = zan;
		this.bgurl = bgurl;
		this.length = length;
		this.local = local;
		this.theme = theme;
		this.musicstring = musicstring;
	}

}
