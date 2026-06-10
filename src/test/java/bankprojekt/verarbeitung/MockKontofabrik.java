package bankprojekt.verarbeitung;

import bankprojekt.verwaltung.KontofabrikAbstract;

/**
 * Konto factory zur erstellung von Mock Konten
 */

public class MockKontofabrik extends KontofabrikAbstract {

    private Konto mockedKonto;
        public MockKontofabrik(Konto mockedKonto) {
            this.mockedKonto = mockedKonto;
        }


    @Override
    public Konto erstellen(Kunde inhaber, long nummer) {
        return mockedKonto;
    }
}
