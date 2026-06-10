package bankprojekt.verwaltung;


import bankprojekt.verarbeitung.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Stellt eine allgemeine Bank dar, die Konten erstellen und verwalten kann
 * und Ueberweisungen zwischen Konten durchfuehren kann.
 */
public class Bank implements Cloneable, Serializable {

    /**
     * Liste aller Konten, die die Bank verwaltet
     */
    private HashMap<Long, Konto> konten = new HashMap<>();
    /**
     * die Bankleitzahl der Bank
     */
    private long bankleitzahl;

    /**
     * die letzte vergebene Kontonummer
     */
    private long lastKontonummer = 100_000;


    /**
     * Erzeugt eine Bank mit einer Bankleitzahl
     *
     * @param bankleitzahl die Bankleitzahl der Bank
     * @throws IllegalArgumentException wenn die Bankleitzahl negativ ist
     */
    public Bank(long bankleitzahl) {
        if (bankleitzahl < 0) {
            throw new IllegalArgumentException("Bankleitzahl ist negativ");
        }
        this.bankleitzahl = bankleitzahl;
    }

    /**
     * Gibt die Bankleitzahl der Bank zurueck
     *
     * @return die Bankleitzahl
     */
    public long getBankleitzahl() {
        return bankleitzahl;
    }



    public long kontoErstellen(KontofabrikAbstract fabrik, Kunde inhaber){
        long newKontonummer = createAccoutNumber();
        Konto konto = fabrik.erstellen(inhaber, newKontonummer);

        konten.put(newKontonummer, konto);
        return newKontonummer;
    }
        /*

    /**
     * Erstellt ein neues Girokonto fuer den angegebenen Kunden und fuegt es der Liste hinzu
     *
     * @param inhaber der Kunde, der das Konto besitzt
     * @return die Kontonummer des neuen Kontos
     * @throws IllegalArgumentException , wenn inhaber == null

    public long girokontoErstellen(Kunde inhaber) {
        long newKontonummer = createAccoutNumber();

        Girokonto newKonto = new Girokonto(inhaber, newKontonummer, 2000);
        konten.put(newKontonummer, newKonto);

        return newKontonummer;
    }

    /**
     * Erstellt ein neues Sparbuch fuer den angegebenen Kunden und fuegt es der Liste hinzu
     *
     * @param inhaber der Kunde, der das Konto besitzt
     * @return die Kontonummer des neuen Kontos

    public long sparbuchErstellen(Kunde inhaber) {
        long newKontonummer = createAccoutNumber();

        Sparbuch newSparbuch = new Sparbuch(inhaber, newKontonummer);
        konten.put(newKontonummer, newSparbuch);

        return newKontonummer;
    }


    */
    /**
     * Gibt einen String zurueck, der alle Konten der Bank auflistet
     *
     * @return der Formatierte String
     */
    public String getAlleKonten() {
        StringBuilder accounts = new StringBuilder();
        for (Konto k : konten.values()) {
            accounts.append("Kontonummer: ")
                    .append(k.getKontonummer())
                    .append("\t\t")
                    .append("Kontostand: ")
                    .append(k.getKontostandFormatiert())
                    .append(System.lineSeparator());
        }
        return accounts.toString();

    }

    /**
     * Gibt eine Liste aller Kontonummern der Bank zurueck
     *
     * @return list aller Kontonummern
     */
    public List<Long> getAlleKontonummern() {
        return new ArrayList<>(konten.keySet());
    }

    /**
     * Hebt geld von einem bestimmten konto ab
     *
     * @param von    Konto von dem geld abgehoben werden soll
     * @param betrag betrag der abgehoben werden soll
     * @return true wenn die abhebung erfolgreich war, false wenn nicht
     * @throws GesperrtException                  wenn das Konto gesperrt ist
     * @throws KontoNummerNichtVorhandenException wenn die Kontonummer nicht existiert
     * @throws IllegalArgumentException           wenn betrag negativ ist
     */
    public boolean geldAbheben(long von, double betrag) throws GesperrtException, KontoNummerNichtVorhandenException {
        Konto target = konten.get(von);

        if (target == null) {
            throw new KontoNummerNichtVorhandenException(von);
        }
        return target.abheben(betrag);
    }

    /**
     * Zahlt geld auf ein bestimmtes Konto ein
     *
     * @param von    Konto auf das das geld eingezahlt werden soll
     * @param betrag betrag der eingezahlt werden soll
     * @throws KontoNummerNichtVorhandenException wenn die Kontonummer nicht existiert
     */
    public void geldEinzahlen(long von, double betrag) throws KontoNummerNichtVorhandenException {
        Konto target = konten.get(von);

        if (target == null) {
            throw new KontoNummerNichtVorhandenException(von);
        }
        target.einzahlen(betrag);
    }


    /**
     * Loescht ein Konto aus der Liste
     *
     * @param nummer die Kontonummer des zu loeschenden Kontos
     * @return true wenn das Konto geloescht wurde, false wenn es garnicht erst existiert hat
     */
    public boolean kontoLoeschen(long nummer) {
        if (!konten.containsKey(nummer)) {
            return false;
        }

        konten.remove(nummer);
        return true;
    }

    /**
     * Gibt den Kontostand eines Kontos zurueck
     *
     * @param nummer die Kontonummer des Kontos
     * @return den Kontostand
     * @throws KontoNummerNichtVorhandenException wenn die Kontonummer nicht existiert
     */
    public double getKontostand(long nummer) throws KontoNummerNichtVorhandenException {
        Konto target = konten.get(nummer);

        if (target == null) {
            throw new KontoNummerNichtVorhandenException(nummer);
        }
        return target.getKontostand();
    }

    /**
     * Ueberweist geld von einem Konto auf ein anderes
     *
     * @param vonKontonr       das Konto des senders
     * @param nachKontonr      das Konto des empfaengers
     * @param betrag           der zu ueberweisende betrag
     * @param verwendungszweck der verwendungszweck der ueberweisung
     * @return true wenn die ueberweisung erfolgreich war, false wenn nicht
     * @throws KontoNummerNichtVorhandenException wenn eine der Kontonummern nicht existiert
     * @throws GesperrtException                  wenn das sendende Konto gesperrt ist
     * @throws IllegalArgumentException           wenn der Betrag negativ bzw. NaN bzw. unendlich ist oder
     *                                            * 					empfaenger oder verwendungszweck null ist
     */
    public boolean geldUeberweisen(long vonKontonr, long nachKontonr,
                                   double betrag, String verwendungszweck) throws KontoNummerNichtVorhandenException, GesperrtException {
        Konto source = konten.get(vonKontonr);
        Konto target = konten.get(nachKontonr);

        //return if atleast one of the accounts doesnt exist
        if (source == null) {
            throw new KontoNummerNichtVorhandenException(vonKontonr);
        }
        if (target == null) {
            throw new KontoNummerNichtVorhandenException(nachKontonr);
        }

        //return false if atleast on of the accounts is not Ueberweisungsfaehig
        if (!(source instanceof UeberweisungsfaehigesKonto)
                || !(target instanceof UeberweisungsfaehigesKonto)) {
            return false;
        }
        String empfaenger = target.getInhaber().getName();
        String sender = source.getInhaber().getName();

        boolean success = ((UeberweisungsfaehigesKonto) source).ueberweisungAbsenden(betrag, empfaenger, nachKontonr, bankleitzahl, verwendungszweck);
        if (success) {
            ((UeberweisungsfaehigesKonto) target).ueberweisungEmpfangen(betrag, sender, vonKontonr, bankleitzahl, verwendungszweck);
            return true;
        }
        return false;
    }


    /**
     * Erstellt eine neue Kontonummer
     *
     * @return die neue Kontonummer
     */
    private long createAccoutNumber() {
        long newKontonummer = lastKontonummer;
        lastKontonummer++;
        return newKontonummer;
    }

    /**
     * Fuegt ein Konto zur Bank hinzu - Usage nur fuer testzwecke!
     *
     * @param k das Konto
     * @return die Kontonummer des neuen Kontos

    public long mockEinfuegen(Konto k) {
        long newAccountNumber = createAccoutNumber();
        konten.put(newAccountNumber, k);
        return newAccountNumber;
    }

    */
    /**
     * Sperrt alle Konten, die einen negativen Kontostand haben
     */
    public void pleitegeierSperren() {
        konten.values().stream()
                .filter(konto -> konto.getKontostand() < 0)
                .forEach(konto -> konto.sperren());
    }

    /**
     * Gibt eine Liste aller Kunden zurueck, die ein Kontostand ueber einem gewissen wert haben
     *
     * @return Liste aller Kunden
     */
    public List<Kunde> getKundenMitVollemKonto(double min) {
        return konten.values().stream()
                .filter(konto -> konto.getKontostand() > min)
                .map(konto -> konto.getInhaber())
                .collect(Collectors.toList());
    }

    /**
     * Gibt eine Liste aller Kontonummern zurueck, die nicht vergeben sind
     *
     * @return Liste aller Kontonummern, die nicht vergeben sind
     */
    public List<Long> getKontonummernLuecken() {
        return LongStream.rangeClosed(100_000, 101_000)
                .filter(number -> !konten.containsKey(number))
                .boxed()
                .collect(Collectors.toList());
    }

    /**
     * Gibt die Map aller Konten zurueck - testzweckens
     *
     * @return Map aller konten
     */
    public Map<Long, Konto> getKonten() {
        return konten;
    }

    @Override
    public Bank clone() {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());

        Bank clone = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
            clone = (Bank) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (clone == null){
            throw new NullPointerException("copying failed");
        }
        return clone;
    }

}