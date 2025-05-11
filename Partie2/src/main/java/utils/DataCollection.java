package utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public abstract class DataCollection<T> {

	protected List<T> data = new ArrayList<>();
	protected CollectionFileManager<T> fileManager;
	
	/**
	 * constructor of the collection, it will create a new collection with the file manager
	 * @param fileManager the file manager to use to serialize and de-serialize the data
	 */
	protected DataCollection(CollectionFileManager<T> fileManager) {
		this.fileManager = fileManager;
	}

	/**
	 * adding an item to the collection
	 * @param item the item to add
	 */
	public void add(T item) {
		data.add(item);
	}

	/**
	 * removing and item from a collection
	 * @param item to remove
	 */
	public void remove(T item) {
		data.remove(item);
	}

	/**
	 * update an item from the collection 
	 * @param index the index of the item to update
	 * @param item the new value of the item to update
	 */
	public void update(int index, T item) {
		if (index >= 0) {
			data.set(index, item);
		}
	}
	
	/**
	 * get a list contains every item of the collection
	 * @return a list of all the items in the collection
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * set a new list of T item has data in the collection
	 * @param data the new data to set as the collection
	 */
	public void setData(List<T> data) {
		this.data = data;
	}

	/**
	 * clear the collection, removing all of its data
	 */
	public void clear() {
		data.clear();
	}

	/**
	 * verify if the collection is empty
	 * @return true if the collection is empty and false if there is some item in it
	 */
	public boolean isEmpty() {
		return data.isEmpty();
	}

	/**
	 * get the number of item in the collection
	 * @return an int that represent the number of item in thge collection
	 */
	public int size() {
		return data.size();
	}

	/**
	 * verify if an item is present in the collection
	 * @param item the item to verify the presence in the collection
	 * @return
	 */
	public boolean contains(T item) {
		return data.contains(item);
	}

	/**
	 * get the index of an item
	 * @param item the item to find the index of
	 * @return the index of item
	 */
	public int indexOf(T item) {
		return data.indexOf(item);
	}
	
	/**
	 * get the item at the specified index
	 * @param index of the item to get
	 * @return the item at the index supplied
	 */
	public T get(int index) {
		return data.get(index);
	}
	
	/**
	 * remove an item from the collection that is at a specific index
	 * @param index the index of the item that needs to be removed
	 */
	public void remove(int index) {
		data.remove(index);
	}
	
	/**
	 * add a list of item into the collection
	 * @param items list of item to be added into the collection
	 */
	public void addAll(List<T> items) {
		data.addAll(items);
	}
	
	/**
	 * remove all the item specified in a list
	 * @param items the list of item to remove
	 */
	public void removeAll(List<T> items) {
		data.removeAll(items);
	}
	
	/**
	 * sorting the collection in a specific order
	 * @param comparator the logc used to compare the order of two items
	 */
	public void sort(Comparator<T> comparator) {
		data.sort(comparator);
	}

	/**
	 * get a list that contains the filtered elements of the collection
	 * @param predicate test if the item should be kept or not
	 * @return a list of all the items which the predicate was true
	 */
	public List<T> filter(Predicate<T> predicate) {
		List<T> filteredData = new ArrayList<>();
		for (T item : data) {
			if (predicate.test(item)) {
				filteredData.add(item);
			}
		}
		return filteredData;
	}

	/**
	 * save the collection into a serialize file
	 * @throws UtilException occurs when the file manager is not able 
	 * to serialize the data for reason like file not found or IO exception
	 */
	public void save() throws UtilException {
		fileManager.serialize(data);
	}
	
	/**
	 * load the data from a serialized file
	 */
	public void load() {
		try {
			data = fileManager.deSerialize();
		} catch (Exception e) {
			data = new ArrayList<>();
		}
	}
}
