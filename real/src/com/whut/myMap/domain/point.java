package com.whut.myMap.domain;

import java.io.Serializable;

public class point implements Serializable{
   private int redsource_id;
   private int user_id;
   private int dongtai_id;
   private int track_id;
   private String words;
   private String redimageurl;
   private double x;
   private double y;
public int getRedsource_id() {
	return redsource_id;
}
public void setRedsource_id(int redsource_id) {
	this.redsource_id = redsource_id;
}
public int getUser_id() {
	return user_id;
}
public void setUser_id(int user_id) {
	this.user_id = user_id;
}
public int getDongtai_id() {
	return dongtai_id;
}
public void setDongtai_id(int dongtai_id) {
	this.dongtai_id = dongtai_id;
}

public int getTrack_id() {
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
public String getRedimageurl() {
	return redimageurl;
}
public void setRedimageurl(String redimageurl) {
	this.redimageurl = redimageurl;
}
public double getX() {
	return x;
}
public void setX(double x) {
	this.x = x;
}
public double getY() {
	return y;
}
public void setY(double y) {
	this.y = y;
}
}