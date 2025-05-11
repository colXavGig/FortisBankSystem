package utils;


public interface IFileManager<T> {
	void serialize(T item) throws UtilException;
	T deSerialize() throws UtilException;
}
