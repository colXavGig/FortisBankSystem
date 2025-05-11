package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileManager<T> implements IFileManager<T> {

	private String filePath;

	public FileManager(Class<T> type) {
		filePath = "./src/main/java/data/" +  type.getSimpleName() + ".ser";

	}
	
	protected FileManager(String fileName) {
		filePath = "./src/main/java/data/" +  fileName + ".ser";
	}
	
	/**
	 * @param item item to serialize
	 * @throws UtilException if an error occurs during serialization, such as file not found or IO exception
	 */
	public void serialize(T item) throws UtilException {
		try (var fStream = new FileOutputStream(filePath)) {
			try (var oStream = new ObjectOutputStream(fStream)) {
				oStream.writeObject(item);			
			} catch (IOException e) {
				throw new UtilException("Error serializing file: " + e.getMessage(), e);
			}
		} catch (FileNotFoundException e) {
			throw new UtilException("File not found", e);
		} catch (IOException e) {
			throw new UtilException("Error serializing file: " + e.getMessage(), e);
		}
	}

	/**
	 * @return item deserialized
	 * @throws UtilException if an error occurs during deserialization, such as file not found or IO exception
	 */
	@SuppressWarnings("unchecked")
	public T deSerialize() throws UtilException {
		var file = new File(filePath);
		T item = null;
		if (!file.exists()) {
			throw new UtilException("No file to deserialize");
		}
		
		try (var fStream = new FileInputStream(file)) {
			try (var oStream = new ObjectInputStream(fStream)) {
				try {
					item = (T) oStream.readObject();
				} catch (ClassNotFoundException | IOException e) {
					throw new UtilException("Error deserializing file", e);
				} finally {
					oStream.close();
				}
			}
		} catch (IOException e) {
			throw new UtilException("Error deserializing file", e);
		}
		return item;
	}

}
