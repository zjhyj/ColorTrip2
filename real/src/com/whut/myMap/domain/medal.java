package com.whut.myMap.domain;

import java.io.Serializable;

public class medal implements Serializable{
     private int tracknum;
     private int concernnum;
     private int commnum;
     private int localnum;
     private double sumlength;
     private int scorenum;
     private int maxzannum;
     private int sumzannum;
     private int shoucangnum;
	public int getTracknum() {
		return tracknum;
	}
	public void setTracknum(int tracknum) {
		this.tracknum = tracknum;
	}
	public int getConcernnum() {
		return concernnum;
	}
	public void setConcernnum(int concernnum) {
		this.concernnum = concernnum;
	}
	public int getCommnum() {
		return commnum;
	}
	public void setCommnum(int commnum) {
		this.commnum = commnum;
	}
	public int getLocalnum() {
		return localnum;
	}
	public void setLocalnum(int localnum) {
		this.localnum = localnum;
	}
	public double getSumlength() {
		return sumlength;
	}
	public void setSumlength(double sumlength) {
		this.sumlength = sumlength;
	}
	public int getScorenum() {
		return scorenum;
	}
	public void setScorenum(int scorenum) {
		this.scorenum = scorenum;
	}
	public int getMaxzannum() {
		return maxzannum;
	}
	public void setMaxzannum(int maxzannum) {
		this.maxzannum = maxzannum;
	}
	public int getSumzannum() {
		return sumzannum;
	}
	public void setSumzannum(int sumzannum) {
		this.sumzannum = sumzannum;
	}
	public int getShoucangnum() {
		return shoucangnum;
	}
	public void setShoucangnum(int shoucangnum) {
		this.shoucangnum = shoucangnum;
	}
	public medal(int tracknum, int concernnum, int commnum, int localnum,
			double sumlength, int scorenum, int maxzannum, int sumzannum,
			int shoucangnum) {
		super();
		this.tracknum = tracknum;
		this.concernnum = concernnum;
		this.commnum = commnum;
		this.localnum = localnum;
		this.sumlength = sumlength;
		this.scorenum = scorenum;
		this.maxzannum = maxzannum;
		this.sumzannum = sumzannum;
		this.shoucangnum = shoucangnum;
	}
	public medal() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
