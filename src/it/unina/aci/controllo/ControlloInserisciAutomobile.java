package it.unina.aci.controllo;

import it.unina.aci.modello.*;
import it.unina.aci.persistenza.*;
import it.unina.utilita.Console;

class ControlloInserisciAutomobile {
    
    public void schermoIniziale() {
        ControlloRicercaProprietario controlloRicercaProprietario = new ControlloRicercaProprietario();
        int scelta = controlloRicercaProprietario.schermoScelta();
        Proprietario proprietario = null;
        if (scelta == 1) {
            proprietario = controlloRicercaProprietario.schermoRicercaPerCodiceFiscale();
        } else if (scelta == 2) {
            proprietario = controlloRicercaProprietario.schermoRicercaPerNome();
        }
        controlloRicercaProprietario.schermoStampaDatiProprietario(proprietario);
        if (proprietario != null) {
            schermoInserimentoAuto(proprietario);
        }
    }
    
    private void schermoInserimentoAuto(Proprietario proprietario){
        System.out.println("\n--------------------------------------");
        System.out.println("  Inserimento Automobile");
        System.out.println("--------------------------------------\n");
        Automobile automobile = new Automobile();
        System.out.print("Inserisci la targa       -->");
        String targa = Console.leggiStringa();
        try {
            automobile = DAOAutomobile.doSelectTarga(targa);
            if (automobile == null) {
                automobile = new Automobile();
                automobile.setTarga(targa);
                System.out.print("Inserisci il modello     -->");
                automobile.setModello(Console.leggiStringa());
                System.out.print("Inserisci la cilindrata  -->");
                automobile.setCilindrata(Console.leggiIntero());
                System.out.print("Inserisci versione  -->");
                automobile.setVersione(Console.leggiIntero());
                
                
                automobile.setProprietario(proprietario);
                try {
                    DAOAutomobile.doInsert(automobile);
                } catch(DAOException pe){
                    Principale.stampaMessaggioErrore("Inserimento impossibile");
                    System.err.println(pe);
                }
            } else {
                Principale.stampaMessaggioErrore("Targa esistente");
            }
        } catch(DAOException pe){
            System.err.println(pe);
        }
    }
    
}