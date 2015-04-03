/*
 * 
 * 
 * 
 */
package tirserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * InputReader.java
 *
 */
public class InputReader implements Runnable {

    private final Thread thread;
    private final BufferedReader input;
    private final ListPaquet listPaquet = new ListPaquet();
    private int id;
    private boolean run;

    public InputReader(BufferedReader in, int ide) {
	thread = new Thread(this);
	input = in;
	id = ide;
    }

    @Override
    public void run() {
	String reception;
	try {
	    while (run) {
		reception = input.readLine();
		if (!reception.isEmpty() && isForMe(reception)) {
		    listPaquet.add(new Paquet(reception));
		    System.out.println(listPaquet.size() + " " + reception);
		}
	    }
	} catch (IOException ex) {
	    if (run) {
		System.err.println("Client déconnecté.");
	    }
	}
	System.err.println("InputReader stoppé.");
    }

    public void start() {
	run = true;
	thread.start();
    }

    public void stop() {
	run = false;
	try {
	    input.close();
	} catch (IOException ex) {
	    Logger.getLogger(InputReader.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public ListPaquet getListPaquet() {
	return listPaquet;
    }

    private boolean isForMe(String paquet) {
	int recu = Integer.parseInt(paquet.split(":")[0].substring(1));
	return recu == id || recu == -1;
    }

}
