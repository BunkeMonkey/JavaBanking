package bankprojekt.verarbeitung;

//import com.google.common.primitives.Doubles;
//Abkürzung des Klassennamens ist jetzt erlaubt

import javafx.beans.property.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * stellt ein allgemeines Bank-Konto dar
 */
public abstract class Konto implements Comparable<Konto>, Serializable
{
	/** 
	 * der Kontoinhaber
	 */
	private Kunde inhaber;

	/**
	 * die Kontonummer
	 */
	private final long nummer;

	/**
	 * der aktuelle Kontostand
	 */
	//private double kontostand;

	private ReadOnlyDoubleWrapper kontostand = new ReadOnlyDoubleWrapper();
	/**
	 * die Waehrung des Kontos
	 */
	private Waehrung waehrung = Waehrung.EUR;

	private List<KontoObserver> observers = new ArrayList<>();

	private transient BooleanProperty  negativeBalance = new  SimpleBooleanProperty(false);

	/**
	 * setzt den aktuellen Kontostand
	 * @param kontostand neuer Kontostand
	 */
	protected void setKontostand(double kontostand) {
		//this.kontostand = kontostand;
		this.kontostand.set(kontostand);
		negativeBalance.set(this.kontostand.get() < 0.0);
		notifyObservers();
	}

	/**
	 * Wenn das Konto gesperrt ist (gesperrt = true), können keine Aktionen daran mehr vorgenommen werden,
	 * die zum Schaden des Kontoinhabers wären (abheben, Inhaberwechsel)
	 */
	public transient BooleanProperty gesperrt = new SimpleBooleanProperty(false);

	/**
	 * Setzt die beiden Eigenschaften kontoinhaber und kontonummer auf die angegebenen Werte,
	 * der anfängliche Kontostand wird auf 0 gesetzt.
	 *
	 * @param inhaber der Inhaber
	 * @param kontonummer die gewünschte Kontonummer
	 * @throws IllegalArgumentException wenn der inhaber null ist
	 */
	public Konto(Kunde inhaber, long kontonummer) {
		if(inhaber == null)
			throw new IllegalArgumentException("Inhaber darf nicht null sein!");
		this.inhaber = inhaber;
		this.nummer = kontonummer;
		//this.kontostand = 0;
		gesperrt.set(false);
	}
	
	/**
	 * setzt alle Eigenschaften des Kontos auf Standardwerte
	 */
	public Konto() {
		this(Kunde.MUSTERMANN, 1234567);
	}

	/**
	 * liefert den Kontoinhaber zurück
	 * @return   der Inhaber
	 */
	public  Kunde getInhaber() {
		return this.inhaber;
	}
	
	/**
	 * setzt den Kontoinhaber
	 * @param kinh   neuer Kontoinhaber
	 * @throws GesperrtException wenn das Konto gesperrt ist
	 * @throws IllegalArgumentException wenn kinh null ist
	 */
	public final void setInhaber(Kunde kinh) throws GesperrtException{
		if (kinh == null)
			throw new IllegalArgumentException("Der Inhaber darf nicht null sein!");
		if(gesperrt.get())
			throw new GesperrtException(this.nummer);        
		this.inhaber = kinh;

	}
	
	/**
	 * liefert den aktuellen Kontostand
	 * @return   Kontostand
	 */
	public double getKontostand() {
		return kontostand.get();
	}

	/**
	 * liefert die ReadOnlyProperty des kontostandes
	 * @return Kontostand als readOnlyProperty
	 */
	public ReadOnlyDoubleProperty kontostandProperty(){
		return kontostand.getReadOnlyProperty();
	}

	/**
	 * liefert die Kontonummer zurück
	 * @return   Kontonummer
	 */
	public final long getKontonummer() {
		return nummer;
	}

	/**
	 * liefert zurück, ob das Konto gesperrt ist oder nicht
	 * @return true, wenn das Konto gesperrt ist
	 */
	public boolean isGesperrt() { //Achtung: nicht get... bei booleschen Werten
		return gesperrt.get();
	}

	/**
	 * Liefert die Property des gesperrt Status
	 * @return gesperrt als Property
	 */
	public BooleanProperty gesperrtProperty(){
		return gesperrt;
	}

	/**
	 * liefert zurück, ob das Konto gesperrt ist oder nicht
	 * @return true, wenn das Konto gesperrt ist
	 */
	public boolean isNegativeBalance(){
		return negativeBalance.get();
	}

	/**
	 * Liefert die Property des negativen Kontostandes
	 * @return negativeBalance als Property
	 */
	public BooleanProperty negativeBalanceProperty(){
		return negativeBalance;
	}
	
	/**
	 * Erhöht den Kontostand um den eingezahlten Betrag.
	 *
	 * @param betrag double
	 * @throws IllegalArgumentException wenn der betrag negativ ist 
	 */
	public void einzahlen(double betrag) {
		if (betrag < 0 ||!Double.isFinite(betrag)) {
			throw new IllegalArgumentException("Falscher Betrag");
		}
		setKontostand(getKontostand() + betrag);
	}
	
	@Override
	public String toString() {
		String ausgabe;
		ausgabe = "Kontonummer: " + this.getKontonummerFormatiert()
				+ System.getProperty("line.separator");
		ausgabe += "Inhaber: " + this.inhaber;
		ausgabe += "Aktueller Kontostand: " + getKontostandFormatiert() + " ";
		ausgabe += this.getGesperrtText() + System.getProperty("line.separator");
		return ausgabe;
	}

	/**
	 * Mit dieser Methode wird der geforderte Betrag vom Konto abgehoben, wenn es nicht gesperrt ist
	 * und die speziellen Abheberegeln des jeweiligen Kontotyps die Abhebung erlauben
	 *
	 * @param betrag double
	 * @throws GesperrtException wenn das Konto gesperrt ist
	 * @throws IllegalArgumentException wenn der betrag negativ oder unendlich oder NaN ist 
	 * @return true, wenn die Abhebung geklappt hat, 
	 * 		   false, wenn sie abgelehnt wurde
	 */

	public final boolean abheben(double betrag) throws GesperrtException, IllegalArgumentException {
		if (betrag < 0 || Double.isNaN(betrag)|| Double.isInfinite(betrag)) {
			throw new IllegalArgumentException("Betrag ungültig");
		}
		if(this.isGesperrt())
			throw new GesperrtException(this.getKontonummer());

		if (!abhebKonditionen(betrag)){
			return false;
		}
		setKontostand(getKontostand() - betrag);
		extraActionsAbheben(betrag);

		return true;

	}


	/**
	 * Zum ausführen Weiterer Aktionen die Unterklassen von Konten nach einer abhebung machen müssen
	 * @param betrag
	 */
	protected abstract void extraActionsAbheben(double betrag);
	/**
	 * Methode zum checken der Konditionen für eine Abhebung
	 * @return true, wenn die Konditionen erfüllt sind,
	 * 		   false, wenn sie nicht erfüllt sind
	 */
	protected abstract boolean abhebKonditionen(double betrag);
	
	/**
	 * sperrt das Konto, Aktionen zum Schaden des Benutzers sind nicht mehr möglich.
	 */
	public void sperren() {
		gesperrt.set(true);
	}

	/**
	 * entsperrt das Konto, alle Kontoaktionen sind wieder möglich.
	 */
	public void entsperren() {
		gesperrt.set(false);
	}
	
	
	/**
	 * liefert eine String-Ausgabe, wenn das Konto gesperrt ist
	 * @return "GESPERRT", wenn das Konto gesperrt ist, ansonsten ""
	 */
	public String getGesperrtText()
	{
		if (isGesperrt())
		{
			return "GESPERRT";
		}
		else
		{
			return "";
		}
	}
	
	/**
	 * liefert die ordentlich formatierte Kontonummer
	 * @return auf 10 Stellen formatierte Kontonummer
	 */
	public String getKontonummerFormatiert()
	{
		return String.format("%10d", this.nummer);
	}
	
	/**
	 * liefert den ordentlich formatierten Kontostand
	 * @return formatierter Kontostand mit 2 Nachkommastellen und Währungssymbol
	 */
	public String getKontostandFormatiert()
	{
		return String.format("%10.2f "+ this.waehrung , this.getKontostand());
	}

	/**
	 * hebt einen Betrag in der angegebenen Waehrung ab
	 * @param betrag double
	 * @param w die Waehrung des Betrags
	 * @return true, wenn die Abhebung geklappt hat,
	 * 		   false, wenn sie abgelehnt wurde
	 * @throws GesperrtException wenn das Konto gesperrt ist
	 * @throws IllegalArgumentException wenn der betrag negativ ist
	 * @throws NullPointerException wenn w null ist
	 */
	public boolean abheben(double betrag, Waehrung w) throws GesperrtException {
		if (w == null){
			throw new NullPointerException("Waehrung darf nicht null sein");
		}

		if (w == this.waehrung) {
			return abheben(betrag);
		}


		double abzuhebenderBetrag;
		abzuhebenderBetrag = w.waehrungsUmrechnung(betrag, w, this.waehrung);
		return abheben(abzuhebenderBetrag);

	}

	/**
	 * zahlt einen Betrag auf das Konto ein
	 * @param betrag double
	 * @param w die Waehrung des Betrags
	 * @throws IllegalArgumentException wenn der betrag negativ ist
	 * @throws NullPointerException wenn w null ist
	 */
	public void einzahlen(double betrag, Waehrung w) throws IllegalArgumentException, NullPointerException{
		if (w == null){
			throw new NullPointerException("Waehrung darf nicht null sein");
		}

		if (w == this.waehrung){
			einzahlen(betrag);
			return;
		}
		double einzahlenderBetrag;

		einzahlenderBetrag = w.waehrungsUmrechnung(betrag, w, this.waehrung);
		einzahlen(einzahlenderBetrag);

	}

	/**
	 * liefert die aktuelle Waehrung des Kontos
	 * @return die aktuelle Waehrung
	 */
	public Waehrung getAktuelleWaehrung(){
		return this.waehrung;
	}

	/**
	 * wechselt die Waehrung des Kontos
	 * @param neu die neue Waehrung
	 * @throws NullPointerException wenn neu null ist
	 */
	public void waehrungswechsel(Waehrung neu) throws NullPointerException{
		if (neu == this.waehrung){return;}

		double balance = this.getKontostand();
		balance = this.waehrung.waehrungsUmrechnung(balance, this.waehrung, neu);
		setKontostand(balance);
		this.waehrung = neu;


	}
	
	/**
	 * Vergleich von this mit other; Zwei Konten gelten als gleich,
	 * wen sie die gleiche Kontonummer haben
	 * @param other das Vergleichskonto
	 * @return true, wenn beide Konten die gleiche Nummer haben
	 */
	@Override
	public boolean equals(Object other)
	{
		if(this == other)
			return true;
		if(other == null)
			return false;
		if(this.getClass() != other.getClass())
			return false;
		if(this.nummer == ((Konto)other).nummer)
			return true;
		else
			return false;
	}
	
	@Override
	public int hashCode()
	{
		return 31 + (int) (this.nummer ^ (this.nummer >>> 32));
	}

	@Override
	public int compareTo(Konto other)
	{
		if(other.getKontonummer() > this.getKontonummer())
			return -1;
		if(other.getKontonummer() < this.getKontonummer())
			return 1;
		return 0;
	}


	/**
	 * Fügt einen Observer hinzu
	 * @param observer der hinzuzufügende Observer
	 */
	public void subscribe(KontoObserver observer){
		if (observer == null) return;
		observers.add(observer);
	}

	/**
	 * Entfernt einen Observer
	 * @param observer der zu entfernende Observer
	 */
	public void unsubscribe(KontoObserver observer){
		observers.remove(observer);
	}

	/**
	 * Benachrichtigt alle Observer über eine änderung
	 */
	protected void notifyObservers(){
		observers.forEach(observer -> observer.update(this.getKontostand()));
	}
}
