package dao;

import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;

import db.DbConnectionManager;
import javafx.util.converter.LocalDateTimeStringConverter;
import model.Calibration;
import model.Radiopharmaceutical;
import model.RegRadio;
import model.RegRadio;
import model.Room;
import model.Substance;
import model.Supplier;
import model.User;

/**
 * DAO for the persistent handling of a Student object. It manages all CRUD
 * operations and conversion between the object world student and the relational
 * version student (DB version). Due to the use of a DbConnectionManager the DAO
 * doesen't need to use, or even know, about any of lower level connections to
 * the Database. It 'speeks' in Objects with the object world (Domain model)and
 * in relational sql strings, tables, columns and result sets with the database.
 * 
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
				while(rs.next()) {
				Radiopharmaceutical radiopharmaceutical = new RadiopharmaceuticalDao().get(rs.getInt(7));
				Room room = new RoomDao().get(rs.getInt(8));
				User user = new UserDao().get(rs.getInt(9));
				Calibration calibration = new CalibrationDao().get(rs.getInt(10));
				rp = new RegRadio(rs.getInt(1),rs.getDouble(2), rs.getTimestamp(3).toLocalDateTime(), rs.getDate(4),
						rs.getString(5), rs.getString(6), radiopharmaceutical,
						room, user, calibration);		
				radioList.add(rp);
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
	
	public ArrayList<RegRadio> getSearchedRegRadios(Date startDate, Date endDate) {
		RegRadio rp = null;
		ArrayList<RegRadio> searchedList = new ArrayList<>();
		try {
			String sqlString = "select * from regradios where arrival_date BETWEEN \""+startDate+"\"" +" AND "+"\""+endDate+"\"";
			ResultSet rs = conn.excecuteQuery(sqlString);
				while(rs.next()) {
				Radiopharmaceutical radiopharmaceutical2 = new RadiopharmaceuticalDao().get(rs.getInt(7));
				Room room2 = new RoomDao().get(rs.getInt(8));
				User user2 = new UserDao().get(rs.getInt(9));
				Calibration calibration = rs.getInt(10) != 0 ? new CalibrationDao().get(rs.getInt(10)):null;
				rp = new RegRadio(rs.getInt(1),rs.getDouble(2), rs.getTimestamp(3).toLocalDateTime(), rs.getDate(4),
						rs.getString(5), rs.getString(6), radiopharmaceutical2,
						room2, user2, calibration);		
				searchedList.add(rp);
				}
//			}
			conn.close();
		} catch (SQLException e) {
			System.err.println("Ingen registrerade Läkemedel  mellan" + startDate + endDate + " hittades!");
		}
		return searchedList;
	}
	
	public ArrayList<RegRadio> getSearchedRegRadios(Date startDate, Date endDate, Radiopharmaceutical radiopharmaceutical, Room room, User user) {
		RegRadio rp = null;
		ArrayList<RegRadio> searchedList = new ArrayList<>();
		try {
		String sqlString = null;
		if(radiopharmaceutical != null && room == null && user == null) {
			//KOD
			System.out.println("radiopharmaceutical != null && room == null && user == null");
			sqlString = "SELECT * FROM regradios "
			+ "JOIN radiopharmaceuticals ON regradios.radiopharmaceuticals_idradio = radiopharmaceuticals.idradio "
			+ "JOIN rooms ON regradios.rooms_idroom = rooms.idroom "
			+ "JOIN users ON regradios.users_iduser = users.iduser "
			+ " WHERE arrival_date BETWEEN \""+startDate+"\"" +" AND "+"\""+endDate+"\""
			+ " AND radiopharmaceuticals_idradio = "+"\""+radiopharmaceutical.getId()+"\"";
		}else if(radiopharmaceutical != null && room != null && user == null) {
			//KOD
			System.out.println("radiopharmaceutical != null && room != null && user == null");
		}else if(radiopharmaceutical != null && room == null && user != null) {
			//KOD
			System.out.println("radiopharmaceutical != null && room == null && user != null");
		}else if(radiopharmaceutical == null && room != null && user == null) {
			//KOD
			System.out.println("radiopharmaceutical == null && room != null && user == null");
		}else if(radiopharmaceutical == null && room != null && user != null) {
			//KOD
			System.out.println("radiopharmaceutical == null && room != null && user != null");
		}else if(radiopharmaceutical == null && room == null && user != null) {
			//KOD
			System.out.println("radiopharmaceutical == null && room == null && user != null");
		}else {
			System.out.println("else");
			sqlString = "SELECT * FROM regradios "
					+ "JOIN radiopharmaceuticals ON regradios.radiopharmaceuticals_idradio = radiopharmaceuticals.idradio "
					+ "JOIN rooms ON regradios.rooms_idroom = rooms.idroom "
					+ "JOIN users ON regradios.users_iduser = users.iduser "
					+ " WHERE arrival_date BETWEEN \""+startDate+"\"" +" AND "+"\""+endDate+"\"";
		}
			System.out.println(sqlString);
			 ResultSet rs = conn.excecuteQuery(sqlString);
				while(rs.next()) {
				Radiopharmaceutical radiopharmaceutical2 = new RadiopharmaceuticalDao().get(rs.getInt(7));
				Room room2 = new RoomDao().get(rs.getInt(8));
				User user2 = new UserDao().get(rs.getInt(9));
				Calibration calibration = rs.getInt(10) != 0 ? new CalibrationDao().get(rs.getInt(10)):null;
				rp = new RegRadio(rs.getInt(1),rs.getDouble(2), rs.getTimestamp(3).toLocalDateTime(), rs.getDate(4),
						rs.getString(5), rs.getString(6), radiopharmaceutical2,
						room2, user2, calibration);		
				searchedList.add(rp);
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
				throw new NoSuchElementException("The radiopharmaceutical with id " + id + " doesen't exist in database");
			else {
				Radiopharmaceutical radiopharmaceutical = new RadiopharmaceuticalDao().get(rs.getInt(7));
				Room room = new RoomDao().get(rs.getInt(8));
				User user = new UserDao().get(rs.getInt(9));
				Calibration calibration = rs.getInt(10) != 0 ? new CalibrationDao().get(rs.getInt(10)):null;	
				regRadio = new RegRadio(rs.getInt(1),rs.getDouble(2), rs.getTimestamp(3).toLocalDateTime(), rs.getDate(4),
						rs.getString(5), rs.getString(6), radiopharmaceutical,
						room, user, calibration);						
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
				
				Calibration calibration = rs.getInt(10) != 0 ? new CalibrationDao().get(rs.getInt(10)):null;
				list.add(new RegRadio(rs.getInt(1),rs.getDouble(2), rs.getTimestamp(3).toLocalDateTime(), rs.getDate(4),
						rs.getString(5), rs.getString(6), radiopharmaceutical,
						room, user, calibration));

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
				
				Calibration calibration = rs.getInt(10) != 0 ? new CalibrationDao().get(rs.getInt(10)):null;
				list.add(new RegRadio(rs.getInt(1),rs.getDouble(2), rs.getTimestamp(3).toLocalDateTime(), rs.getDate(4),
						rs.getString(5), rs.getString(6), radiopharmaceutical,
						room, user, calibration));

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
				queryString = "INSERT INTO regradios (start_activity, start_date, arrival_date, batchnumber, con_controll, radiopharmaceuticals_idradio, rooms_idroom, users_iduser, calibrations_idcalibration) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(queryString);
			ps.setDouble(1, t.getStartActivity());
			ps.setTimestamp(2, java.sql.Timestamp.valueOf(t.getStartDate()));
			ps.setDate(3, new java.sql.Date(t.getArrivalDate().getTime()));
			ps.setString(4, t.getBatchNumber());
			ps.setString(5, t.getContaminationControll());
			ps.setInt(6, t.getRadiopharmaceutical().getId());
			ps.setInt(7, t.getRoom().getId());
			ps.setInt(8, t.getUser().getId());
			if(t.getCalibration() != null)
			ps.setInt(9, t.getCalibration().getId());
			else {
				ps.setNull(9, java.sql.Types.INTEGER);
			}
			if(ps.executeUpdate() == 1) {
				System.out.println("Save Success");
				saveSucess = true;
			}
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println("Could not save");
		}
		return saveSucess;
	}

	/**
	 * This method uses a temporary Student set with the desired changed values. It
	 * must have a 'id' that corresponds to a existing record in the database. The
	 * String array provides the attribute names of Student class that is subject to
	 * change. Do not use the column names from the table, this will increase
	 * coupling and is bad. The method should make the coupling between the Students
	 * attribute and corresponding column name in table students, it should be the
	 * same but there's no guarantee. In this way the calling object need not to
	 * know anything about the construction of the database table, and that is a
	 * good thing.
	 * 
	 * @param t      - an instance of a Student with new values on attributes but an
	 *               'id' identical to an existing student in the DB
	 * @param params - an array with the attribute names of the student that is
	 *               subject to change with this update.
	 */

	@Override
	public void update(RegRadio t, String[] params) {
		/*PreparedStatement ps = null;
		RegRadio k = get(t.getId());

		for (String p : params) {

			if (p.equals("RegRadioKod")) {
				k.setRegRadioKod(t.getRegRadioKod());
			} else if (p.equals("RegRadioNamn")) {
				k.setRegRadioNamn(t.getRegRadioNamn());
			} else if (p.equals("anmälningskod")) {
				k.setAnmälningsKod(t.getAnmälningsKod());
			} else if (p.equals("fart")) {
				k.setFart(t.getFart());
			} else if (p.equals("hp")) {
				k.setHp(t.getHp());
			} else if (p.equals("RegRadioschema")) {
				k.setRegRadioschema(t.getRegRadioschema());
			} else if (p.equals("RegRadioplan")) {
				k.setRegRadioPlan(t.getRegRadioPlan());
			} else if (p.equals("anmälningsDatum")) {
				k.setAnmälningsDatum(t.getAnmälningsDatum());
			} else if (p.equals("betyg")) {
				k.setBetyg(t.getBetyg());
			}
		}

		try {
			String sqlQuery = "UPDATE regradios SET RegRadiokod=?, RegRadionamn=?, anmälningskod=?, fart=?, hp=?, RegRadioschema=?, "
					+ "RegRadioplan=?, anmälningsdatum=?, betyg=? WHERE idStudent=?";
			ps = conn.prepareStatement(sqlQuery);
					
			ps.setString(1, k.getRegRadioKod());
			ps.setString(2, k.getRegRadioNamn());
			ps.setInt(3, k.getAnmälningsKod());
			ps.setString(4, k.getFart());
			ps.setDouble(5, k.getHp());
			ps.setString(6, k.getRegRadioschema());
			ps.setString(7, k.getRegRadioPlan());
			ps.setString(8, k.getAnmälningsDatum());
			ps.setInt(9, k.getBetyg().getId());
			ps.executeUpdate();

			if (ps.executeUpdate() == 1)
				System.out.println("Update success");
		} catch (SQLException e) {
			System.out.println("Update Fail");
			e.printStackTrace();
		}
*/
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