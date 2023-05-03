package gameCode;

public class MinenFeld {


	/*Spielfeld wo Minen oder Zahlen sein können Inhalte:
	 * -1 = Mine
	 * 0 = Keine Mine in der nähe
	 * 1 = 1 Mine in der Nähe
	 * .
	 * .
	 * 5 Minen in der Nähe
	 * 5 ist das Maximum
	 */
	private int[][] feld = null;
	
	private final int BREITE;
	private final int HOEHE;
	private int anzahlBomben = 0;
	/**
	 * legt ein neues Feld an und füllt es mit Minen
	 * @param x breite vom Array
	 * @param y hoehe vom Array
	 */
	public MinenFeld(Schwierigkeit schwierigkeit, int xStart, int yStart) {
		switch(schwierigkeit) {
		case EINFACH:
			feld = new int[10][10];
			anzahlBomben = 10;
			break;
		case MITTEL:
			feld = new int[15][15];
			anzahlBomben = 20;
			break;

		case SCHWIERIG:
			feld = new int[20][20];
			anzahlBomben = 40;
			break;
		}
		BREITE = feld.length;
		HOEHE = feld[0].length;
		fuellenMitMinen(feld, xStart, yStart);
		fuellenMitZahlen(feld);
	}
	
	public void setZahl(int x, int y, int value) {
		if(x > 0 && y > 0 && x < BREITE && y < HOEHE) {
			feld[x][y] = value;
		}
	}
	/**
	 * Hier wird der index des 2D-Arrays übergeben um dann herrauszufinden 
	 * ob das eine Mine ist, oder wie viele Minen in der nähe sind
	 * @param x 0 - Maximale Breite vom Array erste Index
	 * @param y 0 - Maximale Hoehe vom Array zweiter Index
	 * @throws ArrayIndexOutOfBoundsException wenn man auf einen nicht definierten 
	 * 										  Index zugreift
	 * @return -2: aufgedeckt 
	 * 	       -1: Mine
	 * 			0: Keine Mine in der Nähe
	 * 			1 bis 5: eine bis fünf Mine in der Nähe
	 */
	public int aufdecken(int x, int y) throws ArrayIndexOutOfBoundsException {
		int ret = feld[x][y];
		feld[x][y] = -2;
		return ret;

	}
	
	private void fuellenMitMinen(int[][] feld, int xStart, int yStart) {

		for(int i = 0; i < BREITE; i++) {
			for(int j = 0; j < HOEHE; j++) {
				if(i == xStart && j == yStart) {
					feld[i][j] = 0;
				}
				else {
					//feld[i][j] = Math.random() < MINEN_CHANCHE ? -1 : 0;
				}
			}
		}


	}

	
	private void fuellenMitZahlen(int[][] feld){

		for(int i = 0; i < BREITE; i++) {
			for(int j = 0; j < HOEHE; j++) {
				if(feld[i][j] != -1) {
					int anzahl = 0;

					try {
						if(feld[i-1][j-1] == -1) {
							anzahl++;
						}
					} catch(ArrayIndexOutOfBoundsException e) { ; }
					try {
						if(feld[i][j-1] == -1) {
							anzahl++;
						}
					} catch(ArrayIndexOutOfBoundsException e) { ; }
					try {
						if(feld[i+1][j-1] == -1) {
							anzahl++;
						}
					} catch(ArrayIndexOutOfBoundsException e) { ; }
					try {
						if(feld[i-1][j] == -1) {
							anzahl++;
						}
					} catch(ArrayIndexOutOfBoundsException e) { ; }
					try {
						if(feld[i+1][j] == -1) {
							anzahl++;
						}
					} catch(ArrayIndexOutOfBoundsException e) { ; }
					try {
						if(feld[i+1][j+1] == -1) {
							anzahl++;
						}
					} catch(ArrayIndexOutOfBoundsException e) { ; }
					try {
						if(feld[i][j+1] == -1) {
							anzahl++;
						}
					} catch(ArrayIndexOutOfBoundsException e) { ; }
					try {
						if(feld[i-1][j+1] == -1) {
							anzahl++;
						}
					} catch(ArrayIndexOutOfBoundsException e) { ; }

					feld[i][j] = anzahl;
				}
			}
		}
	}
	
	public String toString() {
		String ret = "";
		for(int i = 0; i < BREITE; i++) {
			for(int j = 0; j < HOEHE; j++) {
				int d = feld[i][j];
				if(d < 0) {
					ret = ret + d + " ";
				}
				else {
					ret = ret + " " + d + " ";
				}
			}
			ret = ret + "\n";
		}
		return ret;
	}
	
	public static void main(String[] args) {
		MinenFeld m = new MinenFeld(Schwierigkeit.EINFACH, 0, 0);

		System.out.println(m.toString());
	}
}
