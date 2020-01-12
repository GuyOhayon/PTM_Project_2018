package server;

/**
 * @author Nathan Dillbary, ID 037070489 / Guy Ohayon, ID 301851713 listen for
 * connections and invoke component in charge of handling client requests
 */
public interface Server {

	/**
	 * @param clientHandler
	 */
	public void start(ClientHandler clientHandler);

	public void stop();
}
