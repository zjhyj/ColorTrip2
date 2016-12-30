package com.whut.myMap.entites;

import java.io.Serializable;

public class alltrack implements Serializable {
    private int alltrack_id;
    private int fir;
    private int sec;
	public int getAlltrack_id() {
		return alltrack_id;
	}
	public void setAlltrack_id(int alltrack_id) {
		this.alltrack_id = alltrack_id;
	}
	public int getFir() {
		return fir;
	}
	public void setFir(int fir) {
		this.fir = fir;
	}
	public int getSec() {
		return sec;
	}
	public void setSec(int sec) {
		this.sec = sec;
	}
	public alltrack(int alltrack_id, int fir, int sec) {
		super();
		this.alltrack_id = alltrack_id;
		this.fir = fir;
		this.sec = sec;
	}
	public alltrack() {
		super();
		// TODO Auto-generated constructor stub
	}
    
	
	
}
