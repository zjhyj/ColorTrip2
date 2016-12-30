package com.whut.myMap.entites;

public class shoucanged {
	 private int shoucang_id;
	 private int source_id;
	 private int type;
	public int getShoucang_id() {
		return shoucang_id;
	}
	public void setShoucang_id(int shoucang_id) {
		this.shoucang_id = shoucang_id;
	}
	public int getSource_id() {
		return source_id;
	}
	public void setSource_id(int source_id) {
		this.source_id = source_id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public shoucanged(int shoucang_id, int source_id, int type) {
		super();
		this.shoucang_id = shoucang_id;
		this.source_id = source_id;
		this.type = type;
	}
	public shoucanged() {
		super();
		// TODO Auto-generated constructor stub
	}
	  
		
}
