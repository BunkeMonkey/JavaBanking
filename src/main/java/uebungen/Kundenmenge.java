package uebungen;

import java.time.LocalDate;
import java.util.*;

import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verarbeitung.Sparbuch;

/**
 * verwaltet eine Menge von Kunden
 * @author Doro
 *
 */
public class Kundenmenge {
	

	/**
	 * erstellt eine Menge von Kunden und löscht die unnötigen
	 * wieder
	 * @param args
	 */
	public static void main(String[] args) {
		Kunde anna = new Kunde("Anna", "Müller", "hier", LocalDate.parse("1979-05-14"));
		Kunde berta = new Kunde("Berta", "Beerenbaum", "hier", LocalDate.parse("1980-03-15"));
		Kunde chris = new Kunde("Chris", "Tall", "hier", LocalDate.parse("1979-01-07"));
		Kunde anton = new Kunde("Anton", "Meier", "hier", LocalDate.parse("1982-10-23"));
		Kunde bert = new Kunde("Bert", "Chokowski", "hier", LocalDate.parse("1970-12-24"));
		Kunde doro = new Kunde("Doro", "Hubrich", "hier", LocalDate.parse("1976-07-13"));

		TreeSet<Kunde> kundenTree = new TreeSet<>();
		kundenTree.add(anna);
		kundenTree.add(berta);
		kundenTree.add(chris);
		kundenTree.add(anton);
		kundenTree.add(bert);
		kundenTree.add(doro);

		for (Kunde k : kundenTree){
			System.out.println(k);
		}

		System.out.println("Anzahl der Kunden: " + kundenTree.size());





		//ToDo: TreeSet mit den vorhandenen Kunden anlegen, Aufgaben 1-3
		
		
		Scanner tastatur = new Scanner(System.in);
		System.out.println("Nach welchem Namen wollen Sie suchen? ");
		String gesucht = tastatur.nextLine();

		for (Kunde k : kundenTree){
			String name = k.getName();
			int index = name.indexOf(",");
			name = name.substring(0,index);
			if (name.equalsIgnoreCase(gesucht)){
				System.out.println(k);
			}
		}

		Iterator<Kunde> iter = kundenTree.iterator();
		while (iter.hasNext()){
			Kunde k = iter.next();

			String name = k.getName();
			int index = name.indexOf(",");
			name = name.substring(index + 2);
			//System.out.println("name of kunde : " + name);
			if (name.charAt(0) == 'A'){
				System.out.println("kunden entfernt: "+ k.getName());
				iter.remove();
			}
		}
		System.out.println();
		System.out.println();

		System.out.println(anna.getGeburtstag());
		TreeSet<Kunde> kundenGeb = new TreeSet<>();

		
		//ToDo: Aufgabe 4-6



		
		Map<Long, Konto> kontenliste = Map.of(
				1L, new Girokonto(bert, 1, 1000),
				2L, new Girokonto(chris, 2, 1000),
				3L, new Sparbuch(chris, 3),
				4L, new Girokonto(berta, 4, 1000),
				5L, new Sparbuch(berta, 5),
				6L, new Girokonto(bert, 6, 1000),
				7L, new Girokonto(chris, 7, 1000),
				8L, new Girokonto(bert, 8, 1000),
				9L, new Sparbuch(chris, 9));
		
		//ToDo: Aufgabe 7 und 8
	}

}
