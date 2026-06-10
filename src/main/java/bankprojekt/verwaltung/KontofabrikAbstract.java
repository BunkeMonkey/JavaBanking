package bankprojekt.verwaltung;

import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;

/**
 * Eine Abstrake Klasse die dazu dienen soll verschiedene Konto typen erzeugen zu können
 */
public abstract class KontofabrikAbstract {


    /**
     * Erzeugt ein spezifisches Konto
     * @param inhaber Inhaber des Kontos
     * @param nummer Kontonummer
     * @return das spezifische Konto Objekt
     */
    public abstract Konto erstellen(Kunde inhaber, long nummer);

}
