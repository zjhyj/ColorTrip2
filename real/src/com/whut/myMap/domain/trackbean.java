package com.whut.myMap.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.whut.myMap.entites.track;
import com.whut.myMap.entites.lbs;
public class trackbean implements Serializable {
	private track track;
	private List<redsbean> redsbean;
	private List<lbs> lbsbean;
	 private String username;
	 private String url;

	public track getTrack() {
		return track;
	}
	public void setTrack(track track) {
		this.track = track;
	}
	public List<redsbean> getRedsbean() {
		return redsbean;
	}
	public void setRedsbean(List<redsbean> redsbean) {
		this.redsbean = redsbean;
	}
	public List<lbs> getLbsbean() {
		return lbsbean;
	}
	public void setLbsbean(List<lbs> lbsbean) {
		this.lbsbean = lbsbean;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public trackbean(track track,List<redsbean> redsbean, List<lbs> lbsbean,String username, String url) {
		super();
		this.track = track;
		this.redsbean = redsbean;
		this.lbsbean = lbsbean;
		this.username = username;
		this.url = url;
	}
	public trackbean(track track,
			List<redsbean> redsbean, List<lbs> lbsbean) {
		super();
		this.track = track;
		this.redsbean = redsbean;
		this.lbsbean = lbsbean;
	}
	public trackbean() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "trackbean [listtrack=" + track + ", redsbean=" + redsbean
				+ ", lbsbean=" + lbsbean + "]";
	}
	
}
