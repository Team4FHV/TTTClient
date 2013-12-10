/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import DTO.objecte.DTOMessage;
import client.Client;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Stefan Dietrich
 */
public class MessagesBearbeitenCtrl {

    private Client _client;

    public MessagesBearbeitenCtrl(Client client) {
        _client = client;
    }

    public TableModel getMessageModel() {
        List<DTOMessage> messages = _client.loadUnpublishedMessages();
       Object[][] ob = new Object[messages.size()][2];
        for (int i = 0; i < messages.size(); i++) {
            ob[i][0] = messages.get(i).getTitle();
            ob[i][1] = messages.get(i).getText();
            
        }
        return (new DefaultTableModel(
                ob,
                new String[]{
            "Titel", "Nachricht"
        }) {
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[]{
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
    }

    public void cancelButton() {
        MainGuiCtrl.MessageBearbeitenCancel();
    }

    public void selectedMessage(DTOMessage dtoMessage) {
       MainGuiCtrl.MessageZuordnen(dtoMessage);
    }
}
