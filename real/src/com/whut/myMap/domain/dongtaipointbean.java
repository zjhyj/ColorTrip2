package com.whut.myMap.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class dongtaipointbean implements Serializable {

	private redsbean reds;
	
	public redsbean getReds() {
		return reds;
	}
	public void setReds(redsbean reds) {
		this.reds = reds;
	}

	public dongtaipointbean(redsbean reds) {
		super();
		this.reds = reds;
		
	}
	public dongtaipointbean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
