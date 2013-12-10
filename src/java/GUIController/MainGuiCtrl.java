/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import DTO.objecte.DTOMessage;
import DTO.objecte.DTORollenList;

import GUI.*;


import client.Client;
import hello.ejb.HelloRemote;
import java.rmi.RemoteException;
import javax.ejb.EJB;
import javax.naming.NamingException;

/**
 *
 * @author Stefan Dietrich
 */
public class MainGuiCtrl {
     @EJB
    private static HelloRemote hallo;
    private static VeranstaltungSuchen _veranstaltungSuchen;
    private static VeranstaltungKategorie _veranstaltungKategorie;
    private static KartenInfo _kartenInfo;
    private static KundeAnlegen _kundeAnlegen;
    private static Login _login;
    private static Selection _selection;
    private static MessageViewer _messageViewer;
    private static MessageSchreiben _messageSchreiben;
    private static MessagesBearbeiten _messagesBearbeiten;
    private static MessageZuordnen _messageZuordnen;
    private static KartenInfoCtrl _kartenInfoCtrl;
    private static VeranstaltungKategorieCtrl _veranstaltungKategorieCtrl;
    private static VeranstaltungsSuchenCtrl _veranstaltungSuchenCtrl;
    private static KundeAnlegenCtrl _kundeAnlegenCtrl;
    private static LoginCtrl _loginCtrl;
    private static SelectionCtrl _selectionCtrl;
    private static MessageViewerCtrl _messsageViewerCtrl;
    private static MessageSchreibenCtrl _messageSchreibenCtrl;
    private static MessagesBearbeitenCtrl _messagesBearbeitenCtrl;
    private static MessageZuordnenCtrl _messageZuordnenCtrl;
    private static Client _client;

    public static void VeranstaltungAusgewaehlt(int veranstaltungID) {
        _veranstaltungSuchen.setVisible(false);
        _veranstaltungKategorie = new VeranstaltungKategorie(getVeranstaltungKategorieCtrl(veranstaltungID));
        _veranstaltungSuchen.Quit();
        _veranstaltungSuchen = null;
    }

    public static void KategorieAusgewaehlt(int veranstaltungID, int kategorieID) {
        _veranstaltungKategorie.setVisible(false);
        _kartenInfo = new KartenInfo(getKartenInfoCtrl(veranstaltungID, kategorieID));
        _veranstaltungKategorie.Quit();
        _veranstaltungKategorie = null;
    }

    public static void KategorieCancel() {
        _veranstaltungKategorie.setVisible(false);
        _veranstaltungSuchen = new VeranstaltungSuchen(getVeranstaltungSuchenCtrl());
        _veranstaltungKategorie.Quit();
        _veranstaltungKategorie = null;
    }

    public static void KarteCancel(int veranstaltungID) {
        _kartenInfo.setVisible(false);
        _veranstaltungKategorie = new VeranstaltungKategorie(getVeranstaltungKategorieCtrl(veranstaltungID));
        _kartenInfo.Quit();
        _kartenInfo = null;
    }

    public static void VeranstaltungCancel() {
        _veranstaltungSuchen.setVisible(false);
        _selectionCtrl = getSelectionCtrl();
        _selectionCtrl.setRollen(_client.getUserRollen());
        _selection = new Selection(_selectionCtrl);
        _veranstaltungSuchen.Quit();
        _veranstaltungSuchen = null;

    }

    static void Login(DTORollenList rolList) {
        _login.setVisible(false);
        _selectionCtrl = getSelectionCtrl();
        _selectionCtrl.setRollen(rolList);
        _selection = new Selection(_selectionCtrl);
        _login.Quit();
    }

    static void VeranstaltungSuchen() {
        _selection.setVisible(false);
        _veranstaltungSuchen = new VeranstaltungSuchen(getVeranstaltungSuchenCtrl());
        _selection.Quit();
        _selection = null;
    }

    static void KundenVerwalten() {
        _selection.setVisible(false);
        _kundeAnlegen = new KundeAnlegen(getKundeAnlegenCtrl());
        _selection.Quit();
        _selection = null;
    }

    static void KundeAnlegenCancel() {
        _kundeAnlegen.setVisible(false);
        _selectionCtrl = getSelectionCtrl();
        _selectionCtrl.setRollen(_client.getUserRollen());
        _selection = new Selection(_selectionCtrl);
        _kundeAnlegen.Quit();
        _kundeAnlegen = null;
    }

    public static void MessageSchreibenCancel() {
        _messageSchreiben.setVisible(false);
        _selectionCtrl = getSelectionCtrl();
        _selectionCtrl.setRollen(_client.getUserRollen());
        _selection = new Selection(_selectionCtrl);
        _messageSchreiben.Quit();
        _messageSchreiben = null;
    }

    public static void MessageSchreiben() {
        _selection.setVisible(false);
        _messageSchreiben = new MessageSchreiben(getMessageSchreibenCtrl());
        _selection.Quit();
        _selection = null;
    }

    static void SelectionClose() {
        _selection.setVisible(false);
        _login = new Login(getLoginCtrl());
        _selection.Quit();
        _selection = null;
    }

    public static void showMessages() {
        _veranstaltungSuchen.setEnabled(false);
        _messageViewer = new MessageViewer(getMessageViewerCtrl());
    }

    public static void enableVeranstaltungSuchen() {
        _messageViewer.setVisible(false);
        if (_veranstaltungSuchen != null) {
            _veranstaltungSuchen.setEnabled(true);
            _veranstaltungSuchen.checkMessages();
            _veranstaltungSuchen.toFront();
        }
        _messageViewer.Quit();
        _messageViewer = null;
    }

    static void MessageZuordnenCancel() {
        _messageZuordnen.setVisible(false);
        if (_messagesBearbeiten != null) {
            _messagesBearbeiten.setEnabled(true);
            _messagesBearbeiten.reloadMessages();
            _messagesBearbeiten.toFront();
        }
        _messageZuordnen.Quit();
        _messageZuordnen = null;
    }

    static void MessageBearbeitenCancel() {
        _messagesBearbeiten.setVisible(false);
        _selectionCtrl = getSelectionCtrl();
        _selectionCtrl.setRollen(_client.getUserRollen());
        _selection = new Selection(_selectionCtrl);
        _messagesBearbeiten.Quit();
        _messagesBearbeiten = null;
    }

    static void MessageZuordnen(DTOMessage dtoMessage) {
        _messagesBearbeiten.setEnabled(false);
        _messageZuordnenCtrl = getMessageZuordnenCtrl(dtoMessage);
        _messageZuordnen = new MessageZuordnen(_messageZuordnenCtrl);
    }

    static void MessageBearbeiten() {
        _selection.setVisible(false);
        _messagesBearbeiten = new MessagesBearbeiten(getMessagesBearbeitenCtrl());
        _selection.Quit();
        _selection = null;
    }

    public static void main(String[] args) throws Exception, NamingException {
        _client = new Client(hallo);
        _login = new Login(getLoginCtrl());
       
    }

    private static VeranstaltungsSuchenCtrl getVeranstaltungSuchenCtrl() {
        if (_veranstaltungSuchenCtrl == null) {
            _veranstaltungSuchenCtrl = new VeranstaltungsSuchenCtrl(_client);
        }
        return _veranstaltungSuchenCtrl;
    }

    private static VeranstaltungKategorieCtrl getVeranstaltungKategorieCtrl(int id) {
        if (_veranstaltungKategorieCtrl == null) {
            _veranstaltungKategorieCtrl = new VeranstaltungKategorieCtrl(id, _client);
        } else {
            _veranstaltungKategorieCtrl.setVeranstaltungsID(id);
        }
        return _veranstaltungKategorieCtrl;
    }

    private static KartenInfoCtrl getKartenInfoCtrl(int veranstaltungID, int kategorieID) {
        if (_kartenInfoCtrl == null) {
            _kartenInfoCtrl = new KartenInfoCtrl(veranstaltungID, kategorieID, _client);
        } else {
            _kartenInfoCtrl.setVeranstaltung(veranstaltungID);
            _kartenInfoCtrl.setKategorieID(kategorieID);
        }
        return _kartenInfoCtrl;
    }

    private static KundeAnlegenCtrl getKundeAnlegenCtrl() {
        if (_kundeAnlegenCtrl == null) {
            _kundeAnlegenCtrl = new KundeAnlegenCtrl(_client);
        }
        return _kundeAnlegenCtrl;
    }

    private static SelectionCtrl getSelectionCtrl() {
        if (_selectionCtrl == null) {
            _selectionCtrl = new SelectionCtrl(_client);
        }
        return _selectionCtrl;
    }

    private static LoginCtrl getLoginCtrl() {
        if (_loginCtrl == null) {
            _loginCtrl = new LoginCtrl(_client);
        }
        return _loginCtrl;
    }

    private static MessageViewerCtrl getMessageViewerCtrl() {
        if (_messsageViewerCtrl == null) {
            _messsageViewerCtrl = new MessageViewerCtrl(_client);
        }
        return _messsageViewerCtrl;
    }

    private static MessageSchreibenCtrl getMessageSchreibenCtrl() {
        if (_messageSchreibenCtrl == null) {
            _messageSchreibenCtrl = new MessageSchreibenCtrl(_client);
        }
        return _messageSchreibenCtrl;
    }

    private static MessageZuordnenCtrl getMessageZuordnenCtrl(DTOMessage m) {
        if (_messageZuordnenCtrl == null) {
            _messageZuordnenCtrl = new MessageZuordnenCtrl(_client, m);
        } else {
            _messageZuordnenCtrl.setMessage(m);
        }
        return _messageZuordnenCtrl;
    }

    private static MessagesBearbeitenCtrl getMessagesBearbeitenCtrl() {
        if (_messagesBearbeitenCtrl == null) {
            _messagesBearbeitenCtrl = new MessagesBearbeitenCtrl(_client);
        }
        return _messagesBearbeitenCtrl;
    }

    public static VeranstaltungSuchen getVeranstaltungSuchenView() {
        return _veranstaltungSuchen;
    }
}
