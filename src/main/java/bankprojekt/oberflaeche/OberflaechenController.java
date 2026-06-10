package bankprojekt.oberflaeche;

import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.checkerframework.checker.units.qual.K;

import javax.swing.*;
import java.awt.geom.GeneralPath;
import java.time.LocalDate;

public class OberflaechenController extends Application {


    private Stage primaryStage;

    private KontoOberflaeche view;

    private Girokonto model;

    @Override
    public void start(Stage primaryStage) {
        Kunde customer = new Kunde("daniel", "marks", "marzahn", LocalDate.now());
        model = new Girokonto(customer, 13376969420L, 1000);
        view = new KontoOberflaeche(this, model);
        Scene scene = new Scene(view, 320, 343);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Konto");
        primaryStage.show();
    }

    /**
     * Methode zum Einzahlen von Geld und setten der Meldung falls etwas schief geht
     * @param value der Betrag der eingezahlt werden soll
     */
    public void einzahlen(String value) {
        try {
            double doubleValue = Double.parseDouble(value);
            model.einzahlen(doubleValue);
            view.setMeldung("Einzahlung von " + value + " Erfolgreich", Color.GREEN, false);
        }catch (NumberFormatException doubleFormatException){
            view.setMeldung("Keine valide Nummer eingetippt", Color.RED, true);
        } catch (IllegalArgumentException e) {
            view.setMeldung(e.getMessage(), Color.RED, true);
        }

    }

    /**
     * Methode zum Abheben von Geld und setten der Meldung falls etwas schief geht
     * @param value der Betrag der abgehoben werden soll
     * @return true wenn das Abheben erfolgreich war
     */
    public boolean abheben(String value) {

        try {
            double doubleValue = Double.parseDouble(value);
            boolean success = model.abheben(doubleValue);
            if (!success){
                view.setMeldung("Abheben fehlgeschlagen eventuell ist nicht"+ System.lineSeparator() + "genügend Geld auf dem Konto", Color.RED,true);
                return success;
            }

            view.setMeldung("Aheben von " + value+ " Erfolgreich",Color.GREEN, false);
            return success;
        }catch (NumberFormatException doubleFormatException){
            view.setMeldung("Keine valide Nummer eingetippt", Color.RED, true);
            return false;
        } catch (IllegalArgumentException | GesperrtException e) {
            view.setMeldung(e.getMessage(), Color.RED, true);
            return false;
        }

    }


}






