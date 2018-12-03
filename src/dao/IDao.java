package dao;

import java.util.List;
/**
 * Interface for all CRUD operations of DAO classes
 * @author awi
 *
 * @param <T> One of the intended model classes that a DAO
 * implementation should be able to handle
 */

public interface IDao<T> {
	
	T get(int id);
	List<T> getAll();
	boolean save(T t);
	void update(T t, String[] params);
	void delete(T t);
}
