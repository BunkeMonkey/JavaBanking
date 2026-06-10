package bankprojekt.intervalle;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Main Klasse um die Intervall Klasse zu testen

 */
public class IntervallMain {

    /**
     * Main Methode um die Intervall Klasse zu testen
     * @param args die Argumente
     */
    public static void main(String[] args) {


        Intervall<String> stringIntervall1 = new Intervall<>("alpha", "omega");
        Intervall<String> stringIntervall2 = new Intervall<>("kappa", "lambda");
        System.out.println(stringIntervall1.enthaelt("alpha"));
        System.out.println(stringIntervall1.isLeer());
        var intervall = stringIntervall1.schnitt(stringIntervall2);


        System.out.println(intervall);



        Intervall<java.util.Date> dateIntervall = new Intervall<>(new Date(2003 - 1900, 8, 2,13,42), new Date());
        Intervall<java.util.Date> dateIntervall2 = new Intervall<>(new Date(1982 - 1900, 03, 5), new Date(2025, 11, 24));
        //NOTE: time here is 1977.5.17 11:00:00 on the lower and 15:00:00 on the upper
        Intervall<java.sql.Time> sqlTimeIntervall = new Intervall<>(new Time(232711200000L), new Time(232725600000L));
        Intervall<java.util.Date> dateTimeIntervall = dateIntervall.schnitt(sqlTimeIntervall);
        System.out.println(dateTimeIntervall);

        Intervall<java.util.Date> dateIntervall3 = dateIntervall.schnitt(dateIntervall2);
        System.out.println(dateIntervall3);

        java.sql.Timestamp timestamp = new java.sql.Timestamp(1977 - 1900, 4, 17, 11, 0, 0, 0);
        Timestamp timestamp2 = new Timestamp(232711200000L);
        //sollte nicht enthalten sein

        System.out.println(dateIntervall2.enthaelt(timestamp));

        Date stamptoDate = (Date) timestamp;
        //System.out.println(stamptoDate);


        //System.out.println("time intervall: " + sqlTimeIntervall);
       //System.out.println(timestamp);

        System.out.println(sqlTimeIntervall.enthaelt(timestamp));


        //Intervall<Object> objectIntervall;
        Intervall<String> richtig = new Intervall<>("A", "B");
        //richtig.enthaelt(3.5);
        Intervall<Double> zahlen = new Intervall<>(1.2, 3.4);
        Intervall<String> texte = new Intervall<>("a", "b");
        //zahlen.schnitt(texte);
    }
}

