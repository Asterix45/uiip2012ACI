package it.unina.aci.controllo;

import java.util.*;
import it.unina.aci.modello.*;
import it.unina.aci.persistenza.*;
import it.unina.utilita.Console;

public class ControlloRicercaProprietario {
    
    public void schermoIniziale(){
        int scelta = schermoScelta();
        Proprietario proprietario = null;
        if (scelta == 1) {
            proprietario = schermoRicercaPerCodiceFiscale();
        } else if (scelta == 2) {
            proprietario = schermoRicercaPerNome();
        }
        schermoStampaDatiProprietario(proprietario);
    }
    
    public int schermoScelta() {
        int scelta = -1;
        System.out.println("\n--------------------------------------");
        System.out.println("  Ricerca proprietario");
        System.out.println("--------------------------------------\n");
        System.out.println("  1. Utilizzando il codice fiscale");
        System.out.println("  2. Utilizzando il nome");
        System.out.println("  0. Esci");
        System.out.print("\n  Scegli --> ");
        scelta = Console.leggiIntero();
        while (scelta < 0 || scelta > 2){
            System.out.print("Scelta scorretta. Ripeti --> ");
            scelta = Console.leggiIntero();
        }
        return scelta;
    }
    
    public Proprietario schermoRicercaPerCodiceFiscale(){
        System.out.print("\nInserisci il Codice Fiscale --> ");
        String codiceFiscale=Console.leggiStringa();
        Proprietario proprietario = null;
        try {
            proprietario = DAOProprietario.doSelectCodiceFiscale(codiceFiscale);
            DAOProprietario.caricaAutomobili(proprietario);
        } catch(DAOException pe){
            System.err.println(pe);
        }
        return proprietario;
    }
    
    public Proprietario schermoRicercaPerNome(){
        Proprietario proprietario = null;
        System.out.print("\nInserisci il Nome --> ");
        String nome = Console.leggiStringa();
        List listaProprietari = null;
        try {
            listaProprietari = DAOProprietario.doSelectNome(nome);
            if (listaProprietari.isEmpty()) {
                return null;
            } else {
                Iterator it = listaProprietari.iterator();
                int i = 0;
                while (it.hasNext()){
                    proprietario = (Proprietario)it.next();
                    System.out.println("--------- Proprietario n. " + i++);
                    System.out.println("Codice fiscale  : " + proprietario.getCodiceFiscale());
                    System.out.println("Nome            : " + proprietario.getNome());
                    System.out.println("Citta           : " + proprietario.getCittaDiResidenza());
                    System.out.println("Anno patente    : " + proprietario.getAnnoPatente());
                }
                System.out.println("\nScegli il numero corrispondente al record relativo al proprietario:");
                System.out.print("  Scelta --> ");
                int scelta=Console.leggiIntero();
                while(scelta < 0 || scelta >= listaProprietari.size()){
                    System.out.print("Scelta scorretta. Ripeti --> ");
                    scelta = Console.leggiIntero();
                }
                proprietario = (Proprietario)listaProprietari.get(scelta);
                DAOProprietario.caricaAutomobili(proprietario);
            }
        } catch (DAOException pe) {
            System.err.println(pe);
            return null;
        }
        return proprietario;
    }
    
    public void schermoStampaDatiProprietario(Proprietario proprietario) {
        if (proprietario == null) {
            System.out.println("\n****** ERRORE: non ci sono proprietari con la chiave specificata ******");
        } else {
            System.out.println("\n------------- Dati del Proprietario --------------");
            System.out.println("Codice fiscale  : " + proprietario.getCodiceFiscale());
            System.out.println("Nome            : " + proprietario.getNome());
            System.out.println("Citta           : " + proprietario.getCittaDiResidenza());
            System.out.println("Anno patente    : " + proprietario.getAnnoPatente());
            Collection listaAutomobili = proprietario.getAutomobili();
            Iterator iterator = listaAutomobili.iterator();
            System.out.println("------  Elenco delle Automobili Possedute     ----");
            Automobile automobile;
            while (iterator.hasNext()){
                automobile = (Automobile)iterator.next();
                System.out.println("     Targa      : "+automobile.getTarga());
                System.out.println("     Modello    : "+automobile.getModello());
                System.out.println("     Cilindrata : "+automobile.getCilindrata());
            }
            System.out.println("--------------------------------------------------");
        }
    }
    
}
