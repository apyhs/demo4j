package com.apyhs.demo.jdbc;

public class User {
	private Long id;
	private String user_name;
	private String password;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserSmall [id=" + id + ", user_name=" + user_name + ", password=" + password + ", name=" + name + "]";
	}
}
