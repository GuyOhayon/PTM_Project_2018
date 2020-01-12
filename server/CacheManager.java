package server;

import java.io.IOException;

/**
 * @author Nathan Dillbary, ID 037070489 / Guy Ohayon, ID 301851713 
 * This interface declares persistence operations for solution management
 * 
 * @param fileName
 * @param T type parameter of a the file
 */
public interface CacheManager<T> {

	public T loadFile(String fileName) throws Exception;

	public void saveFile(String fileName, T file) throws IOException;

	public boolean isFileExist(String fileName);
}
