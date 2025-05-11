/**
 * 
 */
package utils;

import java.util.List;

/**
 * 
 */
public class CollectionFileManager<T> extends FileManager<List<T>> {

	/**
	 * @param type type of the collection
	 */
	public CollectionFileManager(Class<T> type ) {
		super(type.getSimpleName());
	}
}
