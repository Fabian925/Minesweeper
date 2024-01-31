package gameCode;

public enum Schwierigkeit {
	EINFACH,
	MITTEL,
	SCHWIERIG;

	@Override
	public String toString() {
        return switch (this.ordinal()) {
            case 0 -> "Einfach";
            case 1 -> "Mittel";
            case 2 -> "Schwierig";
            default -> null;
        };
	}
	
	public int getAnzahlBomben() {
        return switch (this.ordinal()) {
            case 0 -> 12;
            case 1 -> 28;
            case 2 -> 40;
            default -> 10;
        };
	}
}
