package it.unina.aci.controllo;

import java.util.*;
import it.unina.aci.modello.*;
import it.unina.aci.persistenza.*;
import it.unina.utilita.Console;

public class Principale{
    
    private Utente utente;
    private ControlloRicercaProprietario controlloRicercaProprietario;
    private ControlloInserisciProprietario controlloInserisciProprietario;
    private ControlloAggiornaProprietario controlloAggiornaProprietario;
    private ControlloCancellaProprietario controlloCancellaProprietario;
    private ControlloInserisciAutomobile controlloInserisciAutomobile;
    
    public Principale(){
        controlloRicercaProprietario = new ControlloRicercaProprietario();
        controlloInserisciProprietario = new ControlloInserisciProprietario();
        controlloAggiornaProprietario = new ControlloAggiornaProprietario();
        controlloCancellaProprietario = new ControlloCancellaProprietario();
        controlloInserisciAutomobile = new ControlloInserisciAutomobile();
    }
    
    public Utente getUtente() {
        return utente;
    }
    
    public ControlloRicercaProprietario getControlloRicercaProprietario() {
        return this.controlloRicercaProprietario;
    }
    
    public void schermoAutenticazione() {
        while (utente == null || !utente.getAutenticato()) {
            System.out.println();
            System.out.println("-----------Inserisci nome utente e password------");
            System.out.println("-----------digita \"esci\" per uscire  ------------");
            System.out.print("\n  Nome utente --> ");
            String nomeUtente=Console.leggiStringa();
            if (nomeUtente.equals("esci")) {
                schermoFinale();
                return;
            }
            System.out.print("  Password    --> ");
            String password = Console.leggiStringa();
            boolean errore=false;
            try {
                utente = DAOUtente.doSelectNomeUtente(nomeUtente);
                if (utente == null) {
                    System.out.println("\n****** ERRORE: nome utente inesistente ******");
                } else {
                    utente.verifica(password);
                    if (!utente.getAutenticato()) {
                        System.out.println("\n****** ERRORE: password scorretta ******");
                    }
                }
            } catch(DAOException pe) {
                System.err.println(pe);
                utente = null;
            }
        }
        schermoIniziale();
    }
    
    public void schermoIniziale() {
        if (utente.getRuolo().equals("amministratore")) {
            schermoInizialeAmministratore();
        } else if (utente.getRuolo().equals("utente")) {
            schermoInizialeUtente();
        }
    }
    
    private void schermoInizialeUtente() {
        boolean continua = true;
        while (continua) {
            System.out.println();
            System.out.println("Utente: " + utente.getNome() + "\n");
            System.out.println("-----------Menu delle scelte------");
            System.out.println();
            System.out.println("  1. Ricerca un proprietario");
            System.out.println("  2. Stampa l'elenco dei proprietari e delle relative automobili");
            System.out.println("  0. Esci");
            System.out.print("\n  Scegli --> ");
            int scelta = Console.leggiIntero();
            while (scelta < 0 || scelta > 2){
                System.out.print("  Scelta scorretta. Ripeti --> ");
                scelta = Console.leggiIntero();
            }
            if (scelta == 0) {
                schermoFinale();
                continua = false;
            } else if (scelta == 1) {
                controlloRicercaProprietario.schermoIniziale();
            } else if (scelta == 2) {
                schermoElencaProprietariEAuto();
            }
        }
    }
    
    private void schermoInizialeAmministratore() {
        boolean continua = true;
        while (continua) {
            System.out.println();
            System.out.println("Utente: " + utente.getNome() + "\n");
            System.out.println("-----------Menu delle scelte------");
            System.out.println();
            System.out.println("  1. Inserisci un proprietario.");
            System.out.println("  2. Aggiorna un proprietario.");
            System.out.println("  3. Cancella un proprietario.");
            System.out.println("  4. Ricerca un proprietario.");
            System.out.println("  5. Inserisci un'automobile.");
            System.out.println("  6. Stampa l'elenco dei proprietari e delle relative automobili");
            System.out.println("");
            System.out.println("  0. Esci");
            System.out.print("\n  Scegli --> ");
            int scelta = Console.leggiIntero();
            while (scelta < 0 || scelta > 6){
                System.out.print("  Scelta scorretta. Ripeti --> ");
                scelta = Console.leggiIntero();
            }
            if (scelta == 0) {
                schermoFinale();
                continua = false;
            } else if (scelta == 1) {
                controlloInserisciProprietario.schermoIniziale();
            } else if (scelta == 2) {
                controlloAggiornaProprietario.schermoIniziale();
            } else if (scelta == 3) {
                controlloCancellaProprietario.schermoIniziale();
            } else if (scelta == 4) {
                controlloRicercaProprietario.schermoIniziale();
            } else if (scelta == 5) {
                controlloInserisciAutomobile.schermoIniziale();
            } else if (scelta == 6) {
                schermoElencaProprietariEAuto();
            }
        }
    }
    
    private void schermoFinale() {
        System.out.println("\n---------- Arrivederci -----------");
    }
    
    public static void stampaMessaggioErrore(String messaggio) {
        System.out.println("\n**************************************");
        System.out.println("   ERRORE: " + messaggio);
        System.out.println("**************************************");
    }
    
    private void schermoElencaProprietariEAuto(){
        System.out.println("\n--------------------------------------");
        System.out.println("Elenco dei proprietari e delle auto");
        System.out.println("\n--------------------------------------\n");
        Collection listaProprietari = null;
        try {
            listaProprietari = DAOAutomobile.doSelectElencoProprietariEAutomobili();
            Iterator iteratorProprietari = listaProprietari.iterator();
            while (iteratorProprietari.hasNext()){
                Proprietario proprietario = (Proprietario)iteratorProprietari.next();
                System.out.println("Codice fiscale  : "+proprietario.getCodiceFiscale());
                System.out.println("Nome            : "+proprietario.getNome());
                System.out.println("Citta           : "+proprietario.getCittaDiResidenza());
                System.out.println("Anno patente    : "+proprietario.getAnnoPatente());
                Collection listaAutomobili = proprietario.getAutomobili();
                Iterator iteratorAutomobili = listaAutomobili.iterator();
                System.out.println("------  Elenco delle Automobili Possedute     ----");
                while (iteratorAutomobili.hasNext()){
                    Automobile automobile = (Automobile)iteratorAutomobili.next();
                    System.out.println("     Targa      : " + automobile.getTarga());
                    System.out.println("     Modello    : " + automobile.getModello());
                    System.out.println("     Cilindrata : " + automobile.getCilindrata());
                }
                System.out.println("--------------------------------------------------");
            }
        } catch(DAOException pe) {
            System.err.println(pe);
        }
    }
    
    public static void main(String[] args){
        Principale controlloPrincipale = new Principale();
        controlloPrincipale.schermoAutenticazione();
    }
    
}