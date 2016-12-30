package com.whut.myMap.domain;

import java.io.Serializable;
import java.util.List;

import com.whut.myMap.entites.lbs;

public class listtracklbs implements Serializable {
    private  List<lbs> listlbs;

	public List<lbs> getListlbs() {
		return listlbs;
	}

	public void setListlbs(List<lbs> listlbs) {
		this.listlbs = listlbs;
	}

	public listtracklbs(List<lbs> listlbs) {
		super();
		this.listlbs = listlbs;
	}

	public listtracklbs() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
