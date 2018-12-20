package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.RoomDao;
import model.Room;

class RoomTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		Room room = new RoomDao().get(2);
		assertEquals("NM", room.getRoomCode(), "Fel rum");
		List<Room> trash = new RoomDao().getTrash();
		for(Room t : trash) {
			System.out.println(t.getDescription());
		}
		List<Room> main = new RoomDao().getAll();
		for(Room t : main) {
			System.out.println(t.getDescription());
		}
	}
}
