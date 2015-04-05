/*
 * 
 * 
 * 
 */
package tirserver;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Jeu.java
 *
 */
public class Jeu implements Observer {

    private final Reception serveur;
    private final Map map;

    private final ArrayList<Client> listClient;
    private int id;

    public Jeu() throws IOException {
	serveur = new Reception();
	serveur.addObserver(this);
	map = new Map();
	listClient = new ArrayList<Client>();
	id = -1;
	System.out.println(map);
    }

    public void start() {
	serveur.start();
    }

    public void stop() {
	serveur.stop();
	listClient.stream().forEach((client) -> {
	    try {
		client.stop();
	    } catch (IOException ex) {
		Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
	    }
	});
    }

    @Override
    public void update(Observable o, Object arg) {
	try {
	    if (o instanceof Reception) {
		initClient((Socket) arg);
	    } else if (o instanceof Client) {
		Client client = (Client) o;
		Paquet paq = (Paquet) arg;
		switch (paq.getCommande()) {
		    case "pseudo":
			initClientFin(client, (Paquet) arg);
			break;
		    case "move":
			moveClient(client, paq.getFirstMessageToInt(), paq.getMessageToInt(1));
			break;
		    case "tire":
			tireClient(client, paq.getFirstMessageToInt(), paq.getMessageToInt(1));
			break;
		}
	    }
	} catch (IOException ex) {
	    Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    private void initClient(Socket socket) throws IOException {
	id++;
	Client client = new Client(id, socket);
	client.addObserver(this);
	listClient.add(client);
	client.start();
    }

    private boolean initClientFin(Client client, Paquet paqPseudo) throws IOException {
	if (existPseudo(paqPseudo.getFirstMessage())) {
	    client.envoi("id", "false");
	    listClient.remove(client);
	    client.stop();
	    return false;
	}
	client.envoi("id", Integer.toString(id));
	client.setPosition(map.addClient(client.getId()));
	envoiATousSaufUn(client, "nc", Integer.toString(client.getId()), client.getPseudo(), Integer.toString(client.getPosition().x), Integer.toString(client.getPosition().y));
	System.out.println(map);
	client.envoi("map", map.toEnvoi());
	return true;
    }

    private boolean existPseudo(String pseudo) {
	return listClient.stream().anyMatch((client) -> (pseudo.equals(client.getPseudo())));
    }

    private Client getClient(int id) {
	for (Client client : listClient) {
	    if (client.getId() == id) {
		return client;
	    }
	}
	return null;
    }

    private void moveClient(Client client, int x, int y) {
	if (map.estLibre(x, y)) {
	    System.out.println("Map libre " + x + " " + y);
	    map.setValeur(client.getPosition().x, client.getPosition().y, 0);
	    map.setValeur(x, y, client.getId() + 2);
	    client.getPosition().x = x;
	    client.getPosition().y = y;
	    envoiATousSaufUn(client, "move2", Integer.toString(client.getId()), Integer.toString(client.getPosition().x), Integer.toString(client.getPosition().y));
	} else {
	    System.err.println("Map OCCUPEE " + x + " " + y);
	}
	client.envoi("move", Integer.toString(client.getPosition().x), Integer.toString(client.getPosition().y));
    }

    private void envoiATousSaufUn(Client pasALui, String commande, String... message) {
	listClient.stream().filter((client) -> (!client.equals(pasALui))).forEach((client) -> {
	    client.envoi(commande, message);
	});
    }

    private void tireClient(Client client, int x, int y) {
	envoiATousSaufUn(client, "tire", Integer.toString(client.getId()), Integer.toString(x), Integer.toString(y));
	int identifiant = map.tireClient(client.getId(), x, y);
	if (identifiant != -1) {
	    Client cible = getClient(identifiant);
	    cible.vie--;
	    cible.envoi("degats", "1");
	    if (cible.vie <= 0) {
		int[] pos = map.getPosId(cible.getId());
		map.setValeur(pos[0], pos[1], 0);
		listClient.remove(cible);
		envoiATousSaufUn(cible, "mort", Integer.toString(cible.getId()));
	    }
	}
    }

}
