package bankprojekt.verarbeitung;

public interface KontoObserver {


    /**
     * wird aufgerufen, wenn sich der Kontostand des beobachteten Kontos ändert
     * @param kontoStand neuer Kontostand
     */
    public void update(Double kontoStand);
}
