package data.dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
	int add(T item) throws SQLException;
	int update(T item) throws SQLException;
	int delete(T item) throws SQLException;
	T getById(int id) throws SQLException;
	List<T> getAll() throws SQLException;
	List<T> getWhere(String clauses) throws SQLException;
}
