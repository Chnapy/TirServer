/*
 * 
 * 
 * 
 */
package tirserver;

import java.util.Arrays;

/**
 * Paquet.java
 *
 */
public class Paquet {

    private String[] paquet;

    public Paquet(String paquetBrut) {
	paquet = paquetBrut.split(":");
    }
    
    public int getId() {
	return Integer.parseInt(paquet[0].substring(1));
    }

    public String getCommande() {
	return paquet[1];
    }

    public String getMessage(int index) {
	return paquet[index + 2];
    }

    public String getFirstMessage() {
	return getMessage(0);
    }

    public int getMessageToInt(int index) {
	return Integer.parseInt(paquet[index + 2]);
    }

    public int getFirstMessageToInt() {
	return getMessageToInt(0);
    }

    public String[] getAllMessages() {
	String[] ret = new String[paquet.length - 1];
	for (int i = 2; i < paquet.length - 1; i++) {
	    ret[i - 2] = paquet[i];
	}
	return ret;
    }

    public String[] getPaquet() {
	return paquet;
    }

    @Override
    public String toString() {
	return Arrays.toString(paquet);
    }

}
