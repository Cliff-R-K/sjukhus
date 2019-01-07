package test;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.RadiopharmaceuticalDao;
import dao.RegRadioDao;
import dao.RoomDao;
import dao.UserDao;
import model.Radiopharmaceutical;
import model.RegRadio;
import model.Room;
import model.User;

class RegRadioDaoTest {
	RegRadioDao regradiodao = new RegRadioDao();
	RadiopharmaceuticalDao radiodao = new RadiopharmaceuticalDao();
	RoomDao roomdao = new RoomDao();
	UserDao userdao = new UserDao();
	Radiopharmaceutical radiopharmaceutical;
	User user;
	Room room;
	String pattern = "dd-mm-yyyy";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	java.sql.Date startDate;
	java.sql.Date endDate;
	java.sql.Date arrivalDate;
	Date date1;
	String startString = "01-01-1900";
	String endString = "10-02-2019";
	Date date2;
	Date date3;
	List<RegRadio> regradiolist = new ArrayList<>();
	int aktiv;
	int inaktiv;


	@BeforeEach
	void setUp() throws Exception {
		radiopharmaceutical = radiodao.get(1);
		user = userdao.get(2);
		room = roomdao.get(1);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		date1 = simpleDateFormat.parse(startString);
		date2 = simpleDateFormat.parse(endString);
		startDate = new java.sql.Date(date1.getTime());
		endDate = new java.sql.Date(date2.getTime());
		aktiv = 1;
		inaktiv = 1;

	}

	@AfterEach
	void tearDown() throws Exception {
		regradiolist.clear();
	}
	
	@Test
	void getFromTrash() {
		System.out.println("0");
		regradiolist.addAll(regradiodao.getTrash());
		for (RegRadio rg : regradiolist) {
			assertEquals("kassering", rg.getRoom().getRoomCode(), "Fel room hämtades");
		}
	}

	@Test
	void getFromRadioPharmaceutical() {
		System.out.println("1");
		regradiolist.addAll(regradiodao.getSearchedRegRadios(startDate, endDate, radiopharmaceutical, null, null, aktiv));
		for (RegRadio rg : regradiolist) {
			assertEquals(radiopharmaceutical.getRadiopharmaceuticalName(),
					rg.getRadiopharmaceutical().getRadiopharmaceuticalName(), "Fel radiopharmaceutical hämtades");
		}
	}

	@Test
	void getFromUser() {
		System.out.println("2");
		regradiolist.addAll(regradiodao.getSearchedRegRadios(startDate, endDate, null, null, user, aktiv));
		for (RegRadio rg : regradiolist) {
			assertEquals(user.getSignature(), rg.getUser().getSignature(), "Fel user hämtades");
		}

	}

	@Test
	void getFromRoom() {
		System.out.println("3");
		regradiolist.addAll(regradiodao.getSearchedRegRadios(startDate, endDate, null, room, null, aktiv));
		for (RegRadio rg : regradiolist) {
			assertEquals(room.getDescription(), rg.getRoom().getDescription(), "Fel room hämtades");
		}
	}

	@Test
	void getFromRadioPharmaceuticalRoom() {
		System.out.println("4");
		regradiolist.addAll(regradiodao.getSearchedRegRadios(startDate, endDate, radiopharmaceutical, room, null, aktiv));
		for (RegRadio rg : regradiolist) {
			assertEquals(radiopharmaceutical.getRadiopharmaceuticalName(),
					rg.getRadiopharmaceutical().getRadiopharmaceuticalName(), "Fel radiopharmaceutical hämtades");
			assertEquals(room.getDescription(), rg.getRoom().getDescription(), "Fel room hämtades");
		}
	}

	@Test
	void getFromRadioPharmaceuticalUser() {
		System.out.println("5");
		regradiolist.addAll(regradiodao.getSearchedRegRadios(startDate, endDate, radiopharmaceutical, null, user, aktiv));
		for (RegRadio rg : regradiolist) {
			assertEquals(radiopharmaceutical.getRadiopharmaceuticalName(),
					rg.getRadiopharmaceutical().getRadiopharmaceuticalName(), "Fel radiopharmaceutical hämtades");
			assertEquals(user.getSignature(), rg.getUser().getSignature(), "Fel user hämtades");
		}
	}

	@Test
	void getFromRadioPharmaceuticalRoomUser() {
		System.out.println("6");
		regradiolist.addAll(regradiodao.getSearchedRegRadios(startDate, endDate, radiopharmaceutical, room, user, aktiv));
		for (RegRadio rg : regradiolist) {
			assertEquals(radiopharmaceutical.getRadiopharmaceuticalName(),
					rg.getRadiopharmaceutical().getRadiopharmaceuticalName(), "Fel radiopharmaceutical hämtades");
			assertEquals(user.getSignature(), rg.getUser().getSignature(), "Fel user hämtades");
			assertEquals(room.getDescription(), rg.getRoom().getDescription(), "Fel room hämtades");
		}
	}

	@Test
	void getFromRoomUser() {
		System.out.println("7");
		regradiolist.addAll(regradiodao.getSearchedRegRadios(startDate, endDate, null, room, user, aktiv));
		for (RegRadio rg : regradiolist) {
			assertEquals(user.getSignature(), rg.getUser().getSignature(), "Fel user hämtades");
			assertEquals(room.getDescription(), rg.getRoom().getDescription(), "Fel room hämtades");
		}
	}

	@Test
	void getFromNothing() {
		System.out.println("8");
		regradiolist.addAll(regradiodao.getSearchedRegRadios(startDate, endDate, null, null, null, aktiv));
		List<RegRadio> allRegRadioList = regradiodao.getAll();
		assertEquals(allRegRadioList.size(), regradiolist.size(),
				"skulle varit: " + allRegRadioList.size() + "men va: " + regradiolist.size());
	}
	
	@Test
	void getFirstDateFromDatabase() {
		System.out.println("9");
		LocalDate localDate = regradiodao.getFirstDate().toLocalDate();
		assertEquals("2018-10-30", localDate.toString(), "fel första datum från databasen");
	}

}
