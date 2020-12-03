package JZ.com.ws.entity;

public class User {
	private String name;
	private String phone;
	private String pwd;
	private int height;
	private int weight;
	private int age;
	
	public User() {
		super();
	}

	public User(String name, String phone, String pwd, int height, int weight, int age) {
		super();
		this.name = name;
		this.phone = phone;
		this.pwd = pwd;
		this.height = height;
		this.weight = weight;
		this.age = age;
	}

	public User(String phone, String pwd) {
		super();
		this.phone = phone;
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", phone=" + phone + ", pwd=" + pwd + ", height=" + height + ", weight=" + weight
				+ ", age=" + age + "]";
	}
	
	
	
}
