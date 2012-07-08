package it.unina.aci.modello;

import java.util.*;

public class Automobile {

    private long id;
    private int versione;
    private String targa;
    private int cilindrata;
    private String modello;
    private long idProprietario;
    private Proprietario proprietario;

    public void setId(long id){
        this.id = id;
    }

    public long getId(){
        return this.id;
    }

    public void setVersione(int versione){
        this.versione = versione;
    }

    public int getVersione(){
        return this.versione;
    }

    public void setTarga(String targa){
        this.targa = targa;
    }

    public String getTarga(){
        return this.targa;
    }

    public void setCilindrata(int cilindrata){
        this.cilindrata = cilindrata;
    }

    public int getCilindrata(){
        return this.cilindrata;
    }

    public void setModello(String modello){
        this.modello = modello;
    }

    public String getModello(){
        return this.modello;
    }

    public void setIdProprietario(long idProprietario){
        this.idProprietario = idProprietario;
    }

    public long getIdProprietario() {
        return this.idProprietario;
    }

    public void setProprietario(Proprietario proprietario){
        if (proprietario != null) {
            this.proprietario = proprietario;
            this.idProprietario = proprietario.getId();
        }
    }

    public Proprietario getProprietario(){
        return this.proprietario;
    }

	@Override
	public String toString() {

		String token= "\n";
		String s1= "Id :"+ this.id;
		String s2= "IdProprietario : "+ this.idProprietario;
		String s3= "Targa : "+ this.targa;
		String s4= "Modello : "+ this.modello;
		String s5= "Versione : "+ this.versione;
		String s6= "Cilidrata : "+ this.cilindrata;
		
		if(this.proprietario!=null)
		   	return s1+token+s2+token+s3+token+s4+token+s5+token+s6+token+this.proprietario.toString();
		else{
			return s1+token+s2+token+s3+token+s4+token+s5+token+s6;
		
		}
		
		
		
	}

    
    
    
    
    
    
}



