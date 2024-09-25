package gameCode;

public enum Schwierigkeit {
	EINFACH,
	MITTEL,
	SCHWIERIG;

	@Override
	public String toString() {
        return switch (this) {
			case EINFACH -> "Einfach";
			case MITTEL -> "Mittel";
			case SCHWIERIG -> "Schwierig";
        };
	}
	
	public int getAnzahlBomben() {
        return switch (this) {
			case EINFACH -> 12;
			case MITTEL -> 28;
			case SCHWIERIG -> 40;
        };
	}

    public int getDimension() {
        return switch (this) {
			case EINFACH -> 10;
			case MITTEL -> 15;
			case SCHWIERIG -> 20;
        };
    }
}
