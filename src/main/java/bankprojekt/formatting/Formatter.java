package bankprojekt.formatting;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Scanner;

/**
 * Klasse die Input formatiert und in eine Datei Speichert
 */
public class Formatter {


    public static void main(String[] args) throws IOException {
        File outputFile = new File("./target/outputFormatted.txt");

        try (FileWriter fwriter = new FileWriter(outputFile);

             PrintWriter pwriter = new PrintWriter(fwriter))
             {
            //1.
            Scanner scanner = new Scanner(System.in);
            long firstNumber = scanner.nextLong();
            pwriter.printf("%d%n", firstNumber);


            //2.
            pwriter.printf("%012d%n", firstNumber);


            //3.

            pwriter.printf("%+,d%n", firstNumber);
            //4.

            pwriter.printf("%X%n", firstNumber);

            //5.
            double secondNumer = scanner.nextDouble();


            pwriter.printf("%f%n", secondNumer);
            //6.

            pwriter.printf("%+.5f%n", secondNumer);
            //7.

            pwriter.printf("%E%n", secondNumer);
            //8.

            pwriter.printf(Locale.US, "%.2f%n", secondNumer);
            //9.
            LocalDate heute = LocalDate.now();


            pwriter.printf(Locale.GERMANY, "%1$te %1$tB %1$tY (%1$ta)%n", heute);
            //10.

            pwriter.printf(Locale.ITALY, "%1$td.%1$tm.%1$ty (%1$tA)%n", heute);
            //11.
            LocalTime jetzt = LocalTime.now();


            pwriter.printf(Locale.ENGLISH, "%1$tl:%1$tM %1$tp%n", jetzt);


        }catch (IOException e){
            e.printStackTrace();
        }




    }


}
