package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import db.DbConnectionManager;
import model.Student;

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
public class StudentDao implements IDao<Student> {

	DbConnectionManager dbConManagerSingleton = null;

	public StudentDao() {
		dbConManagerSingleton = DbConnectionManager.getInstance();
	}

	@Override
	public Student get(int id) throws NoSuchElementException {
		Student student = null;
		try {
			// ResultSet resultSet = dbConManagerSingleton.excecuteQuery("SELECT id, name,
			// birth_year FROM students WHERE id=" + id);
			ResultSet resultSet = dbConManagerSingleton.excecuteQuery("SELECT * FROM Student WHERE idStudent=" + id);
			if (!resultSet.next())
				throw new NoSuchElementException("The student with id " + id + " doesen't exist in database");
			else
				student = new Student(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));

			dbConManagerSingleton.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return student;
	}

	@Override
	public List<Student> getAll() {

		ArrayList<Student> list = new ArrayList<>();

		try {
			ResultSet resultSet = dbConManagerSingleton.excecuteQuery("SELECT * FROM Student");
			while (resultSet.next()) {
				list.add(new Student(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getString(5), resultSet.getString(6).trim()));

			}
			dbConManagerSingleton.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean save(Student t) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int rowCount = 0;
		boolean saveSucess = false;
		try {
			resultSet = dbConManagerSingleton.excecuteQuery("SELECT COUNT(idStudent) FROM Student");
			resultSet.next();
			rowCount = resultSet.getInt(1);
			System.out.println(rowCount);

			// *******This is the main 'save' operation ***************************
			preparedStatement = dbConManagerSingleton.prepareStatement(
					"INSERT INTO Student (f_namn, e_namn, adress, epost, mobilnr) " + "VALUES (?, ?, ?, ?, ?)");
			preparedStatement.setString(1, t.getF_namn());
			preparedStatement.setString(2, t.getE_namn());
			preparedStatement.setString(3, t.getAdress());
			preparedStatement.setString(4, t.getEpost());
			preparedStatement.setString(5, t.getMobilnr());

			preparedStatement.executeUpdate();
			// ********************************************************************

			resultSet = dbConManagerSingleton.excecuteQuery("SELECT COUNT(idStudent) FROM Student");
			resultSet.next();
			int newRowCount = resultSet.getInt(1);
			if (newRowCount == (rowCount + 1)) // Check if table is one more row after 'save'
				saveSucess = true;
			System.out.format("Previous row count: %d    Current row count: %d", rowCount, newRowCount);
		} catch (SQLException e) {
			e.printStackTrace();
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
	public void update(Student t, String[] params) {
		PreparedStatement preparedStatement = null;
		Student s = get(t.getId());

		for (String p : params) {

			if (p.equals("f�rnamn")) {
				s.setF_namn(t.getF_namn());
			} else if (p.equals("efternamn")) {
				s.setE_namn(t.getE_namn());
			} else if (p.equals("adress")) {
				s.setAdress(t.getAdress());
			} else if (p.equals("epost")) {
				s.setEpost(t.getEpost());
			} else if (p.equals("mobilnr")) {
				s.setMobilnr(t.getMobilnr());
			}
		}

		try {
			preparedStatement = dbConManagerSingleton.prepareStatement(
					"UPDATE Student SET f_namn=?, e_namn=?, adress=?, epost=?, mobilnr=? WHERE idStudent=?");
			preparedStatement.setString(1, s.getF_namn());
			preparedStatement.setString(2, s.getE_namn());
			preparedStatement.setString(3, s.getAdress());
			preparedStatement.setString(4, s.getEpost());
			preparedStatement.setString(5, s.getMobilnr());
			preparedStatement.setInt(6, s.getId());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	@Override
	public void delete(Student t) {
		PreparedStatement preparedStatement = null;
		Student s = get(t.getId());
		if (s != null) {

			ResultSet resultSet = null;
			int rowCount = 0;
			try {
				resultSet = dbConManagerSingleton.excecuteQuery("SELECT COUNT(idStudent) FROM Student");
				resultSet.next();
				rowCount = resultSet.getInt(1);
				System.out.println(rowCount);

				preparedStatement = dbConManagerSingleton.prepareStatement("DELETE FROM Student WHERE idStudent=?");
				preparedStatement.setInt(1, s.getId());
				preparedStatement.executeUpdate();

				resultSet = dbConManagerSingleton.excecuteQuery("SELECT COUNT(idStudent) FROM Student");
				resultSet.next();
				int newRowCount = resultSet.getInt(1);
				if (newRowCount == (rowCount - 1))
					System.out.format("Previous row count: %d    Current row count: %d", rowCount, newRowCount);
				System.out.println("Deleteion Success!");
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

}
