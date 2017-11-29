package demo.entity;

import java.io.Serializable;

public class UserSmall implements Serializable,Cloneable {
	private String name;
	private String pass;
	private String gender;
	
	@SuppressWarnings("unused")
	private static String hello = "你好！";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@SuppressWarnings("unused")
	private String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getGender() {
		return gender;
	}

	private void setGender(String gender) {
		this.gender = gender;
	}
	
	public UserSmall() {
		super();
	}

	@SuppressWarnings("unused")
	private UserSmall(String gender) {
		super();
		setGender(gender);
	}

	public UserSmall(String name, String pass, String gender) {
		super();
		this.name = name;
		this.pass = pass;
		setGender(gender);
	}

	@Override
	public UserSmall clone() throws CloneNotSupportedException {
		return (UserSmall)super.clone();
	}

	@Override
	public String toString() {
		return "UserSmall [name=" + name + ", pass=" + pass + ", gender=" + gender + "]";
	}

}
