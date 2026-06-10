package bankprojekt.verwaltung;

import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verarbeitung.Sparbuch;

public class Sparbuchfabrik extends KontofabrikAbstract{

    private static Sparbuchfabrik instance;

    private Sparbuchfabrik() {
    }

    public static Sparbuchfabrik getInstance() {
        if (instance == null) {
            instance = new Sparbuchfabrik();
        }
        return instance;
    }
    @Override
    public Sparbuch erstellen(Kunde inhaber, long nummer) {
        return new Sparbuch(inhaber, nummer);
    }
}
