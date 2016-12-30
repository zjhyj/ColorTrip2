package com.whut.myMap.entites;

import java.io.Serializable;

public class user implements Serializable {
	private int user_id;
	private String username;
	private String gender;
	private String  phone;
	private String emial;
	private String userimage;
	private String personal;
	private String password;
	private int age;
	private String Clientid;
	private double score;
	public String getClientid() {
		return Clientid;
	}
	public void setClientid(String clientid) {
		Clientid = clientid;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmial() {
		return emial;
	}
	public void setEmial(String emial) {
		this.emial = emial;
	}
	public String getUserimage() {
		return userimage;
	}
	public void setUserimage(String userimage) {
		this.userimage = userimage;
	}
	public String getPersonal() {
		return personal;
	}
	public void setPersonal(String personal) {
		this.personal = personal;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public user(int user_id, String username, String gender, String phone,
			String emial, String userimage, String personal, String password,
			int age, String clientid, double score) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.gender = gender;
		this.phone = phone;
		this.emial = emial;
		this.userimage = userimage;
		this.personal = personal;
		this.password = password;
		this.age = age;
		Clientid = clientid;
		this.score = score;
	}
	public user() {
		super();
		// TODO Auto-generated constructor stub
	}

}
