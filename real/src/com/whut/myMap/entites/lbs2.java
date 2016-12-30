package com.whut.myMap.entites;

import java.io.Serializable;

public class lbs2 implements Serializable{
	private int lbs_id;
	private double lbsx;
	private double lbsy;
	private int track_id;
	private int iscontain;
	private String date;
	public int getLbs_id() {
		return lbs_id;
	}
	public void setLbs_id(int lbs_id) {
		this.lbs_id = lbs_id;
	}
	public double getLbsx() {
		return lbsx;
	}
	public void setLbsx(double lbsx) {
		this.lbsx = lbsx;
	}
	public double getLbsy() {
		return lbsy;
	}
	public void setLbsy(double lbsy) {
		this.lbsy = lbsy;
	}
	public int getTrack_id() {
		return track_id;
	}
	public void setTrack_id(int track_id) {
		this.track_id = track_id;
	}
	public int getIscontain() {
		return iscontain;
	}
	public void setIscontain(int iscontain) {
		this.iscontain = iscontain;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public lbs2() {
		super();
		// TODO Auto-generated constructor stub
	}
	public lbs2(int lbs_id, double lbsx, double lbsy, int track_id,
			int iscontain, String date) {
		super();
		this.lbs_id = lbs_id;
		this.lbsx = lbsx;
		this.lbsy = lbsy;
		this.track_id = track_id;
		this.iscontain = iscontain;
		this.date = date;
	}
	
}
