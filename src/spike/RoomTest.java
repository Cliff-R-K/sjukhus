package spike;

import dao.RoomDao;
import model.Room;

public class RoomTest {

	public static void main(String[] args) {
		RoomDao room = new RoomDao(); 
		for(Room r : room.getAll()) {
			r.print();
		}
	}

}
