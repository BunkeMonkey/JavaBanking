package bankprojekt.verarbeitung;

import bankprojekt.verwaltung.GiroKontofabrik;
import bankprojekt.verwaltung.Sparbuchfabrik;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public class KontoObserverTests {

    Girokonto konto1;
    KontoObserver observer;

    @BeforeEach
    public void init(){
        konto1 = new Girokonto(new Kunde(), 1234567, 0);
        observer = Mockito.mock(KontoObserver.class);
        konto1.subscribe(observer);


    }

    @Test
    public void einzahlenTest(){
        konto1.einzahlen(3000);
        Mockito.verify(observer, Mockito.times(1)).update(3000.0);
    }

    @Test
    public void abhebenTest() throws GesperrtException {
        konto1.einzahlen(3000);
        konto1.abheben(1000);
        Mockito.verify(observer, Mockito.times(2)).update(ArgumentMatchers.anyDouble());
    }

    @Test
    public void abhebenFailTest() throws GesperrtException {
        konto1.abheben(1000);
        Mockito.verifyNoInteractions(observer);
    }

    @Test
    public void waehrungswechselTest() {
        konto1.waehrungswechsel(Waehrung.DOBRA);
        Mockito.verify(observer, Mockito.times(1)).update(ArgumentMatchers.anyDouble());
    }
}
