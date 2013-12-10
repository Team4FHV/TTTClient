/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import DTO.objecte.DTOKategorieInformation;
import DTO.objecte.DTOVeranstaltung;
import DTO.objecte.DTOVeranstaltungAnzeigen;
import client.Client;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Stefan Dietrich
 */
public class VeranstaltungKategorieCtrl {

    private DTOVeranstaltung _veranstaltung;
    private Client _client;
    private ArrayList<DTOKategorieInformation> _kategorien;

    public VeranstaltungKategorieCtrl(int veranstaltungID, Client client) {
        _client = client;
        try {
            _veranstaltung = _client.getVeranstaltungById(veranstaltungID);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        try {
            _kategorien = _client.getKategorieInfoVonVeranstaltung(new DTOVeranstaltungAnzeigen(_veranstaltung.getID()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public DTOVeranstaltung getVeranstaltung() {
        return _veranstaltung;
    }

    public TableModel getKategorieInfoModel() {
        Object[][] ob = new Object[_kategorien.size()][4];
        for (int i = 0; i < _kategorien.size(); i++) {
            ob[i][0] = _kategorien.get(i).getId();
            ob[i][1] = _kategorien.get(i).getName();
            ob[i][2] = _kategorien.get(i).getPreis() + "€";
            ob[i][3] = _kategorien.get(i).getFreiepl�tze();
        }
        return (new DefaultTableModel(
                ob,
                new String[]{
            "KategorieID", "Kategoriename", "Kategoriepreis", "Freie Plätze"
        }) {
            Class[] types = new Class[]{
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean[]{
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
    }

    public void selectKategorie(int id) {
        DTOKategorieInformation selectedKategorie = null;
        try {
            selectedKategorie = _client.getKategorieInfo(id);
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        if (selectedKategorie != null) {
            MainGuiCtrl.KategorieAusgewählt(_veranstaltung.getID(), selectedKategorie.getId());
        }
    }

    public void cancelButton() {
        MainGuiCtrl.KategorieCancel();
    }

    void setVeranstaltungsID(int id) {
        try {
            _veranstaltung = _client.getVeranstaltungById(id);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        try {
            _kategorien = _client.getKategorieInfoVonVeranstaltung(new DTOVeranstaltungAnzeigen(_veranstaltung.getID()));
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
