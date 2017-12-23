package com.simpleproj.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "SP_TASKS")
public class SPTask {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	@Column(name="startdate")
	private Calendar startDate;
	@Column(name="iscompleted")
	private boolean isCompleted;
	
	@ManyToOne
    @JoinColumn(name = "userid")
	private SPUser user;
	@ManyToOne
    @JoinColumn(name = "projectid",nullable=true)
	private SPProject project;

	public SPTask() {// NOSONAR

	}

	public SPTask(String name, Calendar start) {
		this.name = name;
		this.startDate = start;
	}

	public SPTask(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Date getTaskTime() {
		return startDate.getTime();
	}

	public void setTaskTime(Date date) {
		startDate.setTime(date);
	}

	public SPUser getUser() {
		return user;
	}

	public void setUser(SPUser user) {
		this.user = user;
	}

	public SPProject getProject() {
		return project;
	}

	public void setProject(SPProject project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "Id " + id + " name " + name + " startDate " + startDate.getTime() + " isCompleted " + isCompleted;
	}
}
