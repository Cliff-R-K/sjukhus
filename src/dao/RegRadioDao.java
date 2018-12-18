package dao;

import java.util.Date;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import db.DbConnectionManager;
import model.Calibration;
import model.Radiopharmaceutical;
import model.RegRadio;
import model.Room;
import model.User;

/**
 * @author kristersundlof
 *
 */
public class RegRadioDao implements IDao<RegRadio> {
	DbConnectionManager conn = null;
	LocalDateTime ldt;

	public RegRadioDao() {
		conn = DbConnectionManager.getInstance();
	}

	public ArrayList<RegRadio> getRegRadiosBySupplierName(String name) {
		RegRadio rp = null;
		ArrayList<RegRadio> radioList = new ArrayList<>();
		try {

			String sqlString = "SELECT * FROM regradios "
					+ "JOIN suppliers ON regradios.suppliers_idsupplier = suppliers.idsupplier "
					+ "JOIN substances ON regradios.substances_idsubstance = substances.idsubstance "
					+ "WHERE suppliers.name=\"" + name + "\"";
			ResultSet rs = conn.excecuteQuery(sqlString);
//			if (!rs.next())
//				throw new NoSuchElementException("The supplier with name " + name + " doesen't exist in database");
//			else {
			while (rs.next()) {
				Radiopharmaceutical radiopharmaceutical = new RadiopharmaceuticalDao().get(rs.getInt(7));
				Room room = new RoomDao().get(rs.getInt(8));
				User user = new UserDao().get(rs.getInt(9));
				if(rs.getTimestamp(11) == null) {
				rp = new RegRadio(rs.getInt(1), rs.getDouble(2), rs.getTimestamp(3).toLocalDateTime(), rs.getDate(4),
						rs.getString(5), rs.getString(6), radiopharmaceutical, room, user, rs.getDouble(10),
						null);
				radioList.add(rp);
				} else {
					rp = new RegRadio(rs.getInt(1), rs.getDouble(2), rs.getTimestamp(3).toLocalDateTime(), rs.getDate(4),
						rs.getString(5), rs.getString(6), radiopharmaceutical, room, user, rs.getDouble(10),
						rs.getTimestamp(11).toLocalDateTime());
				radioList.add(rp);	
				}
			}
//			}
			conn.close();
		} catch (SQLException e) {
			System.err.println("Ingen Läkemedel med namn " + name + " hittades!");
		}
		return radioList;

//		SELECT * FROM nucleardb.regradios
//		join nucleardb.suppliers on nucleardb.regradios.suppliers_idsupplier = nucleardb.suppliers.idsupplier
//		join nucleardb.substances on nucleardb.regradios.substances_idsubstance = nucleardb.substances.idsubstance
//		where nucleardb.suppliers.idsupplier = 1

	}

	public ArrayList<RegRadio> getSearchedRegRadios(Date startDate, Date endDate,
			Radiopharmaceutical radiopharmaceutical, Room room, User user) {
		RegRadio rp = null;
		ArrayList<RegRadio> searchedList = new ArrayList<>();
		try {
			String sqlString = null;
			if (radiopharmaceutical != null && room == null && user == null) {
				sqlString = "SELECT * FROM regradios "
						+ "JOIN radiopharmaceuticals ON regradios.radiopharmaceuticals_idradio = radiopharmaceuticals.idradio "
						+ "JOIN rooms ON regradios.rooms_idroom = rooms.idroom "
						+ "JOIN users ON regradios.users_iduser = users.iduser " + " WHERE arrival_date BETWEEN \""
						+ startDate + "\"" + " AND " + "\"" + endDate + "\"" + " AND radiopharmaceuticals_idradio = "
						+radiopharmaceutical.getId()+";";
			} else if (radiopharmaceutical != null && room != null && user == null) {
				sqlString = "SELECT * FROM regradios "
						+ "JOIN radiopharmaceuticals ON regradios.radiopharmaceuticals_idradio = radiopharmaceuticals.idradio "
						+ "JOIN rooms ON regradios.rooms_idroom = rooms.idroom "
						+ "JOIN users ON regradios.users_iduser = users.iduser " + " WHERE arrival_date BETWEEN \""
						+ startDate + "\"" + " AND " + "\"" + endDate + "\"" + " AND radiopharmaceuticals_idradio = "
						+ "\"" + radiopharmaceutical.getId() + "\"" + " AND rooms_idroom = " + "\"" + room.getId()
						+ "\"";
			} else if (radiopharmaceutical != null && room == null && user != null) {
				sqlString = "SELECT * FROM regradios "
						+ "JOIN radiopharmaceuticals ON regradios.radiopharmaceuticals_idradio = radiopharmaceuticals.idradio "
						+ "JOIN rooms ON regradios.rooms_idroom = rooms.idroom "
						+ "JOIN users ON regradios.users_iduser = users.iduser " + " WHERE arrival_date BETWEEN \""
						+ startDate + "\"" + " AND " + "\"" + endDate + "\"" + " AND radiopharmaceuticals_idradio = "
						+ "\"" + radiopharmaceutical.getId() + "\"" + " AND users_iduser = " + "\"" + user.getId()
						+ "\"";
			} else if (radiopharmaceutical == null && room != null && user == null) {
				sqlString = "SELECT * FROM regradios "
						+ "JOIN radiopharmaceuticals ON regradios.radiopharmaceuticals_idradio = radiopharmaceuticals.idradio "
						+ "JOIN rooms ON regradios.rooms_idroom = rooms.idroom "
						+ "JOIN users ON regradios.users_iduser = users.iduser " + " WHERE arrival_date BETWEEN \""
						+ startDate + "\"" + " AND " + "\"" + endDate + "\"" + " AND rooms_idroom = " + "\""
						+ room.getId() + "\"";
			} else if (radiopharmaceutical == null && room != null && user != null) {
				sqlString = "SELECT * FROM regradios "
						+ "JOIN radiopharmaceuticals ON regradios.radiopharmaceuticals_idradio = radiopharmaceuticals.idradio "
						+ "JOIN rooms ON regradios.rooms_idroom = rooms.idroom "
						+ "JOIN users ON regradios.users_iduser = users.iduser " + " WHERE arrival_date BETWEEN \""
						+ startDate + "\"" + " AND " + "\"" + endDate + "\"" + " AND rooms_idroom = " + "\""
						+ room.getId() + "\"" + " AND users_iduser = " + "\"" + user.getId() + "\"";
			} else if (radiopharmaceutical == null && room == null && user != null) {
				sqlString = "SELECT * FROM regradios "
						+ "JOIN radiopharmaceuticals ON regradios.radiopharmaceuticals_idradio = radiopharmaceuticals.idradio "
						+ "JOIN rooms ON regradios.rooms_idroom = rooms.idroom "
						+ "JOIN users ON regradios.users_iduser = users.iduser " + " WHERE arrival_date BETWEEN \""
						+ startDate + "\"" + " AND " + "\"" + endDate + "\"" + " AND users_iduser = " + "\""
						+ user.getId() + "\"";
			} else if (radiopharmaceutical != null && room != null && user != null) {
				sqlString = "SELECT * FROM regradios "
						+ "JOIN radiopharmaceuticals ON regradios.radiopharmaceuticals_idradio = radiopharmaceuticals.idradio "
						+ "JOIN rooms ON regradios.rooms_idroom = rooms.idroom "
						+ "JOIN users ON regradios.users_iduser = users.iduser " + " WHERE arrival_date BETWEEN \""
						+ startDate + "\"" + " AND " + "\"" + endDate + "\"" + " AND radiopharmaceuticals_idradio = "
						+ "\"" + radiopharmaceutical.getId() + "\"" + " AND rooms_idroom = " + "\"" + room.getId()
						+ "\"" + " AND users_iduser = " + "\"" + user.getId() + "\"";
			} else if (radiopharmaceutical == null && room == null && user == null){
				sqlString = "SELECT * FROM regradios "
						+ "JOIN radiopharmaceuticals ON regradios.radiopharmaceuticals_idradio = radiopharmaceuticals.idradio "
						+ "JOIN rooms ON regradios.rooms_idroom = rooms.idroom "
						+ "JOIN users ON regradios.users_iduser = users.iduser " + " WHERE arrival_date BETWEEN \""
						+ startDate + "\"" + " AND " + "\"" + endDate + "\"";
			}
			ResultSet rs = conn.excecuteQuery(sqlString);
			while (rs.next()) {
				Radiopharmaceutical radiopharmaceutical2 = new RadiopharmaceuticalDao().get(rs.getInt(7));
				Room room2 = new RoomDao().get(rs.getInt(8));
				User user2 = new UserDao().get(rs.getInt(9));
				if (rs.getTimestamp(11) == null) {
				rp = new RegRadio(rs.getInt(1), rs.getDouble(2), rs.getTimestamp(3).toLocalDateTime(), rs.getDate(4),
						rs.getString(5), rs.getString(6), radiopharmaceutical2, room2, user2, rs.getDouble(10),
						null);
				searchedList.add(rp);
				} else {
					rp = new RegRadio(rs.getInt(1), rs.getDouble(2), rs.getTimestamp(3).toLocalDateTime(), rs.getDate(4),
							rs.getString(5), rs.getString(6), radiopharmaceutical2, room2, user2, rs.getDouble(10), rs.getTimestamp(11).toLocalDateTime());
					searchedList.add(rp);
				}
			}
//			}
			conn.close();
		} catch (SQLException e) {
			System.err.println("Ingen registrerade Läkemedel  mellan" + startDate + endDate + " hittades!");
		}
		return searchedList;
	}

	@Override

	public RegRadio get(int id) throws NoSuchElementException {
		RegRadio regRadio = null;
		try {
			String sqlString = "SELECT * FROM regradios WHERE idregradio=" + id;
			ResultSet rs = conn.excecuteQuery(sqlString);
			if (!rs.next())
				throw new NoSuchElementException(
						"The radiopharmaceutical with id " + id + " doesen't exist in database");
			else {
				Radiopharmaceutical radiopharmaceutical = new RadiopharmaceuticalDao().get(rs.getInt(7));
				Room room = new RoomDao().get(rs.getInt(8));
				User user = new UserDao().get(rs.getInt(9));
				if (rs.getTimestamp(11) == null) {
					regRadio = new RegRadio(rs.getInt(1), rs.getDouble(2), rs.getTimestamp(3).toLocalDateTime(),
							rs.getDate(4), rs.getString(5), rs.getString(6), radiopharmaceutical, room, user,
							rs.getDouble(10), null);
				} else {
					regRadio = new RegRadio(rs.getInt(1), rs.getDouble(2), rs.getTimestamp(3).toLocalDateTime(),
							rs.getDate(4), rs.getString(5), rs.getString(6), radiopharmaceutical, room, user,
							rs.getDouble(10), rs.getTimestamp(11).toLocalDateTime());
				}
			}
			conn.close();
		} catch (SQLException e) {
			System.err.println("Ingen Läkemedel med " + id + " hittades!");
		}
		return regRadio;
	}

	@Override
	public List<RegRadio> getAll() {
		ArrayList<RegRadio> list = new ArrayList<>();

		try {
			String sqlQuary = "SELECT * FROM regradios ORDER BY idregradio DESC";
			ResultSet rs = conn.excecuteQuery(sqlQuary);
			while (rs.next()) {
				Radiopharmaceutical radiopharmaceutical = new RadiopharmaceuticalDao().get(rs.getInt(7));
				Room room = new RoomDao().get(rs.getInt(8));
				User user = new UserDao().get(rs.getInt(9));
				if (rs.getTimestamp(11) == null) {
					list.add(new RegRadio(rs.getInt(1), rs.getDouble(2), rs.getTimestamp(3).toLocalDateTime(),
							rs.getDate(4), rs.getString(5), rs.getString(6), radiopharmaceutical, room, user,
							rs.getDouble(10), null));
				} else {
					list.add(new RegRadio(rs.getInt(1), rs.getDouble(2), rs.getTimestamp(3).toLocalDateTime(),
							rs.getDate(4), rs.getString(5), rs.getString(6), radiopharmaceutical, room, user,
							rs.getDouble(10), rs.getTimestamp(11).toLocalDateTime()));

				}
			}
			conn.close();
		} catch (SQLException e) {
			System.err.println("Inga Läkemedel hittades");
		}

		return list;
	}

	public List<RegRadio> getLastNrows(int number) {
		ArrayList<RegRadio> list = new ArrayList<>();

		try {
			String sqlQuary = "SELECT * FROM regradios LIMIT " + number;
			ResultSet rs = conn.excecuteQuery(sqlQuary);
			while (rs.next()) {
				Radiopharmaceutical radiopharmaceutical = new RadiopharmaceuticalDao().get(rs.getInt(7));
				Room room = new RoomDao().get(rs.getInt(8));
				User user = new UserDao().get(rs.getInt(9));
				if (rs.getTimestamp(11) == null) {
					list.add(new RegRadio(rs.getInt(1), rs.getDouble(2), rs.getTimestamp(3).toLocalDateTime(),
							rs.getDate(4), rs.getString(5), rs.getString(6), radiopharmaceutical, room, user,
							rs.getDouble(10), null));
				} else {
					list.add(new RegRadio(rs.getInt(1), rs.getDouble(2), rs.getTimestamp(3).toLocalDateTime(),
							rs.getDate(4), rs.getString(5), rs.getString(6), radiopharmaceutical, room, user,
							rs.getDouble(10), rs.getTimestamp(11).toLocalDateTime()));

				}
			}
			conn.close();
		} catch (SQLException e) {
			System.err.println("Inga Läkemedel hittades");
		}
		return list;
	}

	@Override
	public boolean save(RegRadio t) {
		PreparedStatement ps = null;
		boolean saveSucess = false;
		try {
			String queryString;
			queryString = "INSERT INTO regradios (start_activity, start_date, arrival_date, batchnumber, con_controll, radiopharmaceuticals_idradio, rooms_idroom, users_iduser, calibration_activity, calibration_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(queryString);
			ps.setDouble(1, t.getStartActivity());
			ps.setTimestamp(2, java.sql.Timestamp.valueOf(t.getStartDate()));
			ps.setDate(3, new java.sql.Date(t.getArrivalDate().getTime()));
			ps.setString(4, t.getBatchNumber());
			ps.setString(5, t.getContaminationControll());
			ps.setInt(6, t.getRadiopharmaceutical().getId());
			ps.setInt(7, t.getRoom().getId());
			ps.setInt(8, t.getUser().getId());
			if(t.getCalibrationActivity()==null) {
				ps.setObject(9, (Double) null);
			} else {
			ps.setDouble(9, t.getCalibrationActivity());
			}
			if(t.getCalibrationDate() == null) {
			ps.setTimestamp(10, null);
			} else {
				ps.setTimestamp(10, java.sql.Timestamp.valueOf(t.getCalibrationDate()));
			}
			if (ps.executeUpdate() == 1) {
				System.out.println("Save Success");
				saveSucess = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Could not save");
		}
		return saveSucess;
	}

	@Override
	public void update(RegRadio t, String[] params) {
		PreparedStatement ps = null;
		RegRadio rg = get(t.getId());

		for (String p : params) {
			if (p.equals("start_activity")) {
				rg.setStartActivity(t.getStartActivity());
			} else if (p.equals("start_date")) {
				rg.setStartDate(t.getStartDate());
			} else if (p.equals("arrival_date")) {
				rg.setArrivalDate(t.getArrivalDate());
			} else if (p.equals("batchnumber")) {
				rg.setBatchNumber(t.getBatchNumber());
			} else if (p.equals("con_controll")) {
				rg.setContaminationControll(t.getContaminationControll());
			} else if (p.equals("radiopharmaceutical_idradio")) {
				rg.setRadiopharmaceutical(t.getRadiopharmaceutical());
			} else if (p.equals("rooms_idroom")) {
				rg.setRoom(t.getRoom());
			} else if (p.equals("users_iduser")) {
				rg.setUser(t.getUser());
			} else if (p.equals("calibration_activity")) {
				rg.setCalibrationActivity(t.getCalibrationActivity());
				rg.setCalibrationsDate();
			} 
		}

		try {
			String sqlQuery = "UPDATE regradios SET start_activity=?, start_date=?, arrival_date=?, batchnumber=?, con_controll=?, "
					+ "radiopharmaceuticals_idradio=?, rooms_idroom=?, users_iduser=?, calibration_activity=?, calibration_date=? WHERE idregradio=?";
			ps = conn.prepareStatement(sqlQuery);
			ps.setDouble(1, rg.getStartActivity());
			ps.setTimestamp(2, java.sql.Timestamp.valueOf(t.getStartDate()));
			ps.setDate(3, new java.sql.Date(t.getArrivalDate().getTime()));
			ps.setString(4, rg.getBatchNumber());
			ps.setString(5, rg.getContaminationControll());
			ps.setInt(6, rg.getRadiopharmaceutical().getId());
			ps.setInt(7, rg.getRoom().getId());
			ps.setInt(8, rg.getUser().getId());
			ps.setDouble(9, rg.getCalibrationActivity());
			System.out.println(rg.getCalibrationDate());
			if(rg.getCalibrationDate() == null) {
				ps.setTimestamp(10, null);
			} else {
				ps.setTimestamp(10, java.sql.Timestamp.valueOf(LocalDateTime.now()));
			}
			ps.setInt(11, rg.getId());
			if (ps.executeUpdate() == 1)
				System.out.println("Update success");
		} catch (SQLException e) {
			System.out.println("Update Fail");
			e.printStackTrace();
		}
	}

	@Override
	public void delete(RegRadio t) {
		String sqlString = "DELETE FROM regradios WHERE idregradio=?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sqlString);
			ps.setInt(1, t.getId());
			if (ps.executeUpdate() == 1) {
				System.out.println("Deletion success");
			}
			ps.close();
		} catch (Exception e) {
			System.err.println("Delete failed");
		}
	}
}