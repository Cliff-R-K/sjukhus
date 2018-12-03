package model;


/**
 *
 * @author kristersundlof
 */
public class Student {
	private int id;
	private String f_namn;
	private String e_namn;
	private String adress;
	private String epost;
	private String mobilnr;
	
	
	public Student(String f_namn, String e_namn, String adress, String epost, String mobilnr) {
		super();
		//this.id = id;
		this.f_namn = f_namn;
		this.e_namn = e_namn;
		this.adress = adress;
		this.epost = epost;
		this.mobilnr = mobilnr;
	}

	public Student(int id, String f_namn, String e_namn, String adress, String epost, String mobilnr) {
		super();
		this.id = id;
		this.f_namn = f_namn;
		this.e_namn = e_namn;
		this.adress = adress;
		this.epost = epost;
		this.mobilnr = mobilnr;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getF_namn() {
		return f_namn;
	}


	public void setF_namn(String f_namn) {
		this.f_namn = f_namn;
	}


	public String getE_namn() {
		return e_namn;
	}


	public void setE_namn(String e_namn) {
		this.e_namn = e_namn;
	}


	public String getAdress() {
		return adress;
	}


	public void setAdress(String adress) {
		this.adress = adress;
	}


	public String getEpost() {
		return epost;
	}


	public void setEpost(String epost) {
		this.epost = epost;
	}


	public String getMobilnr() {
		return mobilnr;
	}


	public void setMobilnr(String mobilnr) {
		this.mobilnr = mobilnr;
	}


	public void print() {
		System.out.println(getF_namn() + " " + getE_namn() +", Adress: " + getAdress() + ", Epost: " + getEpost()+ ", Mobil:" + getMobilnr());
	}
}
