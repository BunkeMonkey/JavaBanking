package bankprojekt.verwaltung;

/**
 * tritt auf, wenn eine Kontonummer nicht existiert
 */
public class KontoNummerNichtVorhandenException extends Exception{

    /**
     * erstellt eine neue KontoNummerNichtVorhandenException
     * @param kontoNummer die nicht existierende Kontonummer
     */
    public KontoNummerNichtVorhandenException(long kontoNummer){
        super("Konto mit der nummer: " + kontoNummer + " existiert nicht");

    }
}
