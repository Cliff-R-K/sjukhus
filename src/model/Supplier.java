package model;

public class Supplier {

	
	
	private int supplierId;
	private String supplierName;

	public Supplier (int id,String name)
	{
		super();
		this.supplierId=id;
		this.supplierName=name;
	}
	public Supplier (String name)
	{
	super();
		this.supplierName=name;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void print() {
		System.out.println("ID: " + getSupplierId() + "\tgetSupplierName(): " + getSupplierName());
	}
}
