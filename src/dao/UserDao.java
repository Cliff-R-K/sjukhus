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

	@Override
	public void update(User t, String[] params) {
		PreparedStatement preparedStatement = null;
		User s = get(t.getId());

		for (String p : params) {

			if (p.equals("signature"))
				s.setSignature(t.getSignature());
			try {
				preparedStatement = conn.prepareStatement("UPDATE users SET signature=? WHERE iduser=?");
				preparedStatement.setString(1, s.getSignature());
				preparedStatement.setInt(2, s.getId());
				if(preparedStatement.executeUpdate() == 1) {
					System.out.println("Uppdateringen lyckades");
				}
				
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
	/*
	public String changeCurrentUser() {
		PreparedStatement preparedStatement = null;
		String sqlStringchange1 = "UPDATE users SET current=0 Where current=1";
		String sqlStringchange2 = "UPDATE users SET current=0 Where current=1";

		try {
			preparedStatement = conn.prepareStatement(sqlStringchange1);
			if (preparedStatement.executeUpdate() == 1) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
			System.err.println("Current User change failed");
		}
		return sqlStringchange1;	
	}
	*/
	
	public String updateCurrentUser(String t) {
		PreparedStatement preparedStatement = null;
		String sqlStringChange1 = "UPDATE users SET current=0 Where current=1";
		String sqlStringChange2 = "UPDATE users SET current=1 Where signature='"+t+"'";
		try {
			preparedStatement = conn.prepareStatement(sqlStringChange1);
			if (preparedStatement.executeUpdate() == 1) {
				System.out.println("loggas ut...");
				preparedStatement.close();
			}
			preparedStatement = conn.prepareStatement(sqlStringChange2);
			if (preparedStatement.executeUpdate() == 1) {
				System.out.println(t+" loggas in...");
				preparedStatement.close();
			}
		} catch (SQLException e) {
			System.err.println("Current User Delete failed");
		}
		return t;	
	}
	
	public User getUserByName(String name) {
		User user = null;
		try {
			ResultSet resultSet = conn.excecuteQuery("SELECT * FROM users WHERE signature=" + name);
			if (!resultSet.next())
				throw new NoSuchElementException("The user with name " + name + " doesen't exist in database");
			else
				user = new User(resultSet.getInt(1), resultSet.getString(2));

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}
  
	public User getCurrent(int id) throws NoSuchElementException {
		User user = null;
		try {
			ResultSet resultSet = conn.excecuteQuery("SELECT * FROM users WHERE current='" + id+"'");
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

