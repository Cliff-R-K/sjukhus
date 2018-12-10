package model;


/**
 *
 * @author kristersundlof
 */
public class User {
	private int id;
	private String signature;
	
	
	public User(String signature) {
		super();
		this.signature = signature;
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
	
	

	public void print() {
		System.out.println("ID: " + getId() + "\tUser: " + getSignature());
	}
}
