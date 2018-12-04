package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


import db.DbConnectionManager;
import model.Room;

public class RoomDao implements IDao<Room> {
DbConnectionManager conn = null;

public RoomDao() {
	conn = DbConnectionManager.getInstance();
}
	@Override
	public Room get(int id) {
		Room room = null;
		
		try {
			ResultSet rs = conn.excecuteQuery("SELECT * FROM rooms WHERE id = " +id);
			if(!rs.next())
				throw new NoSuchElementException();
			else {
				room = new Room(rs.getString(1), rs.getString(2));
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return room;
	}

	@Override
	public List<Room> getAll() {
		ArrayList<Room> list = new ArrayList<>();
		
		try {
			ResultSet rs = conn.excecuteQuery("SELECT * FROM rooms");
			while(rs.next()) {
				list.add(new Room(rs.getString(1), rs.getString(2)));
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean save(Room t) {
		PreparedStatement ps = null;
		boolean saveSuccess = false;
		
		try {
			String queryString = "INSERT INTO rooms (idroom, description) VALUES(?,?)";
			ps = conn.prepareStatement(queryString);
			ps.setString(1, t.getId());
			ps.setString(2, t.getDescription());
			if(ps.executeUpdate() == 1)
				saveSuccess = true;
		} catch (Exception e) {
			System.err.println("Could not save");
		}
		
		System.out.println("Save Success");
		return saveSuccess;
	}

	@Override
	public void update(Room t, String[] params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Room t) {
		// TODO Auto-generated method stub
		
	}

}
