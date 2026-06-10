package bankprojekt.verarbeitung;

import java.util.HashMap;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Ein Konto, das mit Aktien handeln kann
 */

public class AktienKonto extends Konto{

    private HashMap<Aktie,Integer> aktienDepot = new HashMap<>();


    /**
     * Kauft die Aktie mit der wkn, wenn der Kurs unter dem hoechstpreis liegt
     * @param wkn Wertpapierkennnummer
     * @param anzahl Anzahl der Aktien
     * @param hoechstpreis maximaler Preis zum Kauf
     * @return Future mit dem Preis, wenn die Aktie gekauft wurde
     * @throws IllegalArgumentException wenn die Aktie nicht existiert
     */
    public Future<Double> kaufAuftrag(String wkn, int anzahl, double hoechstpreis) throws IllegalArgumentException{
        Aktie share = Aktie.getAktie(wkn);
        if (share == null){
            throw new IllegalArgumentException("Share is not real");
        }
        if (hoechstpreis < 0){
            throw new IllegalArgumentException("hoechstpreis cant be negative");
        }
        if (anzahl < 0){
            throw new IllegalArgumentException("anzahl cant be negative");
        }
        Lock buyLock = new ReentrantLock();
        Condition buyAvailable = buyLock.newCondition();

        //check ob anzahl * hoechstpreis < kontostand
        ScheduledExecutorService buyingBot = Executors.newScheduledThreadPool(10);

        Future<Double> price = buyingBot.schedule( () -> {
            try {
                buyLock.lock();
                buyAvailable.await();
            } finally {
                buyLock.unlock();
            }
            if (getKontostand() < anzahl * share.getKurs()){
                buyingBot.shutdown();
                return 0d;
            }
            double pricePaid = share.getKurs() * anzahl;
            abheben(pricePaid);
            aktienDepot.put(share,anzahl);
            System.out.println("!! bought " + wkn + " for: " + pricePaid);
            return pricePaid;
        },0,TimeUnit.SECONDS);


        buyingBot.scheduleWithFixedDelay( () -> {
            if (share.getKurs() < hoechstpreis){
                    try {
                        buyLock.lock();
                        buyAvailable.signalAll();
                    }finally {
                        buyLock.unlock();
                    }
            }
        },0,1000, TimeUnit.MILLISECONDS);

        return price;



    }

    /**
     * Verkauft die Aktie mit der wkn, wenn der Kurs über dem minimalPreis liegt
     * @param wkn Wertpapierkennnummer
     * @param minimalPreis minimaler Preis zum Verkauf
     * @return Future mit dem Gewinn, wenn die Aktie verkauft wurde, 0 wenn die aktie nicht im depot ist
     * @throws IllegalArgumentException wenn die Aktie nicht existiert
     */
    public Future<Double> verkaufAuftrag(String wkn, double minimalPreis) throws IllegalArgumentException{
        ScheduledExecutorService sellingBot = Executors.newScheduledThreadPool(10);
        Aktie share = Aktie.getAktie(wkn);
        if (share == null){
            throw new IllegalArgumentException("share doesnt exist");
        }
        if (minimalPreis < 0){
            throw new IllegalArgumentException("minimal price cant be negative");
        }


        Lock sellLock = new ReentrantLock();
        Condition sellCondition = sellLock.newCondition();

        Future<Double> profit = sellingBot.schedule( () -> {
            if (!aktienDepot.containsKey(share)) {
                return 0d;
            }
            try {
                sellLock.lock();
                sellCondition.await();
            }finally {
                sellLock.unlock();
            }
            sellingBot.shutdown();
            double earnings = share.getKurs() * aktienDepot.get(share);
            aktienDepot.remove(share);
            einzahlen(earnings);
            System.out.println("!! sold: " + wkn +" for: "+ earnings);
            return earnings;
        },0,TimeUnit.SECONDS);

        sellingBot.scheduleWithFixedDelay( () -> {
          if (share.getKurs() > minimalPreis){
              try{
                  sellLock.lock();
                  sellCondition.signalAll();
              }finally{
                  sellLock.unlock();
              }
          }
        },0,1000,TimeUnit.MILLISECONDS);


        return profit;
    }

    /**
     * Gibt die Aktie mit der wkn in das Depot **for testing**
     * @param wkn Wertpapierkennnummer
     * @param anzahl Anzahl der Aktien
     */
    public void giveAktie(String wkn, int anzahl){
        Aktie share = Aktie.getAktie(wkn);
        if (share == null){
            throw new IllegalArgumentException("share doesnt exist");
        }
        aktienDepot.put(share,anzahl);
    }


/*
    @Override
    public boolean abheben(double betrag) throws GesperrtException {
        if (betrag <= 0 || Double.isNaN(betrag)|| Double.isInfinite(betrag)) {
            throw new IllegalArgumentException("Betrag ungültig");
        }
        if(this.isGesperrt())
        {
            GesperrtException e = new GesperrtException(this.getKontonummer());
            throw e;
        }
        if (betrag > getKontostand()){
            return false;
        }
        setKontostand(getKontostand() - betrag);
        return true;

    }

 */


    @Override
    protected void extraActionsAbheben(double betrag) {

    }

    @Override
    protected boolean abhebKonditionen(double betrag) {
        if (getKontostand() > betrag){
            return true;
        }
        return false;
    }
}
