package server;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Nathan Dillbary, ID 037070489 / Guy Ohayon, ID 301851713 
 * This interface defines common behavior for communicating with clients over a network
 */
public interface ClientHandler {

	/**
	 * @param inFromClient
	 * @param outToClient
	 */
	public void handleClient(InputStream inFromClient, OutputStream outToClient);
}
