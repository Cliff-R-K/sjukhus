package view;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import dao.RadiopharmaceuticalDao;
import dao.RegRadioDao;
import dao.RoomDao;
import dao.SubstanceDao;
import dao.UserDao;
import model.Calibration;
import model.Radiopharmaceutical;
import model.RegRadio;
import model.Room;
import model.Substance;
import model.Supplier;
import model.User;

public class Main_method {
	UserDao userdao = new UserDao();
	SubstanceDao substancedao = new SubstanceDao();
	RadiopharmaceuticalDao radiodao = new RadiopharmaceuticalDao();
	RegRadioDao regradiodao = new RegRadioDao();
	RoomDao roomdao = new RoomDao();

	//------------------------------User-------------------------------------------------------------------------
	public void addUser(String signature) {
		userdao.save(new User(signature));
	}

	public void deleteUser(int id) {
		userdao.delete(userdao.get(id));
	}

	public void updateUser(User t, String... args) {
		userdao.update(t, args);
	}

	public void getUser(int id) {
		userdao.get(id).print();
	}

	public void getAllUsers() {
		System.out.println("\nAlla Signaturer i databasen:\n");
		for (User s : userdao.getAll()) {
			s.print();
		}
	}

	//------------------------------Calibration-------------------------------------------------------------------------
	/*public void addCalibration(LocalDateTime date, double mbq) {
		calibrationdao.save(new Calibration(date, mbq));
	}

	public void deleteCalibration(int id) {
		calibrationdao.delete(calibrationdao.get(id));
	}

	public void updateCalibration(Calibration t, String...args) {
		calibrationdao.update(t, args);
	}

	public void getCalibration(int id) {
		calibrationdao.get(id).print();
	}

	public void getAllCalibrations() {
		System.out.println("\nAlla Kalibreringar i databasen:\n");
		for(Calibration s : calibrationdao.getAll()) {
			s.print();
		}
	}*/

	//------------------------------Room-------------------------------------------------------------------------
	public void addRoom(String id, String info) {
		roomdao.save(new Room(id, info));
	}

	public void getRoom(int id) {
		roomdao.get(id).print();
	}

	public void getAllRooms() {
		System.out.println("\nAlla Rum i databasen:\n");
		for(Room s : roomdao.getAll()) {
			s.print();
		}
	}

	//------------------------------Substance-------------------------------------------------------------------------

	public void addSubstance(String substance, double halfLife) {
		substancedao.save(new Substance(substance, halfLife));
	}

	public void deleteSubstance(int id) {
		substancedao.delete(substancedao.get(id));
	}

	public void updateSubstance(Substance t, String... args) {
		substancedao.update(t, args);
	}

	public void getSubstance(int id) {
		substancedao.get(id).print();
	}

	public void getAllSubstance() {
		System.out.println("\nAlla Ämnen i databasen:\n");
		for (Substance s : substancedao.getAll()) {
			s.print();
		}
	}
	//------------------------------Radiopharmaceutical-------------------------------------------------------------------------	
	public void addRadiopharmaceutical(String name, String form, Substance substance, Supplier supplier) {
		radiodao.save(new Radiopharmaceutical(name, form, substance, supplier));
	}
	public void getRadiopharmaceutical(int id) {
		radiodao.get(id).print();
	}
	public void getAllRadiopharmaceutical() {
		System.out.println("\nAlla Radiopharmaceuticals i databasen:\n");
		for (Radiopharmaceutical s : radiodao.getAll()) {
			s.print();
		}
	}

	//// ------------------------------RegRadio-------------------------------------------------------------------------
	//	public void addRegRadio(double startActivity, Date start, Date arrivalDate, String batchNumber, 
	//			String conControll, Radiopharmaceutical radiopharmaceutical, Room room, User user, Calibration calibration) {
	//		regradiodao.save(new RegRadio(startActivity, start, arrivalDate, batchNumber, conControll, radiopharmaceutical, room, user, calibration));
	//	}
	//
	//	public void getRegRadio(int id) {
	//		
	//		regradiodao.get(id).print();
	//		
	//	}
	//
	//	public void getAllRegRadio() {
	//		
	//		System.out.println("\nAlla Registrerade Produkter i databasen:\n");
	//		for (RegRadio s : regradiodao.getAll()) {
	//			s.print();
	//			
	//		}
	//	}

	public void addRegRadio() {
		regradiodao.save(new RegRadio(10.1, LocalDateTime.now(), java.sql.Date.valueOf(LocalDate.now()), "9999", "ok",
				new RadiopharmaceuticalDao().get(8), new RoomDao().get(1), new UserDao().get(4)));
	}
	
//	}


	public void printRegRadio(int id) {

		regradiodao.get(id).print();

	}
	public RegRadio getRegRadio(int id) {

		return regradiodao.get(id);

	}

	public void getAllRegRadio() {

		System.out.println("\nAlla Registrerade Produkter i databasen:\n");
		for (RegRadio s : regradiodao.getAll()) {
			s.print();

		}
	}
	// TODO Auto-generated method stub



}
