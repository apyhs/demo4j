package demo.common.jdk;

import java.io.Serializable;

public class ClonedUser implements Serializable,Cloneable {
	private String name;
	private String pass;
	private String gender;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
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

	public ClonedUser() {
		super();
	}

	public ClonedUser(String name, String pass, String gender) {
		super();
		this.name = name;
		this.pass = pass;
		setGender(gender);
	}

	@Override
	public ClonedUser clone() throws CloneNotSupportedException {
		return (ClonedUser)super.clone();
	}

	@Override
	public String toString() {
		return "ClonedUser{" +
				"name='" + name + '\'' +
				", pass='" + pass + '\'' +
				", gender='" + gender + '\'' +
				'}';
	}

}
