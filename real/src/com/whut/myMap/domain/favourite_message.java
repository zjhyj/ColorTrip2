package com.whut.myMap.domain;

import java.io.Serializable;

public class favourite_message implements Serializable{
  private int shoucang_id;
  private int user_id;
  private int dongtai_id;
  private int type;
public int getShoucang_id() {
	return shoucang_id;
}
public void setShoucang_id(int shoucang_id) {
	this.shoucang_id = shoucang_id;
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
public int getDongtai_id() {
	return dongtai_id;
}
public void setDongtai_id(int dongtai_id) {
	this.dongtai_id = dongtai_id;
}



}
