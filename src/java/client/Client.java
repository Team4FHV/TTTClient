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
import DTO.objecte.DTORollenList;
import DTO.objecte.DTOVeranstaltung;
import DTO.objecte.DTOVeranstaltungAnzeigen;
import DTO.objecte.DTOVeranstaltungInformation;
import Exceptions.BenutzerNichtInDBException;
import Exceptions.FalschesPasswordExeption;
import Exceptions.KarteNichtVerfuegbarException;
import Exceptions.SaveFailedException;
import hello.ejb.HelloRemote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.naming.InitialContext;

public class Client {

    
    HelloRemote rmi;
    String host;
    DTORollenList _userRollen;
    

    public Client(HelloRemote hallo) throws Exception{
//         Properties props = new Properties();
//        props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
//        props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");//ur server ip  
//        props.setProperty("org.omg.CORBA.ORBInitialPort", "3700"); //default is 3700
//        InitialContext jndiContext = new InitialContext(props);
//        rmi = (HelloRemote) jndiContext.lookup("C:\\Users\\Anastasia\\Desktop\\mybeans_4\\TTTApp\\TTTApp-ejb\\src\\java\\hello\\ejb\\Hello.java");
        
      
        
        rmi = hallo;
    }

    private void startClient() {

//        System.out.println("Geben Sie Bitte HOST ein:");
//        
//        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
//
//        try {
//            host = console.readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//           
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
        return _userRollen;
    }

    public DTORollenList getUserRollen() {
        return _userRollen;
    }

    public void clearRoles() {
        _userRollen = null;
    }

   
}
