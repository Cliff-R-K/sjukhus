package model;

import java.util.Date;

public class RegRadio {

	private int id;
	private Date startDate;
	private Date endDate;
	private Date arrivalDate;
	private String batchNumber;
	private String contaminationControll;
	private String contaminationControlComment;
	private Calibration calibration;
	private Room room;
	private User user;
	private Radiopharmaceutical radiopharmaceutical;
	private Double startActivity;
	private Supplier supplier;
	private String time;
	
	

	public RegRadio(double startActivity, Date start, Date arrivalDate, String batchNumber, 
			String conControll, Radiopharmaceutical radiopharmaceutical, Room room, User user, Calibration calibration, Supplier supplier, String time, String comment) {

		super();
		this.startActivity = startActivity;
		startDate = start;
		endDate = null;
		this.arrivalDate = arrivalDate;
		this.batchNumber = batchNumber;
		contaminationControll = conControll;
		this.user=user;
		this.calibration = calibration;
		this.room = room;
		this.user = user;
		this.startActivity = startActivity;		
		this.radiopharmaceutical = radiopharmaceutical;
		this.setSupplier(supplier);
		this.setTime(time);
		this.setContaminationControlComment(comment);
	}
	

	public RegRadio(double startActivity, Date start, Date arrivalDate, String batchNumber, 
			String conControll, Radiopharmaceutical radiopharmaceutical, Room room, User user, Calibration calibration) {

		super();
		this.startActivity = startActivity;
		startDate = start;
		endDate = null;
		this.arrivalDate = arrivalDate;
		this.batchNumber = batchNumber;
		contaminationControll = conControll;
		this.user=user;
		this.calibration = calibration;
		this.room = room;
		this.user = user;
		this.startActivity = startActivity;		
		this.radiopharmaceutical = radiopharmaceutical;
	}
  
	public RegRadio(int id, double startActivity, Date start, Date arrivalDate, String batchNumber, 
			String conControll, Radiopharmaceutical radiopharmaceutical, Room room, User user, Calibration calibration) {

		super();
		this.id = id;
		this.startActivity = startActivity;
		startDate = start;
		endDate = null;
		this.arrivalDate = arrivalDate;
		this.batchNumber = batchNumber;
		contaminationControll = conControll;
		this.user=user;
		this.calibration = calibration;
		this.room = room;
		this.user = user;
		this.startActivity = startActivity;		
		this.radiopharmaceutical = radiopharmaceutical;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Double getStartActivity() {
		return startActivity;
	}

	public void setStartActivity(Double startActivity) {
		this.startActivity = startActivity;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setRadiopharmaceutical(Radiopharmaceutical radiopharmaceutical) {
		this.radiopharmaceutical = radiopharmaceutical;
	}
	public Radiopharmaceutical getRadiopharmaceutical() {
		return radiopharmaceutical;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public String getContaminationControlComment() {
		return contaminationControlComment;
	}


	public void setContaminationControlComment(String contaminationControlComment) {
		this.contaminationControlComment = contaminationControlComment;
	}


	/*public void print2() {
		
		System.out.println("ID: " + getId() + "\t Name: " + getRadiopharmaceuticalName()+ "\t start activity: "+ getStartActivity()
		+ "\t start date: " + getStartDate()+ "\t form: " +getForm()+ "\t arrival date: " + getArrivalDate()
		+ "\t batchnumber: " +getBatchNumber()+ "\t con controll: " + getContaminationControll()
		+ "\t substance: " + getSubstance()+ "\t user: " + getUser()+ "\t calibration: " +getCalibration()
		+ "\t room: " + getRoom()+ "\t radiopharmaceutical: " +getRadiopharmaceutical());
	}*/
	public void print() {
		System.out.println("ID:	" + getId() + "\tSign: " + getUser().getSignature());

	}
	/*@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.batchNumber;
	}*/
}
