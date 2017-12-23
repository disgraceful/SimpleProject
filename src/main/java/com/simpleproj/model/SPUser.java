package com.simpleproj.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SP_USERS")
public class SPUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userid")
	private long id;
	private String login;
	private String password;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = { CascadeType.ALL })
	private List<SPTask> tasks = new ArrayList<>();
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = { CascadeType.ALL })
	private List<SPProject> projects = new ArrayList<>();

	public SPUser() {
	}

	public SPUser(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<SPTask> getTasks() {
		return projects.stream().map(e->e.getTasks()).flatMap(List::stream).collect(Collectors.toList());
	}

	public void setTasks(List<SPTask> tasks) {
		this.tasks = tasks;
	}

	public List<SPProject> getProjects() {
		return projects;
	}

	public void setProjects(List<SPProject> projects) {
		this.projects = projects;
	}
	
	public void addProject(SPProject project) {
		projects.add(project);
	}
	
	public void deleteProject(SPProject project) {
		projects.remove(project);
	}
	
}
