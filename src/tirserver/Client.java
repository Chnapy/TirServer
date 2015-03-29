/*
 * 
 * 
 * 
 */
package tirserver;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;

/**
 * Client.java
 *
 */
public class Client extends Observable implements Runnable {
    
    private final Socket socketClient;
    private final InputReader input;
    private final PrintWriter output;
    
    private final Thread thread;
    private boolean run;
    
    private int id;
    private String pseudo;
    private int vie;
    private int munitions;
    private int puissance;
    private Point position; // Coordonnées x/y
    
    public Client(int id, Socket soc) throws IOException {
	socketClient = soc;
	input = new InputReader(new BufferedReader(new InputStreamReader(socketClient.getInputStream())));
	output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream())));

	thread = new Thread(this);
	
	this.id = id;
	pseudo = null;
	vie = 10;
	munitions = 10;
	puissance = 10;
	position = new Point();
    }
    
    public ListPaquet getListPaquet() {
	return input.getListPaquet();
    }
    
    public void envoi(String commande, String message) {
	output.println(commande + ':' + message);
	System.out.println(commande + ':' + message);
	output.flush();
    }
    
    public void start() {
	run = true;
	input.start();
	thread.start();
    }
    
    public void stop() throws IOException {
	run = false;
	input.stop();
	output.close();
	socketClient.close();
    }

    @Override
    public void run() {
	
//	while(run) {
	    Paquet paqPseudo = getListPaquet().waitPaquet("pseudo");
	    System.out.println(paqPseudo);
	    setChanged();
	    notifyObservers(paqPseudo);
//	}
	
    }

    public int getId() {
	return id;
    }

    public String getPseudo() {
	return pseudo;
    }
    
}
