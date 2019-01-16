package spike;

import dao.RoomDao;
import model.Room;

public class RoomTest {

	public static void main(String[] args) {
		RoomDao room = new RoomDao(); 
		room.save(new Room("03-01-301", "Test",true));
		for(Room r : room.getAll()) {
			r.print();
		}
	}

}
