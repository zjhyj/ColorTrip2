package com.whut.myMap.domain;

import java.io.Serializable;

public class redslbs implements Serializable {
  private double redsx;
  private double redsy;
public double getRedsx() {
	return redsx;
}
public void setRedsx(double redsx) {
	this.redsx = redsx;
}
public double getRedsy() {
	return redsy;
}
public void setRedsy(double redsy) {
	this.redsy = redsy;
}
public redslbs(double redsx, double redsy) {
	super();
	this.redsx = redsx;
	this.redsy = redsy;
}
public redslbs() {
	super();
	// TODO Auto-generated constructor stub
}
  
}
