package model;

import java.util.Date;

public class Radiopharmaceutical {

	private String radiopharmaceuticalName;
	private String form;
	private Substance substance;
	private Supplier supplier;
	private int id;

	public Radiopharmaceutical(String name, String form, Substance substance, Supplier supplier) {
		super();
		radiopharmaceuticalName = name;
		this.form = form;
		this.substance = substance;
		this.supplier = supplier;
	
	}
  
	public Radiopharmaceutical(int id, String name, String form, Substance substance, Supplier supplier) {
		super();
		this.id=id;
		radiopharmaceuticalName = name;
		this.form = form;
		this.substance = substance;
		this.supplier = supplier;

	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRadiopharmaceuticalName() {
		return radiopharmaceuticalName;
	}
	public void setRadiopharmaceuticalName(String radiopharmaceuticalName) {
		this.radiopharmaceuticalName = radiopharmaceuticalName;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public Substance getSubstance() {
		return substance;
	}
	public void setSubstance(Substance substance) {
		this.substance = substance;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public void print() {
		
		System.out.println("ID: " + getId() + "\t Name: " + getRadiopharmaceuticalName()+ "\t form: " +getForm()
		+ "\t substance: " + getSubstance()+ "\t supplier: " +getSupplier());
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.radiopharmaceuticalName;
	}
}
