/*
 * 
 * 
 * 
 */
package tirserver;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ListPaquet.java
 *
 */
public class ListPaquet extends ArrayList<Paquet> {

    public ListPaquet() {
    }

    /**
     *
     * @param commande
     * @return
     */
    public Paquet waitPaquet(String commande) {
	System.out.print(".");
	Paquet ret;
	for (Iterator<Paquet> ite = this.iterator(); ite.hasNext();) {
	    ret = ite.next();
	    if (ret.getCommande().equals(commande)) {
		ite.remove();
		return ret;
	    }
	}
	try {
	    sleep(100);  //Permet d'eviter un StackOverflowException
	} catch (InterruptedException ex) {
	    Logger.getLogger(ListPaquet.class.getName()).log(Level.SEVERE, null, ex);
	}
	return waitPaquet(commande);
    }

}
