package it.unina.aci.controllo;

import it.unina.aci.modello.Automobile;
import it.unina.aci.persistenza.DAOAutomobile;
import it.unina.aci.persistenza.DAOException;
import it.unina.utilita.Console;

public class Main1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
while(true){
	System.out.println("Inserisci la targa dell'automobile che vuoi cercare o stop per uscire");
	String keySearchAuto=Console.leggiStringa();
	if(keySearchAuto.equalsIgnoreCase("stop")){
		break;
	}
	
	try {
		
	
		Automobile automobile= DAOAutomobile.doSelectTarga(keySearchAuto);
		
		System.out.println("Query Results: ");
		if(automobile!=null){
			DAOAutomobile.caricaProprietario(automobile);
			System.out.println(automobile.toString());
		                     }
		else{
		System.out.println("No auto per quella targa");	
		}
		
		
		} catch (DAOException e) {
		
		System.out.println("Problems with Database..");
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	}
	
}
