package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import db.DbConnectionManager;
import model.Supplier;

/**
 * DAO for the persistent handling of a Supplier object. It manages all CRUD
 * operations and conversion between the object world supplier and the relational
 * version supplier (DB version). Due to the use of a DbConnectionManager the DAO
 * doesen't need to use, or even know, about any of lower level connections to
 * the Database. It 'speeks' in Objects with the object world (Domain model)and
 * in relational sql strings, tables, columns and result sets with the database.
 * 
 * @author kristersundlof
 *
 */
public class SupplierDao implements IDao<Supplier> {

	DbConnectionManager dbConManagerSingleton = null;

	public SupplierDao() {
		dbConManagerSingleton = DbConnectionManager.getInstance();
	}

	@Override
	public Supplier get(int id) throws NoSuchElementException {
		Supplier supplier = null;
		try {
			// ResultSet resultSet = dbConManagerSingleton.excecuteQuery("SELECT id, name,
			// birth_year FROM suppliers WHERE id=" + id);
			ResultSet resultSet = dbConManagerSingleton.excecuteQuery("SELECT * FROM suppliers WHERE idSupplier=" + id);
			if (!resultSet.next())
				throw new NoSuchElementException("The supplier with id " + id + " doesen't exist in database");
			else
				supplier = new Supplier(resultSet.getInt(1), resultSet.getString(2));

			dbConManagerSingleton.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return supplier;
	}

	@Override
	public List<Supplier> getAll() {

		ArrayList<Supplier> list = new ArrayList<>();

		try {
			ResultSet resultSet = dbConManagerSingleton.excecuteQuery("SELECT * FROM suppliers");
			while (resultSet.next()) {
				list.add(new Supplier(resultSet.getInt(1), resultSet.getString(2)));

			}
			dbConManagerSingleton.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean save(Supplier t) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int rowCount = 0;
		boolean saveSucess = false;
		try {
			resultSet = dbConManagerSingleton.excecuteQuery("SELECT COUNT(idSupplier) FROM suppliers");
			resultSet.next();
			rowCount = resultSet.getInt(1);
			System.out.println(rowCount);

			// *******This is the main 'save' operation ***************************
			preparedStatement = dbConManagerSingleton.prepareStatement(
					"INSERT INTO suppliers (name ) " + "VALUES (?)");
			preparedStatement.setString(1, t.getSupplierName());


			preparedStatement.executeUpdate();
			// ********************************************************************

			resultSet = dbConManagerSingleton.excecuteQuery("SELECT COUNT(idSupplier) FROM suppliers");
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
	 * This method uses a temporary Supplier set with the desired changed values. It
	 * must have a 'id' that corresponds to a existing record in the database. The
	 * String array provides the attribute names of Supplier class that is subject to
	 * change. Do not use the column names from the table, this will increase
	 * coupling and is bad. The method should make the coupling between the suppliers
	 * attribute and corresponding column name in table suppliers, it should be the
	 * same but there's no guarantee. In this way the calling object need not to
	 * know anything about the construction of the database table, and that is a
	 * good thing.
	 * 
	 * @param t      - an instance of a Supplier with new values on attributes but an
	 *               'id' identical to an existing supplier in the DB
	 * @param params - an array with the attribute names of the supplier that is
	 *               subject to change with this update.
	 */
	@Override
	public void update(Supplier t, String[] params) {
		PreparedStatement preparedStatement = null;
		Supplier s = get(t.getSupplierId());

		for (String p : params) {

			if (p.equals("name")) {
				s.setSupplierName((t.getSupplierName()));
			} 


			try {
				preparedStatement = dbConManagerSingleton.prepareStatement(
						"UPDATE suppliers SET name=? WHERE idSupplier=?");
				preparedStatement.setString(1, s.getSupplierName());
				preparedStatement.setInt(2, s.getSupplierId());
				preparedStatement.executeUpdate();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	@Override
	public void delete(Supplier t) {
		PreparedStatement preparedStatement = null;
		Supplier s = get(t.getSupplierId());
		if (s != null) {

			ResultSet resultSet = null;
			int rowCount = 0;
			try {
				resultSet = dbConManagerSingleton.excecuteQuery("SELECT COUNT(idSupplier) FROM suppliers");
				resultSet.next();
				rowCount = resultSet.getInt(1);
				System.out.println(rowCount);

				preparedStatement = dbConManagerSingleton.prepareStatement("DELETE FROM suppliers WHERE idSupplier=?");
				preparedStatement.setInt(1, s.getSupplierId());
				preparedStatement.executeUpdate();

				resultSet = dbConManagerSingleton.excecuteQuery("SELECT COUNT(idSupplier) FROM suppliers");
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
