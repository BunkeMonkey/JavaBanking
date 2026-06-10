package bankprojekt.verarbeitung;

import java.io.FileReader;
import java.util.concurrent.*;

/** Main Klasse zum Testen der Aktien und AktienKonto Klassen
 */
public class AktienMain {

    public static void main(String[] args){
        Aktie bitcoin = new Aktie("$BTC",3);
        Aktie gamestop = new Aktie("GS2C",20);
        Aktie nvidia = new Aktie("NVD", 100);
        AktienKonto dayTrader = new AktienKonto();
        dayTrader.einzahlen(1000);



        Future<Double> btc = dayTrader.kaufAuftrag("$BTC", 5,2.8);
        Future<Double> nvd = dayTrader.kaufAuftrag("NVD", 6,101);

        dayTrader.giveAktie("GS2C", 6);
        dayTrader.giveAktie("NVD", 2);

         dayTrader.verkaufAuftrag("GS2C", 21);
         dayTrader.verkaufAuftrag("NVD", 107);


    }


}
