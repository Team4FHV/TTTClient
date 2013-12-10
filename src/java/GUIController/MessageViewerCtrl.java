/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import DTO.objecte.DTOMessage;
import client.Client;

/**
 *
 * @author Stefan Dietrich
 */
public class MessageViewerCtrl {

    Client _client;

    public MessageViewerCtrl(Client client) {
        _client = client;
    }

    public void messageRecieved() {
        _client.removeFirstMessage();
        MainGuiCtrl.enableVeranstaltungSuchen();
    }

    public DTOMessage getMessage() {
        DTOMessage m = _client.getFirstMessage();
        if (m != null) {
            return m;
        }
        return new DTOMessage("", "", null, "");
    }
}
