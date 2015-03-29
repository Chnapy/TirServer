/*
 * 
 * 
 * 
 */
package tirserver;

/**
 * Map.java
 *
 */
public class Map {

    private final static int MAP_WIDTH = 5;
    private final static int MAP_HEIGHT = 5;

    private int[][] map;

    public Map() {
	map = new int[MAP_WIDTH][MAP_HEIGHT];
	initMap();
    }

    private void initMap() {
	for (int[] map1 : map) {
	    for (int j = 0; j < MAP_HEIGHT; j++) {
		map1[j] = -1;
	    }
	}
    }

    public void addClient(int id) {
	int baseX = (int) (Math.random() * MAP_WIDTH);
	int baseY = (int) (Math.random() * MAP_HEIGHT);
	while (true) {
	    if (map[baseX][baseY] == -1 || map[baseX][baseY] == 0) {
		map[baseX][baseY] = id;
		break;
	    } else {
		baseX = (int) (Math.random() * MAP_WIDTH);
		baseY = (int) (Math.random() * MAP_HEIGHT);
	    }
	}
    }

    public String toEnvoi() {
	String envoi = "";
	for (int[] map1 : map) {
	    for (int i = 0; i < map1.length; i++) {
		envoi += map1[i];
//		if (i < map1.length - 1) {
		    envoi += '.';
//		}
	    }
	    envoi += ' ';
	}
	return envoi;
    }

    @Override
    public String toString() {
	String ret = "Map {";
	for (int[] x : map) {
	    ret += "\n\t";
	    for (int y : x) {
		ret += y;
	    }
	}
	return ret + "\n}";
    }

}
