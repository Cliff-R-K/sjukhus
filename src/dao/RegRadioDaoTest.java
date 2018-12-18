package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	List <RegRadio> regradiolist = new ArrayList<>();
	
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

	}

	@AfterEach
	void tearDown() throws Exception {
		regradiolist.clear();
	}

	@Test
	void getFromRadioPharmaceutical() {
		int actual = regradiodao.getSearchedRegRadios(startDate, endDate, radiopharmaceutical, null, null).size();
		int expected = 6;
		assertEquals(expected, actual,"skulle varit: "+expected+  "men va: "+actual);
		
	}
	
	@Test
	void getFromUser() {
		int actual = regradiodao.getSearchedRegRadios(startDate, endDate, null, null, user).size();
		int expected = 19;
		assertEquals(expected, actual,"skulle varit: "+expected+  "men va: "+actual);

	}
	
	@Test
	void getFromRoom() {
		int actual = regradiodao.getSearchedRegRadios(startDate, endDate, null, room, null).size();
		int expected = 13;
		assertEquals(expected, actual,"skulle varit: "+expected+  "men va: "+actual);
	}
	
	@Test
	void getFromRadioPharmaceuticalRoom() {
		int actual = regradiodao.getSearchedRegRadios(startDate, endDate, radiopharmaceutical, room, null).size();
		int expected = 2;
		assertEquals(expected, actual,"skulle varit: "+expected+  "men va: "+actual);
	}
	
	@Test
	void getFromRadioPharmaceuticalUser() {
		int actual = regradiodao.getSearchedRegRadios(startDate, endDate, radiopharmaceutical, null, user).size();
		int expected = 5;
		assertEquals(expected, actual,"skulle varit: "+expected+  "men va: "+actual);
		}
	
	@Test
	void getFromRadioPharmaceuticalRoomUser() {
		int actual = regradiodao.getSearchedRegRadios(startDate, endDate, radiopharmaceutical, room, user).size();
		int expected = 1;
		assertEquals(expected, actual,"skulle varit: "+expected+  "men va: "+actual);
		}
	
	@Test
	void getFromRoomUser() {
		int actual = regradiodao.getSearchedRegRadios(startDate, endDate, null, room, user).size();
		int expected = 9;
		assertEquals(expected, actual,"skulle varit: "+expected+  "men va: "+actual);
		}
	
	@Test
	void getFromNothing() {
		int actual = regradiodao.getSearchedRegRadios(startDate, endDate, null, null, null).size();
		int expected = 26;
		assertEquals(expected, actual,"skulle varit: "+expected+  "men va: "+actual);
		}

}
