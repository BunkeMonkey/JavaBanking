package bankprojekt.oberflaeche;

import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import org.checkerframework.checker.units.qual.K;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;

public class OberflaechenFXMLController extends Application {
    @FXML
    CheckBox gesperrtBox;
    @FXML
    Text gesperrtText;
    @FXML
    Text kontostandText;
    @FXML
    Text kontonummerText;
    @FXML
    Text adresseText;
    @FXML
    TextField adressFeld;
    @FXML
    Text meldungText;
    @FXML
    Button einzahlen;
    @FXML
    Button abheben;
    @FXML
    Text kontonummer;
    @FXML
    Text kontostand;

   // KontoOberflaeche view;

    Konto model;

    @FXML
    TextField abhebenFeld;



    @Override
    public void start(Stage primaryStage) throws Exception {
        Kunde daniel = new Kunde("Daniel","Marks","Marzahn", LocalDate.now());
        model = new Girokonto(daniel,13376969420L,1000);

        FXMLLoader loader =
                new FXMLLoader(getClass().
                        getResource("kontooberflaeche.fxml"));
        loader.setController(this);
        Parent view = null;
        try {
            view = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    @FXML private void initialize(){
        //Kunde daniel = new Kunde("Daniel","Marks","Marzahn", LocalDate.now());
        //model = new Girokonto(daniel,13376969420L,1000);
        gesperrtBox.selectedProperty().bindBidirectional(model.gesperrtProperty());
        adressFeld.textProperty().bindBidirectional(model.getInhaber().adresseProperty());
        kontostand.fillProperty().bind(model.negativeBalanceProperty().map(negBalance -> negBalance ? Color.RED : Color.GREEN));




    }


    /**
     * Methode zum Einzahlen von Geld und setten der Meldung falls etwas schief geht
     * @param value der Betrag der eingezahlt werden soll
     */
    @FXML
    public void einzahlen(ActionEvent event) {
        String value = abhebenFeld.getText();
        try {
            double doubleValue = Double.parseDouble(value);
            model.einzahlen(doubleValue);
            setMeldung("Einzahlung von " + value + " Erfolgreich",  false);
        }catch (NumberFormatException doubleFormatException){
            setMeldung("Keine valide Nummer eingetippt",  true);
        } catch (IllegalArgumentException e) {
            setMeldung(e.getMessage(), true);
        }

    }

    /**
     * Methode zum Abheben von Geld und setten der Meldung falls etwas schief geht
     * @param value der Betrag der abgehoben werden soll
     * @return true wenn das Abheben erfolgreich war
     */
    public boolean abheben(ActionEvent event) {
        String value = abhebenFeld.getText();
        try {
            double doubleValue = Double.parseDouble(value);
            boolean success = model.abheben(doubleValue);
            if (!success){
                setMeldung("Abheben fehlgeschlagen eventuell ist nicht"+ System.lineSeparator() + "genügend Geld auf dem Konto",true);
                return success;
            }

            setMeldung("Aheben von " + value+ " Erfolgreich", false);
            return success;
        }catch (NumberFormatException doubleFormatException){
            setMeldung("Keine valide Nummer eingetippt",  true);
            return false;
        } catch (IllegalArgumentException | GesperrtException e) {
            setMeldung(e.getMessage(),true);
            return false;
        }

    }
    public void setMeldung(String noticeText, boolean error){
        meldungText.setText(noticeText);
        meldungText.setFill(error ? Color.RED : Color.GREEN);
        meldungText.setUnderline(error);
    }
    public Konto getModel(){
        return this.model;
    }


}
