package demo.pojo;

public class User {
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
	
	public User() {
		super();
	}

	@SuppressWarnings("unused")
	private User(String gender) {
		super();
		setGender(gender);
	}

	public User(String name, String pass, String gender) {
		super();
		this.name = name;
		this.pass = pass;
		setGender(gender);
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", pass=" + pass + ", gender=" + gender + "]";
	}

}
