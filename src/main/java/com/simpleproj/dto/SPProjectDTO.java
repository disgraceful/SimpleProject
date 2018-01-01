package com.simpleproj.dto;

public class SPProjectDTO {
	private long id;
	private String name;
	private int taskAmount;
	private long userId;

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

	public int getTaskAmount() {
		return taskAmount;
	}

	public void setTaskAmount(int taskAmount) {
		this.taskAmount = taskAmount;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
