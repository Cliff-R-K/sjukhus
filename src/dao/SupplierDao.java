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
 * operations and conversion between the object world supplier and the
 * relational version supplier (DB version). Due to the use of a
 * DbConnectionManager the DAO doesen't need to use, or even know, about any of
 * lower level connections to the Database. It 'speeks' in Objects with the
 * object world (Domain model)and in relational sql strings, tables, columns and
 * result sets with the database.
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
		boolean saveSucess = false;
		try {
			preparedStatement = dbConManagerSingleton.prepareStatement("INSERT INTO suppliers (name) " + "VALUES (?)");
			preparedStatement.setString(1, t.getSupplierName());

			if (preparedStatement.executeUpdate() == 1)
				saveSucess = true;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Save Failed");
			return false;
		}
		System.out.println("Save Success");
		return saveSucess;
	}

	@Override
	public void update(Supplier t, String[] params) {
		PreparedStatement preparedStatement = null;
		Supplier s = get(t.getSupplierId());

		for (String p : params) {

			if (p.equals("name")) {
				s.setSupplierName((t.getSupplierName()));
			}

			try {
				preparedStatement = dbConManagerSingleton
						.prepareStatement("UPDATE suppliers SET name=? WHERE idSupplier=?");
				System.out.println(s.getSupplierId());
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
