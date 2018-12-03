package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import db.DbConnectionManager;
import model.Substance;

/**
 * DAO for the persistent handling of a Substance object. It manages all CRUD
 * operations and conversion between the object world Substance and the
 * relational version Substance (DB version). Due to the use of a
 * DbConnectionManager the DAO doesen't need to use, or even know, about any of
 * lower level connections to the Database. It 'speeks' in Objects with the
 * object world (Domain model)and in relational sql strings, tables, columns and
 * result sets with the database.
 * 
 * @author kristersundlof
 *
 */
public class SubstanceDao implements IDao<Substance> {

	DbConnectionManager conn = null;

	public SubstanceDao() {
		conn = DbConnectionManager.getInstance();
	}

	@Override
	public Substance get(int id) throws NoSuchElementException {
		Substance substance = null;
		try {
			ResultSet resultSet = conn.excecuteQuery("SELECT * FROM substances WHERE idSubstance=" + id);
			if (!resultSet.next())
				throw new NoSuchElementException("The Substance with id " + id + " doesen't exist in database");
			else
				substance = new Substance(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3));

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return substance;
	}

	@Override
	public List<Substance> getAll() {

		ArrayList<Substance> list = new ArrayList<>();

		try {
			ResultSet resultSet = conn.excecuteQuery("SELECT * FROM substances");
			while (resultSet.next()) {
				list.add(new Substance(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3)));

			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean save(Substance t) {
		PreparedStatement ps = null;
		boolean saveSucess = false;
		try {
			String queryString = "INSERT INTO substances (name, half_life) " + "VALUES (?, ?)";
			ps = conn.prepareStatement(queryString);
			ps.setString(1, t.getName());
			ps.setDouble(2, t.getHalfLife());
			if (ps.executeUpdate() == 1)
				saveSucess = true;
			System.out.println("Save Sucess");
		} catch (Exception e) {
			System.err.println("Could not save");
			e.printStackTrace();
		}
		return saveSucess;
	}

	/**
	 * This method uses a temporary Substance set with the desired changed values.
	 * It must have a 'id' that corresponds to a existing record in the database.
	 * The String array provides the attribute names of Substance class that is
	 * subject to change. Do not use the column names from the table, this will
	 * increase coupling and is bad. The method should make the coupling between the
	 * substances attribute and corresponding column name in table substances, it
	 * should be the same but there's no guarantee. In this way the calling object
	 * need not to know anything about the construction of the database table, and
	 * that is a good thing.
	 * 
	 * @param t      - an instance of a Substance with new values on attributes but
	 *               an 'id' identical to an existing Substance in the DB
	 * @param params - an array with the attribute names of the Substance that is
	 *               subject to change with this update.
	 */
	@Override
	public void update(Substance t, String[] params) {
		PreparedStatement preparedStatement = null;
		Substance s = get(t.getId());

		for (String p : params) {

			if (p.equals("name"))
				s.setName(t.getName());
			else if (p.equals("half_life"))
				s.setHalfLife(t.getHalfLife());
			try {
				preparedStatement = conn
						.prepareStatement("UPDATE substances SET name=?, half_life=? WHERE idSubstance=?");
				preparedStatement.setString(1, s.getName());
				preparedStatement.setDouble(2, s.getHalfLife());
				if (preparedStatement.executeUpdate() == 1)
					System.out.println("Update success");
			} catch (SQLException e) {
				System.out.println("Update Fail");
				e.printStackTrace();
			}
		}
	}

	@Override
	public void delete(Substance t) {
		PreparedStatement preparedStatement = null;
		String sqlString = "DELETE FROM substances WHERE idSubstance=?";
		try {
			preparedStatement = conn.prepareStatement(sqlString);
			preparedStatement.setInt(1, t.getId());
			if (preparedStatement.executeUpdate() == 1)
				;
			System.out.println("Deletion success");
			preparedStatement.close();
		} catch (SQLException e) {
			System.err.println("Delete failed");
		}

	}

}
