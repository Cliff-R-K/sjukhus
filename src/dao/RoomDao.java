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
			ResultSet rs = conn.excecuteQuery("SELECT * FROM rooms WHERE idroom = " +id);
			if(!rs.next())
				throw new NoSuchElementException();
			else {
				room = new Room(rs.getInt(1), rs.getString(2), rs.getString(3));
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return room;
	}
	
	public List<Room> getTrash() {
		String room_code = "kassering";
		ArrayList<Room> list = new ArrayList<>();
		try {
			ResultSet rs = conn.excecuteQuery("SELECT * FROM rooms WHERE room_code = " +"\"" + room_code + "\"");
			while(rs.next()) {
				list.add(new Room(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Room> getAll() {
		String room_code = "kassering";
		ArrayList<Room> list = new ArrayList<>();
		
		try {
			ResultSet rs = conn.excecuteQuery("SELECT * FROM rooms WHERE room_code != " +"\"" + room_code + "\"");
			while(rs.next()) {
				list.add(new Room(rs.getInt(1), rs.getString(2), rs.getString(3)));
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
			String queryString = "INSERT INTO rooms (room_code,description,aktivt) VALUES(?,?,?)";
			ps = conn.prepareStatement(queryString);
			ps.setString(1, t.getRoomCode());
			ps.setString(2, t.getDescription());
			ps.setBoolean(3, t.getIsActive());
			if(ps.executeUpdate() == 1)
				saveSuccess = true;
			System.out.println("Save Success");
		} catch (Exception e) {
			System.err.println("Could not save");
		}
		
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
