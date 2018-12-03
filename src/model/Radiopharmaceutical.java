package model;

import java.sql.Date;

public class Radiopharmaceutical {

	private String radiopharmaceuticalName;
	private Date startDate;
	private Date endDate;
	private String form;
	private Date arrivalDate;
	private String batchNumber;
	private String contaminationControll;
	private Substance substance;
	private Calibration calibration;
	private Room room;
	private Supplier supplier;

	public Radiopharmaceutical(String name, Date start,Date end,String form,Date arrivalDate, String batchNumber, String conControll,Substance substance,User user,Calibration calibration,Room room, Supplier supplier) {
		super();
		radiopharmaceuticalName = name;
		startDate = start;
		endDate = end;
		this.form = form;
		this.arrivalDate = arrivalDate;
		this.batchNumber = batchNumber;
		contaminationControll = conControll;
		this.substance = substance;
		this.calibration = calibration;
		this.room = room;
		this.supplier = supplier;
		
		
	}
	public Radiopharmaceutical(int id,String name, Date start,Date end,String form,Date arrivalDate, String batchNumber, String conControll,Substance substance,User user,Calibration calibration,Room room, Supplier supplier) {
		super();
		radiopharmaceuticalName = name;
		startDate = start;
		endDate = end;
		this.form = form;
		this.arrivalDate = arrivalDate;
		this.batchNumber = batchNumber;
		contaminationControll = conControll;
		this.substance = substance;
		this.calibration = calibration;
		this.room = room;
		this.supplier = supplier;
	}
	public String getRadiopharmaceuticalName() {
		return radiopharmaceuticalName;
	}
	public void setRadiopharmaceuticalName(String radiopharmaceuticalName) {
		this.radiopharmaceuticalName = radiopharmaceuticalName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getContaminationControll() {
		return contaminationControll;
	}
	public void setContaminationControll(String contaminationControll) {
		this.contaminationControll = contaminationControll;
	}
	public Substance getSubstance() {
		return substance;
	}
	public void setSubstance(Substance substance) {
		this.substance = substance;
	}
	public Calibration getCalibration() {
		return calibration;
	}
	public void setCalibration(Calibration calibration) {
		this.calibration = calibration;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	

}
