package demo.pojo;

public class User1 {
	private String name;
	private String pass;
	private Boolean gender;
	private Integer age;

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
	
	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public User1() {
		super();
	}

	public User1(String name, String pass, Boolean gender, Integer age) {
		super();
		this.name = name;
		this.pass = pass;
		this.gender = gender;
		this.age = age;
	}

	@Override
	public String toString() {
		return "User1 [name=" + name + ", pass=" + pass + ", gender=" + gender + ", age=" + age + "]";
	}

}
