package model;


/**
 *
 * @author kristersundlof
 */
public class User {
	private int id;
	private String signature;
	private String password;
	private int current;
	
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User(String signature) {
		super();
		this.signature = signature;
		this.password= signature;
	}

	public User(int id, String signature) {
		super();
		this.id = id;
		this.signature = signature;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getSignature() {
		return signature;
	}


	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	
	public void setCurrent(int current) {
		this.current = current;
	}
	
	public int getCurrent() {
		return current;
	}

	public void print() {
		System.out.println("ID: " + getId() + "\tUser: " + getSignature());
	}

	@Override
	public String toString() {
		return signature;
	}

}
