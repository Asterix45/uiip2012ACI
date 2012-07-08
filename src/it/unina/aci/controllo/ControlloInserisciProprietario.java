package it.unina.aci.controllo;

import it.unina.aci.modello.*;
import it.unina.aci.persistenza.*;
import it.unina.utilita.Console;

public class ControlloInserisciProprietario {
    
    public void schermoIniziale(){
        System.out.println("\n--------------------------------------");
        System.out.println("Inserimento di un nuovo proprietario");
        System.out.println("\n--------------------------------------\n");
        System.out.print("Inserisci il codice fiscale      ->");
        String codiceFiscale = Console.leggiStringa();
        Proprietario proprietario = null;
        boolean errore=false;
        try {
            proprietario = DAOProprietario.doSelectCodiceFiscale(codiceFiscale);
            if (proprietario == null) {
                proprietario = leggiDatiProprietario(codiceFiscale);
                try {
                    DAOProprietario.doInsert(proprietario);
                    System.out.println("\nInserimento effettuato\n");
                } catch(DAOException pe){
                    Principale.stampaMessaggioErrore("Inserimento impossibile");
                    System.err.println(pe);
                }
            } else {
                Principale.stampaMessaggioErrore("Codice fiscale esistente");
            }
        } catch (DAOException pe) {
            System.err.println(pe);
        }
    }
    
    private Proprietario leggiDatiProprietario(String codiceFiscale) {
        Proprietario proprietario = new Proprietario();
        proprietario.setCodiceFiscale(codiceFiscale);
        System.out.print("Inserisci il nome                ->");
        String nome = Console.leggiStringa();
        proprietario.setNome(nome);
        System.out.print("Inserisci la citta' di residenza ->");
        String cittaDiResidenza = Console.leggiStringa();
        proprietario.setCittaDiResidenza(cittaDiResidenza);
        System.out.print("Inserisci l'anno in cui ha conseguito la patente ->");
        int annoPatente = Console.leggiIntero();
        proprietario.setAnnoPatente(annoPatente);
        return proprietario;
    }
    
}
