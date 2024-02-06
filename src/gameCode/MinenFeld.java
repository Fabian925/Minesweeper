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
	private int[][] feld;
	
	private final int BREITE;
	private final int HOEHE;
	private final int ANZAHL_BOMBEN;
	/**
	 * legt ein neues Feld an und füllt es mit Minen
	 * @param xStart die x Koordinate, welche sicher eine 0 sein muss
	 * @param yStart die y Koordinate, welche sicher eine 0 sein muss
	 */
	public MinenFeld(Schwierigkeit schwierigkeit, int yStart, int xStart) {
		BREITE = schwierigkeit.getDimension();
		HOEHE = schwierigkeit.getDimension();
		feld = new int[HOEHE][BREITE];
			
		ANZAHL_BOMBEN = schwierigkeit.getAnzahlBomben();
		fuellenMitMinen(yStart, xStart);
		fuellenMitZahlen();
	}
	
	public void setZahl(int x, int y, int value) {
		if (x > 0 && y > 0 && x < HOEHE && y < BREITE)
			feld[y][x] = value;
	}

	public int getZahl(int x, int y) {
		if(x >= 0 && y >= 0 && x < HOEHE && y < BREITE)
			return feld[y][x];
		return -3;
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
		int ret = feld[y][x];
		feld[y][x] = -2;
		return ret;
	}
	
	private void fuellenMitMinen(int yStart, int xStart) {
		final double MINEN_CHANCHE = (double) (ANZAHL_BOMBEN) / (BREITE * HOEHE);
		int i = 0;
		for (int verbleibendeBomben = ANZAHL_BOMBEN; verbleibendeBomben > 0; i++) {
			if (i >= HOEHE)
				i = 0;

			for (int j = 0; j < BREITE; j++) {
				if (i <= yStart + 1 && i >= yStart - 1 && j <= xStart + 1 && j >= xStart - 1)
					feld[i][j] = 0;
				else if (feld[i][j] != -1){
					feld[i][j] = Math.random() < MINEN_CHANCHE ? -1 : 0;
					if(feld[i][j] == -1)
						verbleibendeBomben--;
				}
				
				if (verbleibendeBomben <= 0)
					break;
			}
		}
	}

	private void fuellenMitZahlen(){
		for(int i = 0; i < HOEHE; i++) {
			for(int j = 0; j < BREITE; j++) {
				if (feld[i][j] == -1)
					continue;

				int anzahl = 0;
				if (this.isMine(j-1,i-1))
					anzahl++;
				if (this.isMine(j, i-1))
					anzahl++;
				if (this.isMine(j+1, i-1))
					anzahl++;
				if (this.isMine(j-1, i))
					anzahl++;
				if (this.isMine(j+1, i))
					anzahl++;
				if (this.isMine(j+1, i+1))
					anzahl++;
				if (this.isMine(j, i+1))
					anzahl++;
				if (this.isMine(j-1, i+1))
					anzahl++;
				feld[i][j] = anzahl;
			}
		}
	}

	private boolean isMine(int x, int y) {
		try {
			return feld[y][x] == -1;
		} catch (ArrayIndexOutOfBoundsException ignore) {
			return false;
		}
	}

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < HOEHE; i++) {
			for (int j = 0; j < BREITE; j++) {
				int d = feld[i][j];
				if (d < 0)
					ret.append(d).append(" ");
				else
					ret.append(" ").append(d).append(" ");
			}
			ret.append("\n");
		}
		return ret.toString();
	}
	
	public static void main(String[] args) {
		MinenFeld m = new MinenFeld(Schwierigkeit.EINFACH, 0, 0);
		System.out.println(m);
	}
}
