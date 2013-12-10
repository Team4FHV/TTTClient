/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import DTO.objecte.*;
import Exceptions.KarteNichtVerfuegbarException;
import Exceptions.SaveFailedException;
import client.Client;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Stefan Dietrich
 */
public class KartenInfoCtrl {

    private DTOVeranstaltung _veranstaltung;
    private DTOKategorieInformation _kategorie;
    private DTOKategorieKarte _Kategoriekarten;
    private DTOKundenDaten _kunde;
    private Client _client;

    public KartenInfoCtrl(int veranstaltungID, int kategorieID, Client client) {
        _client = client;
        try {
            _veranstaltung = client.getVeranstaltungById(veranstaltungID);
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        try {
            _kategorie = client.getKategorieInfo(kategorieID);
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        try {
            _Kategoriekarten = _client.getAlleFreieKartenNachKategorie(new DTOKategorienAuswaehlen(_kategorie.getId()));
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public TableModel getKartenInfo() {
        return null;
    }

    public DTOVeranstaltung getVeranstaltung() {
        return _veranstaltung;
    }

    public DTOKategorieInformation getKategorie() {
        return _kategorie;
    }

    public boolean checkKundennummer(String text) {
        System.out.println("Ausgabe der Karte: " + "89");
        if (text.equals("")) {
            _kunde = null;
            return false;
        } else {
            int id = 0;
            boolean isNumber = true;
            try {
                id = Integer.parseInt(text);
            } catch (Exception e) {
                _kunde = null;
                isNumber = false;
            }
            if (isNumber) {
                try {
                    _kunde = _client.getKundendatenNachID(id);
                } catch (Exception ex) {
                    _kunde = null;
                }
            } else {
                _kunde = null;
            }
            if (_kunde != null) {
                return true;
            } else {
                return false;
            }
        }
    }

    public DTOKundenDaten getKunde() {
        return _kunde;
    }

    public TableModel getKartenInfoModel() {
        Object[][] ob = new Object[_Kategoriekarten.getDTOKarten().size()][5];
        for (int i = 0; i < _Kategoriekarten.getDTOKarten().size(); i++) {
            DTOKarte k = _Kategoriekarten.getDTOKarten().get(i);
            ob[i][0] = false;
            ob[i][1] = k.getID();
            ob[i][2] = k.getReihe();
            ob[i][3] = k.getPlatz();
            ob[i][4] = false;
        }
        return (new DefaultTableModel(
                ob,
                new String[]{
            "Auswählen", "KartenID", "Reihe", "Sitzplatz", "Ermäßigt"
        }) {
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean[]{
                true, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
    }

    public void kartenBestellen(List<Object[]> bestellteKarten) throws RemoteException, SaveFailedException, KarteNichtVerfuegbarException, Exception {
        List<DTOKarteBestellen> karten = new LinkedList();
        int kundenID = -1;
        if (_kunde != null) {
            kundenID = _kunde.getId();
        }
        for (Object[] o : bestellteKarten) {

            karten.add(new DTOKarteBestellen(new Integer((String) o[1]), kundenID, new Boolean( (String) o[4])));
        }
        _client.verkaufSpeichern(karten);

        updateController();
    }

    public void kartenReservieren(List<Object[]> reservierteKarten) throws RemoteException, SaveFailedException, KarteNichtVerfuegbarException, Exception {
        List<DTOKarteReservieren> karten = new LinkedList();
        int kundenID = -1;
        if (_kunde != null) {
            kundenID = _kunde.getId();
        }
        for (Object[] o : reservierteKarten) {

            karten.add(new DTOKarteReservieren(new Integer((String) o[1]), kundenID, new Boolean((String) o[4])));
        }
        _client.reservierungSpeichern(karten);
        updateController();
    }

    public void deleteKundenInfo() {
        _kunde = null;
    }

    private void updateController() {
        try {
            _kategorie = _client.getKategorieInfo(_kategorie.getId());
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        try {
            _Kategoriekarten = _client.getAlleFreieKartenNachKategorie(new DTOKategorienAuswaehlen(_kategorie.getId()));
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cancelClicked() {
        deleteKundenInfo();
        MainGuiCtrl.KarteCancel(_veranstaltung.getID());
    }

    void setVeranstaltung(int veranstaltungID) {
        try {
            _veranstaltung = _client.getVeranstaltungById(veranstaltungID);
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void setKategorieID(int kategorieID) {
        try {
            _kategorie = _client.getKategorieInfo(kategorieID);
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        try {
            _Kategoriekarten = _client.getAlleFreieKartenNachKategorie(new DTOKategorienAuswaehlen(_kategorie.getId()));
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadKarten() throws RemoteException {
        _Kategoriekarten = _client.getAlleFreieKartenNachKategorie(new DTOKategorienAuswaehlen(_kategorie.getId()));
    }
}
