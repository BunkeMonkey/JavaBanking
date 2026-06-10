package bankprojekt.verwaltung;

import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Kunde;

public class GiroKontofabrik extends KontofabrikAbstract {

    private static GiroKontofabrik instance;

    private GiroKontofabrik() {
    }

    public static GiroKontofabrik getInstance() {
        if (instance == null) {
            instance = new GiroKontofabrik();
        }
        return instance;
    }


    @Override
    public Girokonto erstellen(Kunde inhaber, long nummer) {
        return new Girokonto(inhaber, nummer, 0);
    }
}
