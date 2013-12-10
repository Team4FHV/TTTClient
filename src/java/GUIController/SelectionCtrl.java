/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import DTO.objecte.DTORollenList;
import client.Client;
import java.util.List;

/**
 *
 * @author Monika
 */
public class SelectionCtrl {

    private DTORollenList _rollen;
    private Client _client;

    SelectionCtrl(Client client) {
        _client = client;
    }

    void setRollen(DTORollenList rolList) {
        _rollen = rolList;
    }

    public void veranstaltungSuchen() {
        MainGuiCtrl.VeranstaltungSuchen();
    }

    public void KarteReservieren() {
        MainGuiCtrl.VeranstaltungSuchen();
    }

    public void KundenVerwalten() {
        MainGuiCtrl.KundenVerwalten();
    }

    public void KarteKaufen() {
        MainGuiCtrl.VeranstaltungSuchen();
    }

    public List<String> loadRoles() {
        return _rollen.getRollen();
    }

    public void closeWindow() {
        _client.clearRoles();
        MainGuiCtrl.SelectionClose();
    }

    public void MessageSchreiben() {
        MainGuiCtrl.MessageSchreiben();
    }

    public void MessageZuordnen() {
       MainGuiCtrl.MessageBearbeiten();
    }
}
