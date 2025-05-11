package utils;

import java.util.List;

import bus.model.Identifiable;
import bus.service.IdentityInitiator;

public abstract class IdentifiableCollection<T extends Identifiable<Integer>> extends DataCollection<T> {
	
	protected IdentifiableCollection(CollectionFileManager<T> fileManager) {
		super(fileManager);
	}
	
	/**
	 * Adds an item to the collection and initializes its ID if it doesn't have one.
	 * @param item the item to add
	 * @throws IllegalArgumentException if the item already has an ID
	 */
	@Override
	public void add(T item) {
		if (item.hasID()) {
			throw new IllegalArgumentException("ID already set");
		}
		IdentityInitiator.initID(item);
		super.add(item);
	}
	
	/**
	 * Finds the index of the item with the given ID in the collection.
	 * @param id
	 * @return the index of the item with the given ID, or null if not found
	 */
	public Integer indexOfID(Integer id) {
		for (int i = 0; i < getData().size(); i++) {
			if (getData().get(i).getID().equals(id)) {
				return i;
			}
		}
		return null;
	}
	
	/**
	 * Updates the item in the collection if it exists.
	 * @param item the item to update
	 * @return true if the item was updated, false if not found
	 */
	public boolean update(T item) {
		var index = indexOfID(item.getID());
		if (index != null) {
			data.set(index.intValue(), item);
			return true;
		}
		return false;
	}
	
	/**
	 * Removes the item from the collection.
	 * @param item the item to remove
	 */
	@Override
	public void remove(T item) {
		var index = indexOfID(item.getID());
		super.remove(index.intValue());
	}
	

	/**
	 * Gets the item with the given ID from the collection.
	 * @param id the ID of the item to get
	 * @return the item with the given ID, or null if not found
	 */
	public T getByID(Integer id) {
		try {
			return data.get(indexOfID(id));
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	/**
	 * Checks if the collection contains an item with the given ID.
	 * @param id the ID of the item to check
	 * @return true if the item with the given ID is in the collection, false otherwise
	 */
	public boolean containsID(Integer id) {
		return indexOfID(id) != null;
	}
	
	/**
	 * Adds all items to the collection.
	 * @param id the ID of the item to get
	 * @return the item with the given ID, or null if not found
	 */
	@Override
	public void addAll(List<T> items) {
		for (T item : items) {
			add(item);
		}
	}
	
	/**
	 * Removes all items from the collection.
	 * @param items the items to remove
	 */
	public void removeAll(List<T> items) {
		for (T item : items) {
			remove(item);
		}
	}

}
