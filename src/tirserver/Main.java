/*
 * 
 * 
 * 
 */
package tirserver;

import java.io.IOException;

/**
 * Main.java Main
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

	if (args.length >= 2 && Integer.parseInt(args[0]) > 0 && Integer.parseInt(args[1]) > 0) {
	    Map.MAP_WIDTH = Integer.parseInt(args[0]);
	    Map.MAP_HEIGHT = Integer.parseInt(args[1]);
	}

	Jeu jeu = new Jeu();
	jeu.start();
    }

}
