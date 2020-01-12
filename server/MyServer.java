package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * @author Nathan Dillbary, ID 037070489 / Guy Ohayon, ID 301851713 
 * Handles one client at a time according to a specified protocol until a termination order
 */
public class MyServer implements Server {
	/**
	 * port marked as final, listening socket and operational socket are bound to
	 * the same port clientHandler variable enables delegation (via strategy
	 * pattern)
	 */
	private final int port;
	private ClientHandler clientHandler;
	private volatile boolean stop;
	private static final int TIMEOUT = 1000;

	public MyServer(int port) {
		this.port = port;
		stop = false;
	}

	/**
	 * @throws Exception
	 */
	@Override
	public void start(ClientHandler clientHandler) {
		this.clientHandler = clientHandler;
		new Thread(() -> {
			try {
				runServer();
			} catch (Exception e) {
			}
		}).start();
	}

	@Override
	public void stop() {
		stop = true;

	}

	/**
	 * @throws Exception
	 */
	private void runServer() throws Exception {
		ServerSocket server = new ServerSocket(port);
		server.setSoTimeout(TIMEOUT);
		while (!stop) {
			try {
				Socket aClient = server.accept(); // blocking call
				try {
					clientHandler.handleClient(aClient.getInputStream(), aClient.getOutputStream());
					aClient.getInputStream().close();
					aClient.getOutputStream().close();
					aClient.close();
				} catch (IOException e) {
				}
			} catch (SocketTimeoutException e) {
			}
		}
		server.close();
	}
}
