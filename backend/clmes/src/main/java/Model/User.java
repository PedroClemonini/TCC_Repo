package Model;

public class User {
	private Integer id;
	private String name;
	private String email;
	private String password;
	
	public User() {
	
	}
	
	public User(String name, String email, String password) {
		setName(name);
		setEmail(email);
		setPassword(password);
	}
	
	public User(String email, String password) {
		setEmail(email);
		setPassword(password);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		password = password;
	}
	
	
	
	
}
