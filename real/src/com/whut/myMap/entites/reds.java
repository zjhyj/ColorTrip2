package com.whut.myMap.entites;

import java.io.Serializable;
import java.util.Date;

import android.R.integer;
public class reds implements Serializable{
	private int reds_id;
	private int user_id;
    private String words;
    private double redsx;
    private double redsy;
    private int zan;
    private String date;
    private int track_id;
    private int type;
    private int intrack;
	public int  getReds_id() {
		return reds_id;
	}
	public void setReds_id(int reds_id) {
		this.reds_id = reds_id;
	}
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getWords() {
		return words;
	}
	public void setWords(String words) {
		this.words = words;
	}
	
	public double getRedsx() {
		return redsx;
	}
	public void setRedsx(double redsx) {
		this.redsx = redsx;
	}
	public double getRedsy() {
		return redsy;
	}
	public void setRedsy(double redsy) {
		this.redsy = redsy;
	}
	public int getZan() {
		return zan;
	}
	public void setZan(int zan) {
		this.zan = zan;
	}

	public int getTrack_id() {
		return track_id;
	}
	public void setTrack_id(int track_id) {
		this.track_id = track_id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public int getIntrack() {
		return intrack;
	}
	public void setIntrack(int intrack) {
		this.intrack = intrack;
	}

	public reds(int reds_id, int user_id, String words, double redsx,
			double redsy, int zan, String date, int track_id, int type,
			int intrack) {
		super();
		this.reds_id = reds_id;
		this.user_id = user_id;
		this.words = words;
		this.redsx = redsx;
		this.redsy = redsy;
		this.zan = zan;
		this.date = date;
		this.track_id = track_id;
		this.type = type;
		this.intrack = intrack;
	}
	public reds() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
