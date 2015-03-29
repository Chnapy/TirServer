/*
 * 
 * 
 * 
 */
package tirserver;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ListPaquet.java
 *
 */
public class ListPaquet extends ArrayList<Paquet> {

    public ListPaquet() {
	super();
    }

    /**
     *
     * @param commande
     * @return
     */
    public Paquet getPaquet(String commande) {
	for (Paquet paquet : this) {
	    if (paquet.getCommande().equals(commande)) {
		return paquet;
	    }
	}
	return null;
    }

    public Paquet waitPaquet(String commande) {
	Paquet ret = getPaquet(commande);
	if (ret == null) {
	    try {
		sleep(20);  //Permet d'eviter un StackOverflowException
	    } catch (InterruptedException ex) {
		Logger.getLogger(ListPaquet.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    return waitPaquet(commande);
	}
	return ret;
    }

}
