package bankprojekt.verarbeitung;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Eine Aktie, die ständig ihren Kurs verändert
 * @author Doro
 *
 */
public class Aktie implements Serializable {
	
	private static Map<String, Aktie> alleAktien = new HashMap<>();
	private String wkn;
	private double kurs;
	
	/**
	 * gibt die Aktie mit der gewünschten Wertpapierkennnummer zurück
	 * @param wkn Wertpapierkennnummer
	 * @return Aktie mit der angegebenen Wertpapierkennnummer oder null, wenn es diese WKN
	 * 			nicht gibt.
	 */
	public static Aktie getAktie(String wkn)
	{
		return alleAktien.get(wkn);
	}
	
	/**
	 * erstellt eine neu Aktie mit den angegebenen Werten
	 * @param wkn Wertpapierkennnummer
	 * @param k aktueller Kurs
	 * @throws IllegalArgumentException wenn einer der Parameter null bzw. negativ ist
	 * 		                            oder es eine Aktie mit dieser WKN bereits gibt
	 */
	public Aktie(String wkn, double k) {
		if(wkn == null || k <= 0 || alleAktien.containsKey(wkn))
			throw new IllegalArgumentException();	
		this.wkn = wkn;
		this.kurs = k;
		alleAktien.put(wkn, this);

		ScheduledExecutorService service = Executors.newScheduledThreadPool(5);

		Random r = new Random();
		service.scheduleWithFixedDelay( () ->
				{this.kurs += kurs * (r.nextDouble(-3,3) / 100 );
					System.out.println(wkn + " kurs: " +kurs);
				}, 0,1000, TimeUnit.MILLISECONDS);
		//service.schedule( () -> service.shutdown(), 5,TimeUnit.SECONDS);
	}


	/**
	 * Wertpapierkennnummer
	 * @return WKN der Aktie
	 */
	public String getWkn() {
		return wkn;
	}

	/**
	 * aktueller Kurs
	 * @return Kurs der Aktie
	 */
	public double getKurs() {
		return kurs;
	}
}
