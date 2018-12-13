package model;

import java.time.LocalDateTime;
import java.util.Date;

public class RegRadio {

	private int id;
	private LocalDateTime startDate;
	private Date endDate;
	private Date arrivalDate;
	private String batchNumber;
	private String contaminationControll;
	private Calibration calibration;
	private Room room;
	private User user;
	private Radiopharmaceutical radiopharmaceutical;
	private Double startActivity;
	private Supplier supplier;

	
	

	public RegRadio(double startActivity, LocalDateTime start, Date arrivalDate, String batchNumber, 
			String conControll, Radiopharmaceutical radiopharmaceutical, Room room, User user, Calibration calibration, Supplier supplier) {

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
	}
	


	public RegRadio(double startActivity, LocalDateTime start, Date arrivalDate, String batchNumber, 
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
  
	public RegRadio(int id, double startActivity, LocalDateTime start, Date arrivalDate, String batchNumber, 
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
		this.supplier=radiopharmaceutical.getSupplier();
	}
	


	public RegRadio(Double valueOf, java.sql.Date valueOf2, Object object, Object object2, Object object3,
			Object object4, Object object5, User user2, Object object6) {
		// TODO Auto-generated constructor stub
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
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
@Override
public String toString() {
	// TODO Auto-generated method stub
	return radiopharmaceutical.toString();
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
	
}
