package com.whut.myMap.entites;

import java.io.Serializable;

public class zaned implements Serializable {
private int zan_id;
private int source_id;
private int type;
public int getZan_id() {
	return zan_id;
}
public void setZan_id(int zan_id) {
	this.zan_id = zan_id;
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
public zaned(int zan_id, int source_id, int type) {
	super();
	this.zan_id = zan_id;
	this.source_id = source_id;
	this.type = type;
}
public zaned() {
	super();
	// TODO Auto-generated constructor stub
}

}
