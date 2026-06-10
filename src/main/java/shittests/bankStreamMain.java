package shittests;

import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verwaltung.Bank;
import bankprojekt.verwaltung.KontoNummerNichtVorhandenException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class bankStreamMain {

    public static void main(String[] args) throws GesperrtException, KontoNummerNichtVorhandenException {
        /*
        Bank bank = new Bank(1337);

        Kunde Sam = new Kunde("Sam", "B", "yes", LocalDate.now());
        Kunde Daniel = new Kunde("Daniel", "M", "yes", LocalDate.now());
        Kunde Lasse = new Kunde("Lasse", "P", "yes", LocalDate.now());

        long samNummer = bank.girokontoErstellen(Sam);
        long danielNummer = bank.girokontoErstellen(Daniel);
        long lasseNummer = bank.girokontoErstellen(Lasse);

        bank.geldAbheben(lasseNummer,500);
        //bank.geldAbheben(danielNummer, 100);
        bank.geldEinzahlen(samNummer, 3000);

        bank.pleitegeierSperren();

        Konto lasse = bank.getKonten().get(lasseNummer);
        Konto daniel = bank.getKonten().get(danielNummer);
        Konto sam = bank.getKonten().get(samNummer);

        System.out.println(lasse.isGesperrt() + " sollte true sein");
        System.out.println(daniel.isGesperrt() + " sollte false sein");
        System.out.println(sam.isGesperrt() + " sollte false sein");

        System.out.println("---------------------");

        List<Kunde> kundenMitCash = bank.getKundenMitVollemKonto(500);
        System.out.println(kundenMitCash);

        System.out.println("-------------------------------");


        System.out.println(samNummer);
        System.out.println(danielNummer);
        System.out.println(lasseNummer);

        bank.kontoLoeschen(danielNummer);

        List<Long> kontoLuecken = bank.getKontonummernLuecken();
        System.out.println(kontoLuecken);

         */



    }
}
