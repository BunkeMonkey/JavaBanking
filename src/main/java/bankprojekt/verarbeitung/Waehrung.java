package bankprojekt.verarbeitung;

import org.decimal4j.util.DoubleRounder;

/**
 * stellt eine Waehrung dar, die umgerechnet werden aknn
 */
public enum Waehrung {

    EUR(1),
    ESCUDO(109.8269),
    DOBRA(24304.7429),
    FRANC(490.92);

    private double exchangeRate;
    Waehrung(double exchangeRate){
        this.exchangeRate = exchangeRate;
    }


    /**
     * Umrechnung von Euro zu einer anderen Waehrung
     * @param betrag der umzurechnende Betrag in Euro
     * @return der umgerechnete Betrag in der neuen waehrung
     */
    public double euroInWaehrungUmrechnen(double betrag){
        return DoubleRounder.round(betrag * this.exchangeRate, 2);
    }

    /**
     * Umrechnung von einer Waehrung zu Euro
     * @param betrag der umzurechnende Betrag in der waehrung
     * @return der umgerechnete Betrag in Euro
     */
    public double waehrungInEuroUmrechnen(double betrag){
        return DoubleRounder.round(betrag / this.exchangeRate,2);
    }


    /**
     * Umrechnung von einer Waehrung in eine andere
     * @param betrag der umzurechnende Betrag
     * @param from die Ausgangswaehrung
     * @param to die Zielwaehrung
     * @return der umgerechnete Betrag in der Zielwaehrung
     */
    public double waehrungsUmrechnung(double betrag, Waehrung from, Waehrung to){
        if (from == to){
            return betrag;
        }
        betrag = from.waehrungInEuroUmrechnen(betrag);
        betrag = to.euroInWaehrungUmrechnen(betrag);
        return betrag;
    }


}
