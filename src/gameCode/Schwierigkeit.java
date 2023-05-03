package gameCode;

public enum Schwierigkeit {
	EINFACH,
	MITTEL,
	SCHWIERIG;

	@Override
	public String toString() {
		switch(this.ordinal()) {
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
}
