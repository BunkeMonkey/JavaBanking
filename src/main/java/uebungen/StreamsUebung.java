package uebungen;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;
/**
* Klasse mit Übungen zu Streams
*/
public class StreamsUebung {

	/**
	 * Übungen zu Streams
	 * @param args wird nicht verwendet
	 */
	public static void main(String[] args) {
		Kunde hans = new Kunde("Hans", "Meier", "Unterm Regenbogen 19",LocalDate.of(1990, 1, 5));
		Kunde otto = new Kunde("Otto", "Kar", "Hoch über den Wolken 7",LocalDate.of(1992, 2, 25));
		Kunde sabrina = new Kunde("Sabrina", "August", "Im Wald 15",LocalDate.of(1988, 3, 21));
		Konto eins = new Girokonto(hans, 123, 0);
		eins.einzahlen(100);
		Konto zwei = new Girokonto(otto, 234, 0);
		zwei.einzahlen(200);
		Konto drei = new Girokonto(sabrina, 333, 0);
		drei.einzahlen(100);
		Konto vier = new Girokonto(sabrina, 432, 0);
		vier.einzahlen(500);
		Konto fuenf = new Girokonto(otto, 598, 0);
		fuenf.einzahlen(600);
		
		Map<Long, Konto> kontenmap = new HashMap<Long, Konto>();
		kontenmap.put(123L, eins);
		kontenmap.put(234L, zwei);
		kontenmap.put(333L, drei);
		kontenmap.put(432L, vier);
		kontenmap.put(598L, fuenf);
		
		//Liste aller Kunden ohne doppelte:
		List<Kunde> a = kontenmap.values().stream()
						.map(konto -> konto.getInhaber())
						.distinct()
						.collect(Collectors.toList());
		System.out.println(a);
		System.out.println("-----------------");
		
		//Liste aller Kunden, sortiert nach ihrem Kontostand:
		List<Kunde> b = kontenmap.values().stream()
						.sorted( (konto1, konto2) -> Double.compare(konto2.getKontostand(), konto1.getKontostand()) )
						.map(konto -> konto.getInhaber()).collect(Collectors.toList());
		System.out.println(b);
		System.out.println("-----------------");

		//fängt mindestens ein Kunde mit 'A' an?
		boolean c = kontenmap.values().stream().anyMatch(konto -> konto.getInhaber().getVorname().startsWith("A"));
		System.out.println(c);
		System.out.println("-----------------");

		//alle Kundennamen in einem String: Konto:: getInhaber.ge
		String d = kontenmap.values().stream().map(konto -> konto.getInhaber().getVorname())
				.reduce("", (allNames, currentName) -> allNames + System.lineSeparator() +  currentName );
		System.out.println(d);
		System.out.println("-----------------");

		//Haben alle Kunden im Jahr 1990 Geburtstag?
		boolean e =  kontenmap.values().stream().allMatch(konto -> konto.getInhaber().getGeburtstag().getYear() == 1990);
		System.out.println(e);
		System.out.println("-----------------");

		//Wie viele verschiedene Kunden gibt es?
		long f = kontenmap.values().stream().map(konto -> konto.getInhaber()).distinct().count();
		System.out.println(f);
		System.out.println("-----------------");

		//Map, die zu jedem Kunden alle seine Konten auflistet:
		//Map<Kunde, List<Konto>> g =
		//System.out.println(g);
					
	}

}
