package it.unina.aci.controllo;

import it.unina.aci.modello.*;
import it.unina.aci.persistenza.*;
import it.unina.utilita.Console;

public class ControlloCancellaProprietario {
    
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
            schermoEliminazioneProprietario(proprietario);
        }
    }
    
    private void schermoEliminazioneProprietario(Proprietario proprietario) {
        if (proprietario != null) {
            System.out.println("\n--------------------------------------");
            System.out.println("  Eliminazione dati proprietario");
            System.out.println("--------------------------------------\n");
            System.out.println("  1. Procedi");
            System.out.println("  0. Esci");
            System.out.print("\n  Scegli --> ");
            int scelta = Console.leggiIntero();
            while (scelta < 0 || scelta > 1){
                System.out.print("Scelta scorretta. Ripeti --> ");
                scelta = Console.leggiIntero();
            }
            if (scelta == 1) {
                try {
                    DAOProprietario.doDelete(proprietario);
                } catch (DAOException daoe) {
                    System.out.println(daoe);
                }
            }
        }
    }
    
}
