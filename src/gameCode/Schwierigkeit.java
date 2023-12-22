package gameCode;

public enum Schwierigkeit {
	EINFACH,
	MITTEL,
	SCHWIERIG;

	@Override
	public String toString() {
		switch (this.ordinal()) {
		case 0:
			return "Einfach";
		case 1:
			return "Mittel";
		case 2:
			return "Schwierig";
		default:
			return null;
		}
	}
	
	public int getAnzahlBomben() {
		switch (this.ordinal()) {
		case 0:
			return 12;
		case 1:
			return 28;
		case 2:
			return 40;
		default:
			return 10;
		}
	}
}
