/*
 * 
 * 
 * 
 */
package tirserver;

import java.awt.Point;

/**
 * Map.java
 *
 */
public class Map {

    public static int MAP_WIDTH = 10;
    public static int MAP_HEIGHT = 5;

    private int[][] map;

    public Map() {
	map = new int[MAP_WIDTH][MAP_HEIGHT];
	initMap();
    }

    private void initMap() {
	for (int[] map1 : map) {
	    for (int j = 0; j < MAP_HEIGHT; j++) {
		map1[j] = 0;
	    }
	}
	map[2][2] = 1;
	map[4][1] = 1;
	map[6][4] = 1;
	map[1][0] = 1;
	map[0][4] = 1;
	map[5][3] = 1;
    }

    public Point addClient(int id) {
	int baseX = (int) (Math.random() * MAP_WIDTH);
	int baseY = (int) (Math.random() * MAP_HEIGHT);
	while (true) {
	    if (map[baseX][baseY] == -1 || map[baseX][baseY] == 0) {
		map[baseX][baseY] = id + 2;
		break;
	    } else {
		baseX = (int) (Math.random() * MAP_WIDTH);
		baseY = (int) (Math.random() * MAP_HEIGHT);
	    }
	}
	return new Point(baseX, baseY);
    }
    
    public boolean estLibre(int x, int y) {
	return map[x][y] == 0;
    }
    
    public void setValeur(int x, int y, int valeur) {
	map[x][y] = valeur;
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
