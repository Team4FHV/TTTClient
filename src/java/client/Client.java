/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import DTO.objecte.DTOKarteBestellen;
import DTO.objecte.DTOKarteReservieren;
import DTO.objecte.DTOKategorieInformation;
import DTO.objecte.DTOKategorieKarte;
import DTO.objecte.DTOKategorienAuswaehlen;
import DTO.objecte.DTOKundeNeuSpeichern;
import DTO.objecte.DTOKundenDaten;
import DTO.objecte.DTOLoginDaten;
import DTO.objecte.DTOMessage;
import DTO.objecte.DTORollenList;
import DTO.objecte.DTOTopicData;
import DTO.objecte.DTOVeranstaltung;
import DTO.objecte.DTOVeranstaltungAnzeigen;
import DTO.objecte.DTOVeranstaltungInformation;
import Exceptions.BenutzerNichtInDBException;
import Exceptions.FalschesPasswordExeption;
import Exceptions.KarteNichtVerfuegbarException;
import Exceptions.SaveFailedException;
import GUIController.MainGuiCtrl;
import JMS.Subscriber;
import hello.ejb.HelloRemote;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

public class Client {

    HelloRemote rmi;
   // String host = "localhost";
    DTORollenList _userRollen;
    String username;
    List<DTOMessage> messages = new LinkedList();

    public Client(HelloRemote hallo) throws Exception {
        rmi = hallo;
    }

    private void startClient() {

        
    }

    public DTOKategorieKarte getAlleFreieKartenNachKategorie(DTOKategorienAuswaehlen kat) throws RemoteException {
        DTOKategorieKarte x = null;
        x = rmi.getAlleFreieKartenNachKategorie(kat);
        return x;
    }

    public ArrayList<DTOKategorieInformation> getKategorieInfoVonVeranstaltung(DTOVeranstaltungAnzeigen v) throws RemoteException {
        ArrayList<DTOKategorieInformation> x = null;
        x = rmi.getKategorieInfoVonVeranstaltung(v);
        return x;
    }

    public ArrayList<DTOKundenDaten> getKundenListNachNachname(String nachname) throws RemoteException, Exception {
        ArrayList<DTOKundenDaten> x = null;
        x = rmi.getKundenListNachNachname(nachname);
        return x;
    }

    public DTOKundenDaten getKundendatenNachID(int id) throws RemoteException, Exception {
        DTOKundenDaten x = null;
        x = rmi.getKundendatenNachID(id);
        return x;
    }

    public void reservierungSpeichern(List<DTOKarteReservieren> karten) throws RemoteException, SaveFailedException, Exception, KarteNichtVerfuegbarException {
        rmi.reservierungSpeichern(karten);
    }

    public ArrayList<DTOVeranstaltungInformation> sucheVeranstaltungenNachKrieterien(Date d, String ort, String kuenstler) throws RemoteException {
        ArrayList<DTOVeranstaltungInformation> x = null;
        x = rmi.sucheVeranstaltungenNachKrieterien(d, ort, kuenstler);
        return x;
    }

    public void verkaufSpeichern(List<DTOKarteBestellen> karten) throws RemoteException, SaveFailedException, Exception, KarteNichtVerfuegbarException {
        rmi.verkaufSpeichern(karten);
    }

    public DTOKategorieInformation getKategorieInfo(int id) throws RemoteException {
        DTOKategorieInformation x = null;
        x = rmi.getKategorieInfo(id);
        return x;

    }

    public DTOVeranstaltung getVeranstaltungById(int veranstaltungID) throws Exception {
        DTOVeranstaltung x = null;
        x = rmi.getVeranstaltungById(veranstaltungID);
        return x;
    }

    public void neuenKundeSpeichern(DTOKundeNeuSpeichern kunde) throws RemoteException, SaveFailedException {
        rmi.neuenKundenSpeichern(kunde);
    }

    public DTORollenList login(DTOLoginDaten l) throws RemoteException,
            BenutzerNichtInDBException, FalschesPasswordExeption {
        _userRollen = rmi.login(l);
        username = l.getUsername(); 
        return _userRollen;
    }

    public DTORollenList getUserRollen() {
        return _userRollen;
    }

    public void clearRoles() {
        _userRollen = null;
    }

    public DTOMessage getFirstMessage() {
        if (messages.size() > 0) {
            return messages.get(0);
        }
        return null;
    }

    public void addMessageToClient(DTOMessage m) {
        messages.add(m);
        if (MainGuiCtrl.getVeranstaltungSuchenView() != null) {
            MainGuiCtrl.getVeranstaltungSuchenView().checkMessages();
        }
    }

    public void removeFirstMessage() {
        if (messages.size() > 0) {
            messages.remove(0);
        }
    }

    public ArrayList<DTOTopicData> getTopics() throws RemoteException {
        return rmi.getTopics();
    }

    public void publishMessage(DTOMessage message) throws RemoteException {
        rmi.publishMessage(message);
    }

    public List<DTOMessage> loadUnpublishedMessages() {
        try {
            return rmi.loadUnpublishedMessages();

        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void startListenToMessages() throws RemoteException, NamingException{
         ArrayList<DTOTopicData> topics = rmi.getTopicsVonBenutzer(username);
         for (DTOTopicData topic :topics){
            System.out.println("topic  " + topic.getName());
            Subscriber s = new Subscriber(topic.getName(), this);
              s.subscribe();
         }
    }
}
