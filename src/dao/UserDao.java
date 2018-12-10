package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import db.DbConnectionManager;
import model.Substance;
import model.User;
import model.User;

/**
 * DAO for the persistent handling of a User object. It manages all CRUD
 * operations and conversion between the object world user and the relational
 * version user (DB version). Due to the use of a DbConnectionManager the DAO
 * doesen't need to use, or even know, about any of lower level connections to
 * the Database. It 'speeks' in Objects with the object world (Domain model)and
 * in relational sql strings, tables, columns and result sets with the database.
 * 
 * @author kristersundlof
 *
 */
public class UserDao implements IDao<User> {

	DbConnectionManager conn = null;

	public UserDao() {
		conn = DbConnectionManager.getInstance();
	}

	@Override
	public User get(int id) throws NoSuchElementException {
		User user = null;
		try {
			ResultSet resultSet = conn.excecuteQuery("SELECT * FROM users WHERE idUser=" + id);
			if (!resultSet.next())
				throw new NoSuchElementException("The user with id " + id + " doesen't exist in database");
			else
				user = new User(resultSet.getInt(1), resultSet.getString(2));

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public List<User> getAll() {

		ArrayList<User> list = new ArrayList<>();

		try {
			ResultSet resultSet = conn.excecuteQuery("SELECT * FROM users");
			while (resultSet.next()) {
				list.add(new User(resultSet.getInt(1), resultSet.getString(2)));

			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	 	public boolean save(User t) {
		PreparedStatement ps = null;
		boolean saveSucess = false;
		try {
			String queryString = "INSERT INTO users (signature) " + "VALUES (?)";
			ps = conn.prepareStatement(queryString);
			ps.setString(1, t.getSignature());
			if(ps.executeUpdate() == 1)
				saveSucess = true;
			} catch (Exception e) {
				System.err.println("Could not save");
		}
		System.out.println("Save Sucess");
		return saveSucess;
	}

	/**
	 * This method uses a temporary User set with the desired changed values. It
	 * must have a 'id' that corresponds to a existing record in the database. The
	 * String array provides the attribute names of User class that is subject to
	 * change. Do not use the column names from the table, this will increase
	 * coupling and is bad. The method should make the coupling between the Users
	 * attribute and corresponding column name in table users, it should be the same
	 * but there's no guarantee. In this way the calling object need not to know
	 * anything about the construction of the database table, and that is a good
	 * thing.
	 * 
	 * @param t      - an instance of a User with new values on attributes but an
	 *               'id' identical to an existing user in the DB
	 * @param params - an array with the attribute names of the user that is subject
	 *               to change with this update.
	 */
	@Override
	public void update(User t, String[] params) {
		PreparedStatement preparedStatement = null;
		User s = get(t.getId());

		for (String p : params) {

			if (p.equals("signature"))
				s.setSignature(t.getSignature());
			try {
				preparedStatement = conn
						.prepareStatement("UPDATE users SET signature=? WHERE idUser=?");
				preparedStatement.setString(1, s.getSignature());
				preparedStatement.setInt(2, s.getId());

				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void delete(User t) {
		PreparedStatement preparedStatement = null;
		String sqlString = "DELETE FROM users WHERE idUser=?";
		try {
			preparedStatement = conn.prepareStatement(sqlString);
			preparedStatement.setInt(1, t.getId());
			if (preparedStatement.executeUpdate() == 1) {
			System.out.println("Deletion success");
			preparedStatement.close();
			}
		} catch (SQLException e) {
			System.err.println("Delete failed");
		}
	}
	public String deleteCurrentUser() {
		PreparedStatement preparedStatement = null;
		String sqlString = "UPDATE users SET current=0 Where current=1";
		try {
			preparedStatement = conn.prepareStatement(sqlString);
			if (preparedStatement.executeUpdate() == 1) {
				System.out.println("Current User Deletion success");
				preparedStatement.close();
			}
		} catch (SQLException e) {
			System.err.println("Current User Delete failed");
		}
		return sqlString;	
	}
	
	public String updateCurrentUser(String t) {
		PreparedStatement preparedStatement = null;
		String sqlString = "UPDATE users SET current=1 Where signature='"+t+"'";
		try {
			preparedStatement = conn.prepareStatement(sqlString);
			if (preparedStatement.executeUpdate() == 1) {
				System.out.println("Current User Deletion success");
				preparedStatement.close();
			}
		} catch (SQLException e) {
			System.err.println("Current User Delete failed");
		}
		return t;	
	}
	
	public User getCurrent(int id) throws NoSuchElementException {
		User user = null;
		try {
			ResultSet resultSet = conn.excecuteQuery("SELECT * FROM users WHERE current=" + id);
			if (!resultSet.next())
				throw new NoSuchElementException("The user with id " + id + " doesen't exist in database");
			else
				user = new User(resultSet.getInt(1), resultSet.getString(2));

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}
}
