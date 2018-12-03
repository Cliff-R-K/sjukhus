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
		//this.id = id;
		//this.f_namn = f_namn;
		//this.e_namn = e_namn;
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
		System.out.println(getSignature());
	}
}
