package it.unina.aci.controllo;

import it.unina.aci.modello.*;
import it.unina.aci.persistenza.*;
import it.unina.utilita.Console;

public class ControlloAggiornaProprietario {
    
    public void schermoIniziale(){
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
            schermoAggiornaProprietario(proprietario);
        }
    }
    
    private void schermoAggiornaProprietario(Proprietario proprietario) {
        System.out.println("\n--------------------------------------");
        System.out.println("  Aggiornamento dati proprietario ");
        System.out.println("--------------------------------------\n");
        if (proprietario != null) {
            leggiNuoviDatiProprietario(proprietario);
            try {
                DAOProprietario.doUpdate(proprietario);
            } catch (DAOException daoe) {
                System.out.println(daoe);
            }
        }
    }
    
    private void leggiNuoviDatiProprietario(Proprietario proprietario) {
        System.out.print("Inserisci il nome                ->");
        String nome = Console.leggiStringa();
        proprietario.setNome(nome);
        System.out.print("Inserisci la citta' di residenza ->");
        String cittaDiResidenza = Console.leggiStringa();
        proprietario.setCittaDiResidenza(cittaDiResidenza);
        System.out.print("Inserisci l'anno in cui ha conseguito la patente ->");
        int annoPatente = Console.leggiIntero();
        proprietario.setAnnoPatente(annoPatente);
    }
    
}
