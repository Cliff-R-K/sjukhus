package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import db.DbConnectionManager;
import model.Calibration;
import model.Radiopharmaceutical;
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
public class RadiopharmaceuticalDao implements IDao<Radiopharmaceutical> {

	DbConnectionManager conn = null;

	public RadiopharmaceuticalDao() {
		conn = DbConnectionManager.getInstance();
	}

	@Override

	public Radiopharmaceutical get(int id) throws NoSuchElementException {
		Radiopharmaceutical radiopharmaceutical = null;
		try {
			String sqlString = "SELECT * FROM radiopharmaceuticals WHERE idradio=" + id;
			ResultSet rs = conn.excecuteQuery(sqlString);
			if (!rs.next())
				throw new NoSuchElementException("The radiopharmaceutical with id " + id + " doesen't exist in database");
			else {
				Substance substance = new SubstanceDao().get(rs.getInt(9));
				User user = new UserDao().get(rs.getInt(10));
				Calibration calibration = new CalibrationDao().get(rs.getInt(11));
				Room room = new RoomDao().get(rs.getInt(12));
				Supplier supplier = new SupplierDao().get(rs.getInt(13));	
				radiopharmaceutical = new Radiopharmaceutical(rs.getInt(1),rs.getString(2), rs.getDouble(3), rs.getDate(4),
						rs.getString(5),rs.getDate(6), rs.getString(7), rs.getString(8),
						substance, user, calibration, room, supplier);						
			}
			conn.close();
		} catch (SQLException e) {
			System.err.println("Ingen Läkemedel med " + id + " hittades!");
		}
		return radiopharmaceutical;
	}

	@Override
	public List<Radiopharmaceutical> getAll() {
		ArrayList<Radiopharmaceutical> list = new ArrayList<>();

		try {
			String sqlQuary = "SELECT * FROM radiopharmaceuticals";
			ResultSet rs = conn.excecuteQuery(sqlQuary);
			while (rs.next()) {
				Substance substance = new SubstanceDao().get(rs.getInt(9));
				User user = new UserDao().get(rs.getInt(10));
				Calibration calibration = null;
				int calibrationId = rs.getInt(11);
				if(!rs.wasNull()) {
					calibration = new CalibrationDao().get(calibrationId);
				}
				Room room = new RoomDao().get(rs.getInt(12));
				Supplier supplier = new SupplierDao().get(rs.getShort(13));
				list.add(new Radiopharmaceutical(rs.getInt(1),rs.getString(2), rs.getDouble(3), rs.getDate(4),
						rs.getString(5),rs.getDate(6), rs.getString(7), rs.getString(8),
						substance, user, calibration, room, supplier));

			}
			conn.close();
		} catch (SQLException e) {
			System.err.println("Inga Läkemedel hittades");
		}
		return list;
	}

	@Override
	public boolean save(Radiopharmaceutical t) {
		PreparedStatement ps = null;
		boolean saveSucess = false;
		try {
			String queryString;
			if (t.getCalibration() != null) {
				queryString = "INSERT INTO radiopharmaceuticals (name, start_activity, start_date, form, arrival_date, batchnumber, con_controll, substances_idsubstance, users_iduser, calibrations_idcalibration, rooms_idroom, suppliers_idsupplier) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				} else {
				queryString = "INSERT INTO radiopharmaceuticals (name, start_activity, start_date, form, arrival_date, batchnumber, con_controll, substances_idsubstance, users_iduser, rooms_idroom, suppliers_idsupplier) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			}
			ps = conn.prepareStatement(queryString);
			ps.setString(1, t.getRadiopharmaceuticalName());
			ps.setDouble(2, t.getStartActivity());
			ps.setDate(3, new java.sql.Date(t.getStartDate().getTime()));
			ps.setString(4, t.getForm());
			ps.setDate(5, new java.sql.Date(t.getArrivalDate().getTime()));
			ps.setString(6, t.getBatchNumber());
			ps.setString(7, t.getContaminationControll());
			ps.setInt(8, t.getSubstance().getId());
			ps.setInt(9, t.getUser().getId());
			if (t.getCalibration() != null) {
				ps.setInt(10, t.getCalibration().getId());
			}
			ps.setInt(11, t.getRoom().getId());
			ps.setInt(12,  t.getSupplier().getSupplierId());
			
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
	public void update(Radiopharmaceutical t, String[] params) {
		/*PreparedStatement ps = null;
		Radiopharmaceutical k = get(t.getId());

		for (String p : params) {

			if (p.equals("RadiopharmaceuticalKod")) {
				k.setRadiopharmaceuticalKod(t.getRadiopharmaceuticalKod());
			} else if (p.equals("RadiopharmaceuticalNamn")) {
				k.setRadiopharmaceuticalNamn(t.getRadiopharmaceuticalNamn());
			} else if (p.equals("anmälningskod")) {
				k.setAnmälningsKod(t.getAnmälningsKod());
			} else if (p.equals("fart")) {
				k.setFart(t.getFart());
			} else if (p.equals("hp")) {
				k.setHp(t.getHp());
			} else if (p.equals("Radiopharmaceuticalschema")) {
				k.setRadiopharmaceuticalschema(t.getRadiopharmaceuticalschema());
			} else if (p.equals("Radiopharmaceuticalplan")) {
				k.setRadiopharmaceuticalPlan(t.getRadiopharmaceuticalPlan());
			} else if (p.equals("anmälningsDatum")) {
				k.setAnmälningsDatum(t.getAnmälningsDatum());
			} else if (p.equals("betyg")) {
				k.setBetyg(t.getBetyg());
			}
		}

		try {
			String sqlQuery = "UPDATE radiopharmaceuticals SET Radiopharmaceuticalkod=?, Radiopharmaceuticalnamn=?, anmälningskod=?, fart=?, hp=?, Radiopharmaceuticalschema=?, "
					+ "Radiopharmaceuticalplan=?, anmälningsdatum=?, betyg=? WHERE idStudent=?";
			ps = conn.prepareStatement(sqlQuery);
					
			ps.setString(1, k.getRadiopharmaceuticalKod());
			ps.setString(2, k.getRadiopharmaceuticalNamn());
			ps.setInt(3, k.getAnmälningsKod());
			ps.setString(4, k.getFart());
			ps.setDouble(5, k.getHp());
			ps.setString(6, k.getRadiopharmaceuticalschema());
			ps.setString(7, k.getRadiopharmaceuticalPlan());
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
	public void delete(Radiopharmaceutical t) {
		String sqlString = "DELETE FROM radiopharmaceuticals WHERE idRadiopharmaceutical=?";
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