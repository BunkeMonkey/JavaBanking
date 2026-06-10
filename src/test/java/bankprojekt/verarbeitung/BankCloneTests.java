package bankprojekt.verarbeitung;

import bankprojekt.verwaltung.Bank;
import bankprojekt.verwaltung.GiroKontofabrik;
import bankprojekt.verwaltung.KontoNummerNichtVorhandenException;
import bankprojekt.verwaltung.Sparbuchfabrik;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class BankCloneTests {

    Bank bank;
    Bank clone;
    long acc1;
    long acc2;
    long acc3;
    //TODO: shit no workie
/*
    @BeforeEach
    public void init(){
        Kunde k1 = new Kunde("Max", "Mustermann", "zuhause", LocalDate.parse("1979-05-14"));
        Kunde k2 = new Kunde();
        Kunde k3 = new Kunde("Frida", "Klust", "draußen", LocalDate.parse("1980-03-15"));
        GiroKontofabrik giroKontofabrik = GiroKontofabrik.getInstance();
        Sparbuchfabrik sparbuchfabrik = Sparbuchfabrik.getInstance();
        bank = new Bank(123);
        acc1 = bank.kontoErstellen(giroKontofabrik,k1);//bank.girokontoErstellen(k1);
        acc2 = bank.kontoErstellen(sparbuchfabrik,k2);//bank.sparbuchErstellen(k2);
        acc3 = bank.kontoErstellen(giroKontofabrik, k3);//bank.girokontoErstellen(k3);
        clone = bank.clone();
    }

    @Test
    public void cloneNotSame() {
        assertNotSame(bank, clone);
    }

    @Test
    public void attributesSame(){
        assertEquals(bank.getBankleitzahl(), clone.getBankleitzahl());

    }

    @Test
    public void cloneAccountsNotSame() throws KontoNummerNichtVorhandenException {
        bank.geldEinzahlen(acc1,1000);
        assertNotEquals(bank.getKontostand(acc1), clone.getKontostand(acc1));
    }

 */
}
