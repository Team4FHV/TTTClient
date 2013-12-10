/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import DTO.objecte.DTOKundeNeuSpeichern;
import Exceptions.SaveFailedException;
import client.Client;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Stefan Dietrich
 */
public class KundeAnlegenCtrl {

    private Client _client;
    
    public KundeAnlegenCtrl(Client client)
    {
        _client = client;
    }

    public boolean neuenKundeAnlegen(String vorname, String nachname, String geburtsdatum, String anrede, String firmenname, String land, String postleitzahl, String ort, String strasse, String hausnummer, String telefonnummer, String email) throws SaveFailedException {
        SimpleDateFormat sdfToDate = new SimpleDateFormat("dd.MM.yyyy");
        Date date = null;
        try {
            date = sdfToDate.parse(geburtsdatum);
        } catch (ParseException ex) {
            System.out.println("Fehler beim Parsen des Geburtsdatums");
        }
        DTOKundeNeuSpeichern kunde = new DTOKundeNeuSpeichern(vorname, nachname, date, anrede, firmenname, land, postleitzahl, ort, strasse, hausnummer, telefonnummer, email);
        try {
            _client.neuenKundeSpeichern(kunde);
            return true;
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void cancelClicked() {
        MainGuiCtrl.KundeAnlegenCancel();
    }
}
