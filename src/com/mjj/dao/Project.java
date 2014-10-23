package com.mjj.dao;


import java.io.Serializable;
import java.sql.Timestamp;


public class Project implements Serializable{
	

	private static final long serialVersionUID = -5840111518748747629L;
	
	public Integer pk;
	public String name;
	public String comment;
	public Timestamp startTime;
	public Timestamp endTime;
	public Timestamp remindStartTime;
	public Timestamp remindEndTime;
	public String state;
	public String custom1;
	public String custom2;
	public String custom3;
	
	public Project() {

	}
	

	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public Timestamp getRemindStartTime() {
		return remindStartTime;
	}
	public void setRemindStartTime(Timestamp remindStartTime) {
		this.remindStartTime = remindStartTime;
	}
	public Timestamp getRemindEndTime() {
		return remindEndTime;
	}
	public void setRemindEndTime(Timestamp remindEndTime) {
		this.remindEndTime = remindEndTime;
	}
	public String getCustom1() {
		return custom1;
	}
	public void setCustom1(String custom1) {
		this.custom1 = custom1;
	}
	public String getCustom2() {
		return custom2;
	}
	public void setCustom2(String custom2) {
		this.custom2 = custom2;
	}
	public String getCustom3() {
		return custom3;
	}
	public void setCustom3(String custom3) {
		this.custom3 = custom3;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
