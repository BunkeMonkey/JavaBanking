package bankprojekt.verarbeitung;

import bankprojekt.verwaltung.Bank;
import bankprojekt.verwaltung.KontoNummerNichtVorhandenException;
import org.checkerframework.checker.units.qual.K;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class BankTests {

    //test geldUeberweisen und kontoLoeschen

    //test cases for geldUeberweisen:
    //kontos are null
    //kontos do not extend ueberweisungsfaehig
    //no changing interactions when exceptions are thrown

    UeberweisungsfaehigesKonto giro1 = Mockito.mock(UeberweisungsfaehigesKonto.class);
    UeberweisungsfaehigesKonto giro2 = Mockito.mock(UeberweisungsfaehigesKonto.class);
    Konto spar1 = Mockito.mock(Konto.class);
    Bank bank = new Bank(1337);



    @Test
    public void geldUeberweisenAllesKlappt() throws GesperrtException, KontoNummerNichtVorhandenException {
    //To mock: getInhaber .getName ? kunde mock?
        //ueberweisung absenden
        //ueberweisung empfangen
        MockKontofabrik mockKontofabrik1 = new MockKontofabrik(giro1);
        MockKontofabrik mockKontofabrik2 = new MockKontofabrik(giro2);
        long mock1Number = bank.kontoErstellen(mockKontofabrik1, new Kunde());
        long mock2Number = bank.kontoErstellen(mockKontofabrik2, new Kunde());
        //long mock1Number = bank.mockEinfuegen(giro1);
        //long mock2Number = bank.mockEinfuegen(giro2);

        Mockito.when(giro1.getInhaber()).thenReturn(new Kunde());
        Mockito.when(giro2.getInhaber()).thenReturn(new Kunde());
        Mockito.when(giro1.ueberweisungAbsenden(500,"Mustermann, Max",mock2Number,bank.getBankleitzahl(),"bankTest")).thenReturn(true);

       boolean success =  bank.geldUeberweisen(mock1Number,mock2Number,500,"bankTest");

        Mockito.verify(giro1, Mockito.times(1)).ueberweisungAbsenden(500,"Mustermann, Max",mock2Number,bank.getBankleitzahl(),"bankTest");
        Mockito.verify(giro2, Mockito.times(1)).ueberweisungEmpfangen(500,"Mustermann, Max",mock1Number,bank.getBankleitzahl(),"bankTest");
        assertTrue(success);
    }
    @Test
    public void geldUeberweisenZielKontoNichtInListe() throws GesperrtException {
        MockKontofabrik mockKontofabrik1 = new MockKontofabrik(giro1);
        long mock1Number = bank.kontoErstellen(mockKontofabrik1, new Kunde());
        long nachKontoNr = 8843;
        Exception e = assertThrows(KontoNummerNichtVorhandenException.class,()-> bank.geldUeberweisen(mock1Number,nachKontoNr,500,"bankTest") );
        assertEquals("Konto mit der nummer: " + nachKontoNr + " existiert nicht",e.getMessage());
        Mockito.verify(giro1, Mockito.times(0)).ueberweisungAbsenden(ArgumentMatchers.anyDouble(),ArgumentMatchers.anyString(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyString());


    }
    @Test
    public void geldUeberweisenQuellKontoNichtInListe() {
        MockKontofabrik mockKontofabrik1 = new MockKontofabrik(giro1);
        long mock1Number = bank.kontoErstellen(mockKontofabrik1, new Kunde());
        //long mock1Number = bank.mockEinfuegen(giro1);
        long vonKontoNr = 8843;
        Exception e = assertThrows(KontoNummerNichtVorhandenException.class,()-> bank.geldUeberweisen(vonKontoNr,mock1Number,500,"bankTest") );
        assertEquals("Konto mit der nummer: " + vonKontoNr+ " existiert nicht",e.getMessage());
        Mockito.verify(giro1, Mockito.times(0)).ueberweisungEmpfangen(ArgumentMatchers.anyDouble(),ArgumentMatchers.anyString(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyString());

    }

    @Test
    public void geldUeberweisenZielKontoNichtUeberweisungsfaehig() throws GesperrtException, KontoNummerNichtVorhandenException {
        MockKontofabrik mockKontofabrik1 = new MockKontofabrik(giro1);
        MockKontofabrik mockKontofabrik2 = new MockKontofabrik(spar1);
        long mockGiroNumber = bank.kontoErstellen(mockKontofabrik1, new Kunde());//bank.mockEinfuegen(giro1);
        long mockSparbuchNumber = bank.kontoErstellen(mockKontofabrik2, new Kunde());//bank.mockEinfuegen(spar1);

        assertFalse(bank.geldUeberweisen(mockGiroNumber,mockSparbuchNumber,500,"ShouldFailTest"));
        Mockito.verifyNoInteractions(giro1,spar1);

    }

    @Test
    public void geldUeberweisenQuellKontoNichtUeberweisungsfaehig() throws GesperrtException, KontoNummerNichtVorhandenException {
        MockKontofabrik mockKontofabrik1 = new MockKontofabrik(giro1);
        MockKontofabrik mockKontofabrik2 = new MockKontofabrik(spar1);
        long mockGiroNumber = bank.kontoErstellen(mockKontofabrik1, new Kunde());//bank.mockEinfuegen(giro1);
        long mockSparbuchNumber = bank.kontoErstellen(mockKontofabrik2, new Kunde());//bank.mockEinfuegen(spar1);

        assertFalse(bank.geldUeberweisen(mockSparbuchNumber,mockGiroNumber,500,"ShouldFailTest"));
        Mockito.verifyNoInteractions(giro1,spar1);

    }

    @Test
    public void geldUeberweisenKontoAbsendenIstFalse() throws GesperrtException, KontoNummerNichtVorhandenException {
        MockKontofabrik mockKontofabrik1 = new MockKontofabrik(giro1);
        MockKontofabrik mockKontofabrik2 = new MockKontofabrik(giro2);
        long mock1Number = bank.kontoErstellen(mockKontofabrik1, new Kunde());
        long mock2Number = bank.kontoErstellen(mockKontofabrik2, new Kunde());
        //long mock1Number = bank.mockEinfuegen(giro1);
        //long mock2Number = bank.mockEinfuegen(giro2);

        Mockito.when(giro1.getInhaber()).thenReturn(new Kunde());
        Mockito.when(giro2.getInhaber()).thenReturn(new Kunde());
        Mockito.when(giro1.ueberweisungAbsenden(500,"Mustermann, Max",mock2Number,bank.getBankleitzahl(),"bankTest")).thenReturn(false);

        boolean success =  bank.geldUeberweisen(mock1Number,mock2Number,500,"bankTest");

        Mockito.verify(giro1).ueberweisungAbsenden(500,"Mustermann, Max",mock2Number,bank.getBankleitzahl(),"bankTest");
        Mockito.verify(giro2, Mockito.times(0)).ueberweisungEmpfangen(ArgumentMatchers.anyDouble(),ArgumentMatchers.anyString(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyString());
        assertFalse(success);

    }

    @Test
    public void geldUeberweisenKontoGesperrt() throws GesperrtException {
        MockKontofabrik mockKontofabrik1 = new MockKontofabrik(giro1);
        MockKontofabrik mockKontofabrik2 = new MockKontofabrik(giro2);
        long mock1Number = bank.kontoErstellen(mockKontofabrik1, new Kunde());
        long mock2Number = bank.kontoErstellen(mockKontofabrik2, new Kunde());
        //long mock1Number = bank.mockEinfuegen(giro1);
        //long mock2Number = bank.mockEinfuegen(giro2);

        Mockito.when(giro1.getInhaber()).thenReturn(new Kunde());
        Mockito.when(giro2.getInhaber()).thenReturn(new Kunde());

        Mockito.when(giro1.ueberweisungAbsenden(ArgumentMatchers.anyDouble(),ArgumentMatchers.anyString(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyString()))
                .thenThrow(new GesperrtException(mock1Number));

        Exception e = assertThrows(GesperrtException.class, ()-> bank.geldUeberweisen(mock1Number,mock2Number,500,"throws gesperrt"));
        assertEquals("Zugriff auf gesperrtes Konto mit Kontonummer " + mock1Number, e.getMessage());
        Mockito.verify(giro1, Mockito.times(0)).setKontostand(ArgumentMatchers.anyDouble());
        //Mockito.verifyNoMoreInteractions(giro2);
        Mockito.verify(giro2, Mockito.times(0)).ueberweisungEmpfangen(ArgumentMatchers.anyDouble(),ArgumentMatchers.anyString(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyString());

    }

    @Test
    public void geldUeberweisenThrowsIllegalArgumentException() throws IllegalArgumentException, GesperrtException, KontoNummerNichtVorhandenException {

        MockKontofabrik mockKontofabrik1 = new MockKontofabrik(giro1);
        MockKontofabrik mockKontofabrik2 = new MockKontofabrik(giro2);
        long mock1Number = bank.kontoErstellen(mockKontofabrik1, new Kunde());
        long mock2Number = bank.kontoErstellen(mockKontofabrik2, new Kunde());
        //long mock1Number = bank.mockEinfuegen(giro1);
        //long mock2Number = bank.mockEinfuegen(giro2);

        Mockito.when(giro1.getInhaber()).thenReturn(new Kunde());
        Mockito.when(giro2.getInhaber()).thenReturn(new Kunde());

        Mockito.when(giro1.ueberweisungAbsenden(ArgumentMatchers.anyDouble(),ArgumentMatchers.anyString(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyString()))
                .thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, ()->bank.geldUeberweisen(mock1Number, mock2Number, -500, "shoul throw Illegal"));
        Mockito.verify(giro1, Mockito.times(0)).setKontostand(ArgumentMatchers.anyDouble());
        Mockito.verify(giro2, Mockito.times(0)).ueberweisungEmpfangen(ArgumentMatchers.anyDouble(),ArgumentMatchers.anyString(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyString());

    }



    //test cases for kontoLoeschen
    //konto was there is gone now -> true
    //konto was never there -> false
    @Test
    public void kontoLoeschenWarVorhandenTest(){
        MockKontofabrik mockKontofabrik1 = new MockKontofabrik(spar1);
        long mock1Number = bank.kontoErstellen(mockKontofabrik1, new Kunde());
        //long mock1Number = bank.mockEinfuegen(spar1);
        assertTrue(bank.getAlleKontonummern().contains(mock1Number));
        assertTrue(bank.kontoLoeschen(mock1Number));
        assertFalse(bank.getAlleKontonummern().contains(mock1Number));
    }
    @Test
    public void kontoLoeschenWarNichtVorhandenTest(){
        assertFalse(bank.kontoLoeschen(762354));
    }
}
