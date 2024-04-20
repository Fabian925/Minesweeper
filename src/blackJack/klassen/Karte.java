package blackJack.klassen;

public class Karte {
    private Farbe farbe;
    private Zahl zahl;

    public Karte(Farbe farbe, Zahl zahl) {
        setFarbe(farbe);
        setZahl(zahl);
    }

    public void setFarbe(Farbe farbe) {
        this.farbe = farbe;
    }

    public Farbe getFarbe() {
        return farbe;
    }

    public void setZahl(Zahl zahl) {
        this.zahl = zahl;
    }

    public Zahl getZahl() {
        return zahl;
    }

}
