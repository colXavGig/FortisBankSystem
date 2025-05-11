package bus.model;

public interface Identifiable<T> {
	/**
	 * @return the id of the object
	 */
	public T getID();

	/**
	 * @param id the id to set
	 */
	public void setID(T id);

	/**
	 * @return true if the object has an id
	 */
	public boolean hasID();

}
