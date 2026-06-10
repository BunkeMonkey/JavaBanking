package bankprojekt.verarbeitung;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class KontoTests {


    private Konto k;
    private double kontostandOldEuro;



    @BeforeEach
    public void setUp() {
        k = new Girokonto(new Kunde(),123,0);
        k.einzahlen(1000);
        kontostandOldEuro = k.getKontostand();
    }

    //Euro
    @Test
    public void waehrungWechselEURzuEscudo() {

        k.waehrungswechsel(Waehrung.ESCUDO);
        assertEquals(Waehrung.ESCUDO.euroInWaehrungUmrechnen(1000), k.getKontostand());
    }
    @Test
    public void waehrungWechselEURzuEUR() {

        k.waehrungswechsel(Waehrung.EUR);
        assertEquals(Waehrung.EUR.euroInWaehrungUmrechnen(1000), k.getKontostand());
    }
    @Test
    public void waehrungWechselEURzuDobra() {

        k.waehrungswechsel(Waehrung.DOBRA);
        assertEquals(Waehrung.DOBRA.euroInWaehrungUmrechnen(1000), k.getKontostand());
    }
    @Test
    public void waehrungWechselEURzuFrancs() {

        k.waehrungswechsel(Waehrung.FRANC);
        assertEquals(Waehrung.FRANC.euroInWaehrungUmrechnen(1000), k.getKontostand());
    }

    //Escudo
    @Test
    public void waehrungWechselEscudozuEUR() {
        k.waehrungswechsel(Waehrung.ESCUDO);
        k.waehrungswechsel(Waehrung.EUR);
        assertEquals(Waehrung.EUR.euroInWaehrungUmrechnen(1000), k.getKontostand());
    }
    @Test
    public void waehrungsWechselEscudozuEscudo() {
        k.waehrungswechsel(Waehrung.ESCUDO);
        k.waehrungswechsel(Waehrung.ESCUDO);
        assertEquals(Waehrung.ESCUDO.euroInWaehrungUmrechnen(1000), k.getKontostand());
    }
    @Test
    public void waehrungsWechselEscudozuDobra() {
        k.waehrungswechsel(Waehrung.ESCUDO);
        k.waehrungswechsel(Waehrung.DOBRA);
        assertEquals(Waehrung.DOBRA.euroInWaehrungUmrechnen(1000), k.getKontostand());
    }
    @Test
    public void waehrungsWechselEscudozuFrancs() {
        k.waehrungswechsel(Waehrung.ESCUDO);
        k.waehrungswechsel(Waehrung.FRANC);
        assertEquals(Waehrung.FRANC.euroInWaehrungUmrechnen(1000), k.getKontostand());
    }

    //Dobra

    @Test
    public void waehrungsWechselDobrazuEUR() {
        k.waehrungswechsel(Waehrung.DOBRA);
        k.waehrungswechsel(Waehrung.EUR);
        assertEquals(Waehrung.EUR.euroInWaehrungUmrechnen(1000), k.getKontostand());
    }
    @Test
    public void waehrungsWechselDobrazuEscudo() {
        k.waehrungswechsel(Waehrung.DOBRA);
        k.waehrungswechsel(Waehrung.ESCUDO);
        assertEquals(Waehrung.ESCUDO.euroInWaehrungUmrechnen(1000), k.getKontostand());
    }
    @Test
    public void waehrungsWechselDobrazuDobra() {
        k.waehrungswechsel(Waehrung.DOBRA);
        k.waehrungswechsel(Waehrung.DOBRA);
        assertEquals(Waehrung.DOBRA.euroInWaehrungUmrechnen(1000), k.getKontostand());
    }
    @Test
    public void waehrungsWechselDobrazuFrancs() {
        k.waehrungswechsel(Waehrung.DOBRA);
        k.waehrungswechsel(Waehrung.FRANC);
        assertEquals(Waehrung.FRANC.euroInWaehrungUmrechnen(1000), k.getKontostand());
    }

    //Francs
    @Test
    public void waehrungsWechselFrancszuEUR() {
        k.waehrungswechsel(Waehrung.FRANC);
        k.waehrungswechsel(Waehrung.EUR);
        assertEquals(Waehrung.EUR.euroInWaehrungUmrechnen(1000), k.getKontostand());
    }
    @Test
    public void waehrungsWechselFrancszuEscudo() {
        k.waehrungswechsel(Waehrung.FRANC);
        k.waehrungswechsel(Waehrung.ESCUDO);
        assertEquals(Waehrung.ESCUDO.euroInWaehrungUmrechnen(1000), k.getKontostand());
    }
    @Test
    public void waehrungsWechselFrancszuDobra() {
        k.waehrungswechsel(Waehrung.FRANC);
        k.waehrungswechsel(Waehrung.DOBRA);
        assertEquals(Waehrung.DOBRA.euroInWaehrungUmrechnen(1000), k.getKontostand());
    }
    @Test
    public void waehrungsWechselFrancszuFrancs() {
        k.waehrungswechsel(Waehrung.FRANC);
        k.waehrungswechsel(Waehrung.FRANC);
        assertEquals(Waehrung.FRANC.euroInWaehrungUmrechnen(1000), k.getKontostand());
    }

    @Test
    public void waehrungsWechselNull() {
        assertThrows(NullPointerException.class, () -> k.waehrungswechsel(null));
    }

    /**
     * Testen der abheben methode in dem immer 500 der jeweiligen Waehrung abgehoben
     * werden und dann der kontostand in euro genommen wird und minus dem abzuhebendem
     * wert in Euro mit dem jetzigem kontoStand in euro verglichen wird
     * <p>
     * Ich habe versucht es auch mit hard coded values zu machen (also die ergebnisse
     * selbst auszurechnen) aber durch die round() methode in waehrung kam es hier zu
     * fehlern bei den tests
     */


    //Abheben von einem Euro Konto
    @Test
    public void testAbhebenVonEuroMitEUR() throws GesperrtException {
        k.abheben(500, Waehrung.EUR);
        assertEquals(500, k.getKontostand());
    }
    @Test
    public void testAbhebenVonEuroMitEscudo() throws GesperrtException {
        k.abheben(500, Waehrung.ESCUDO);
        assertEquals(kontostandOldEuro - Waehrung.ESCUDO.waehrungInEuroUmrechnen(500),
                k.getKontostand()
        );
    }
    //@Test
    public void testAbhebenVonEuroMitDobra() throws GesperrtException {
        k.abheben(500, Waehrung.DOBRA);
        assertEquals(kontostandOldEuro - Waehrung.DOBRA.waehrungInEuroUmrechnen(500),
                k.getKontostand()
        );
    }
    @Test
    public void testAbhebenVonEuroMitFrancs() throws GesperrtException {
        k.abheben(500, Waehrung.FRANC);
        assertEquals(kontostandOldEuro - Waehrung.FRANC.waehrungInEuroUmrechnen(500),
                k.getKontostand()
        );
    }


    //Abheben von einem Escudo Konto
    @Test
    public void testAbhebenVonEscudoMitEuro() throws GesperrtException {
        k.waehrungswechsel(Waehrung.ESCUDO);
        k.abheben(500, Waehrung.EUR);
        assertEquals(kontostandOldEuro - Waehrung.EUR.waehrungInEuroUmrechnen(500),
                Waehrung.ESCUDO.waehrungInEuroUmrechnen(k.getKontostand())
        );
    }
    //@Test
    public void testAbhebenVonEscudoMitEscudo() throws GesperrtException {
        k.waehrungswechsel(Waehrung.ESCUDO);
        k.abheben(500, Waehrung.ESCUDO);
        assertEquals(kontostandOldEuro - Waehrung.ESCUDO.waehrungInEuroUmrechnen(500),
                Waehrung.ESCUDO.waehrungInEuroUmrechnen(k.getKontostand())
        );;
    }
    @Test
    public void testAbhebenVonEscudoMitDobra() throws GesperrtException {
        k.waehrungswechsel(Waehrung.ESCUDO);
        k.abheben(500, Waehrung.DOBRA);
        assertEquals(kontostandOldEuro - Waehrung.DOBRA.waehrungInEuroUmrechnen(500),
                Waehrung.ESCUDO.waehrungInEuroUmrechnen(k.getKontostand())
        );
    }
    //@Test
    public void testAbhebenVonEscudoMitFrancs() throws GesperrtException {
        k.waehrungswechsel(Waehrung.ESCUDO);
        k.abheben(500, Waehrung.FRANC);
        assertEquals(kontostandOldEuro - Waehrung.FRANC.waehrungInEuroUmrechnen(500),
                Waehrung.ESCUDO.waehrungInEuroUmrechnen(k.getKontostand())
        );
    }


    //Abheben von einem Dobra konto
    //@Test
    public void testAbhebenVonDobraMitEuro() throws GesperrtException {
        k.waehrungswechsel(Waehrung.DOBRA);
        k.abheben(500, Waehrung.EUR);
        assertEquals(kontostandOldEuro - 500,
                Waehrung.DOBRA.waehrungInEuroUmrechnen(k.getKontostand())
        );
    }
    //@Test
    public void testAbhebenVonDobraMitEscudo() throws GesperrtException {
        k.waehrungswechsel(Waehrung.DOBRA);
        k.abheben(500, Waehrung.ESCUDO);
        assertEquals(kontostandOldEuro - Waehrung.ESCUDO.waehrungInEuroUmrechnen(500),
                Waehrung.DOBRA.waehrungInEuroUmrechnen(k.getKontostand())
        );
    }

    //@Test
    public void testAbhebenVonDobraMitDobra() throws GesperrtException {
        k.waehrungswechsel(Waehrung.DOBRA);
        k.abheben(500, Waehrung.DOBRA);
        assertEquals(kontostandOldEuro - Waehrung.DOBRA.waehrungInEuroUmrechnen(500),
                Waehrung.DOBRA.waehrungInEuroUmrechnen(k.getKontostand())
        );
    }
    //@Test
    public void testAbhebenVonDobraMitFrancs() throws GesperrtException {
        k.waehrungswechsel(Waehrung.DOBRA);
        k.abheben(500, Waehrung.FRANC);
        assertEquals(kontostandOldEuro - Waehrung.FRANC.waehrungInEuroUmrechnen(500),
                Waehrung.DOBRA.waehrungInEuroUmrechnen(k.getKontostand())
        );
    }
    //@Test
    public void testTest() throws GesperrtException {
        k.waehrungswechsel(Waehrung.DOBRA);
        k.abheben(500, Waehrung.FRANC);
        assertEquals(kontostandOldEuro- Waehrung.FRANC.waehrungInEuroUmrechnen(500),
                Waehrung.DOBRA.waehrungInEuroUmrechnen(k.getKontostand())
        );
    }


    //abheben von einem konto mit francs
    //@Test
    public void testAbhebenVonFrancsMitEuro() throws GesperrtException {
        k.waehrungswechsel(Waehrung.FRANC);
        k.abheben(500, Waehrung.EUR);
        assertEquals(kontostandOldEuro - 500,
                Waehrung.FRANC.waehrungInEuroUmrechnen(k.getKontostand())
        );
    }
    @Test
    public void testAbhebenVonFrancsMitEscudo() throws GesperrtException {
        k.waehrungswechsel(Waehrung.FRANC);
        k.abheben(500, Waehrung.ESCUDO);
        assertEquals(kontostandOldEuro - Waehrung.ESCUDO.waehrungInEuroUmrechnen(500),
                Waehrung.FRANC.waehrungInEuroUmrechnen(k.getKontostand())
        );
    }
    @Test
    public void testAbhebenVonFrancsMitDobra() throws GesperrtException {
        k.waehrungswechsel(Waehrung.FRANC);
        k.abheben(500, Waehrung.DOBRA);
        assertEquals(kontostandOldEuro - Waehrung.DOBRA.waehrungInEuroUmrechnen(500),
                Waehrung.FRANC.waehrungInEuroUmrechnen(k.getKontostand())
        );
    }
    @Test
    public void testAbhebenVonFrancsMitFrancs() throws GesperrtException {
        k.waehrungswechsel(Waehrung.FRANC);
        k.abheben(500, Waehrung.FRANC);
        assertEquals(kontostandOldEuro - Waehrung.FRANC.waehrungInEuroUmrechnen(500),
                Waehrung.FRANC.waehrungInEuroUmrechnen(k.getKontostand())
        );
    }

    //error cases
    @Test
    public void testAbhebenMitNegativemBetrag() {
        assertThrows(IllegalArgumentException.class, () -> k.abheben(-500, Waehrung.FRANC));
    }
    @Test
    public void testAbhebenMitNanBetrag() {
        assertThrows(IllegalArgumentException.class, () -> k.abheben(Double.NaN, Waehrung.ESCUDO));
    }
    @Test
    public void testAbhebenMitInfiniteBetrag() {
        assertThrows(IllegalArgumentException.class, () -> k.abheben(Double.POSITIVE_INFINITY, Waehrung.EUR));
    }
    @Test
    public void testAbhebenZuVielEigeneWaehrung() throws GesperrtException {
        assertFalse(k.abheben(1001));
    }
    @Test
    public void testAbhebenZuVielFremdeWaehrung() throws GesperrtException {
        //1001 Euro == 109936.7269 Escudo
        assertFalse(k.abheben(109936.7269, Waehrung.ESCUDO));
    }
    @Test
    public void testAbhebenZuVielFremdeWaehrungNichtEuro() throws GesperrtException {
        //1001 Euro == 109936.7269 Escudo
        k.waehrungswechsel(Waehrung.DOBRA);
        assertFalse(k.abheben(109936.7269, Waehrung.ESCUDO));
    }
    @Test
    public void testAbhebenMitNullWaehrung() {
        assertThrows(NullPointerException.class, () -> k.abheben(500, null));
    }
    @Test
    public void testAbhebenMitGesperrtemKonto() {
        Konto k = new Girokonto();
        k.sperren();
        assertThrows(GesperrtException.class, () -> k.abheben(500));
    }





}

