/*
 * 
 * 
 * 
 */
package tirserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Reception.java
 *
 */
public class Reception extends Observable implements Runnable {

    private final static int PORT = 43666;

    private final ServerSocket socketServer;
    private final Thread thread;
    private boolean run;

    public Reception() throws IOException {
	thread = new Thread(this);
	socketServer = new ServerSocket(PORT);
    }

    public void start() {
	run = true;
	thread.start();
    }

    @Override
    public void run() {
	System.out.println("En attente de connexions sur le port " + PORT);
	try {
	    while (run) {
		Socket soc = socketServer.accept();
		System.out.println("Connexion reçue !");
		setChanged();
		notifyObservers(soc);
	    }
	} catch (IOException ex) {
	    Logger.getLogger(Reception.class.getName()).log(Level.SEVERE, null, ex);
	}
	try {
	    socketServer.close();
	} catch (IOException ex) {
	    Logger.getLogger(Reception.class.getName()).log(Level.SEVERE, null, ex);
	}
	System.out.println("Serveur arrêté.");

    }

    public void stop() {
	System.out.println("Arrêt du serveur.");
	run = false;
    }

}
