package com.whut.myMap.domain;

import java.io.Serializable;
import java.sql.Date;

public class TestRoute implements Serializable {
	private String words;
	private Date date;
	private String image;
	public String getWords() {
		return words;
	}
	public void setWords(String words) {
		this.words = words;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	
}
