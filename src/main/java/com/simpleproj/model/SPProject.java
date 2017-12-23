package com.simpleproj.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SP_PROJECTS")
public class SPProject {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "projectid")
	private long id;
	private String name;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "project", cascade = { CascadeType.ALL })
	private List<SPTask> tasks = new ArrayList<>();
	@ManyToOne
	@JoinColumn(name = "userid")
	private SPUser user;

	public SPProject() {
	}

	public SPProject(String name) {
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

	public List<SPTask> getTasks() {
		return tasks;
	}

	public void setTasks(List<SPTask> tasks) {
		this.tasks = tasks;
	}

	public void addTask(SPTask task) {
		tasks.add(task);
	}

	public void deleteTask(SPTask task) {
		tasks.remove(task);
	}

	public SPUser getUser() {
		return user;
	}

	public void setUser(SPUser user) {
		this.user = user;
	}
}
