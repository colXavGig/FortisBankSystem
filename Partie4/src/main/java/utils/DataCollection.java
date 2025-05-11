package utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class DataCollection<T> {

	
	/**
	 * constructor of the collection, it will create a new collection with the file manager
	 * @param fileManager the file manager to use to serialize and de-serialize the data
	 */
	protected DataCollection() {}

	/**
	 * adding an item to the collection
	 * @param item the item to add
	 * @throws SQLException 
	 */
	public abstract void add(T item) throws SQLException;

	/**
	 * removing and item from a collection
	 * @param item to remove
	 * @throws SQLException 
	 */
	public abstract void remove(T item) throws SQLException;

	/**
	 * update an item from the collection 
	 * @param index the index of the item to update
	 * @param item the new value of the item to update
	 * @throws SQLException 
	 */
	public abstract void update(T item) throws SQLException;
	
	/**
	 * get a list contains every item of the collection
	 * @return a list of all the items in the collection
	 * @throws SQLException 
	 */
	public abstract List<T> getData() throws SQLException;


	/**
	 * verify if the collection is empty
	 * @return true if the collection is empty and false if there is some item in it
	 * @throws SQLException 
	 */
	public abstract boolean isEmpty() throws SQLException;

	/**
	 * verify if an item is present in the collection
	 * @param item the item to verify the presence in the collection
	 * @return
	 * @throws SQLException 
	 */
	public abstract boolean contains(T item) throws SQLException;

	/**
	 * get the item at the specified index
	 * @param index of the item to get
	 * @return the item at the index supplied
	 * @throws UtilException 
	 * @throws SQLException 
	 */
	public abstract T get(int id) throws UtilException, SQLException;
	
	/**
	 * remove an item from the collection that is at a specific index
	 * @param index the index of the item that needs to be removed
	 */
	public abstract void remove(int id);
	
	/**
	 * add a list of item into the collection
	 * @param items list of item to be added into the collection
	 * @throws SQLException 
	 */
	public void addAll(List<T> items) throws SQLException {
		for (T item : items) {
			add(item);
		}
	}
	
	/**
	 * remove all the item specified in a list
	 * @param items the list of item to remove
	 * @throws SQLException 
	 */
	public void removeAll(List<T> items) throws SQLException {
		for (T item : items) {
			remove(item);
		}
	}
	
	/**
	 * get a list that contains the filtered elements of the collection
	 * @param predicate test if the item should be kept or not
	 * @return a list of all the items which the predicate was true
	 * @throws SQLException 
	 */
	public List<T> filter(Predicate<T> predicate) throws SQLException {
		List<T> filteredData = new ArrayList<>();
		for (T item : getData()) {
			if (predicate.test(item)) {
				filteredData.add(item);
			}
		}
		return filteredData;
	}

}
