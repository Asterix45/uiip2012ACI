package it.unina.aci.modello;

import java.util.*;

public class Proprietario {

    private long id;
    private String codiceFiscale;
    private String nome;
    private String cittaDiResidenza;
    private int annoPatente;
    private List<Automobile> automobili = new ArrayList<Automobile>();

    public void setId(long id){
        this.id = id;
    }

    public long getId(){
        return this.id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setCodiceFiscale(String codiceFiscale){
        this.codiceFiscale = codiceFiscale;
    }

    public void setCittaDiResidenza(String cittaDiResidenza){
        this.cittaDiResidenza = cittaDiResidenza;
    }

    public void setAnnoPatente(int annoPatente){
        this.annoPatente = annoPatente;
    }

    public String getNome(){
        return this.nome;
    }

    public String getCodiceFiscale(){
        return this.codiceFiscale;
    }

    public String getCittaDiResidenza(){
        return this.cittaDiResidenza;
    }

    public int getAnnoPatente() {
        return this.annoPatente;
    }

    public List getAutomobili() {
        return this.automobili;
    }

    public Automobile getAutomobile(int i) {
        if (i < 0 || i >= automobili.size()) {
            return null;
        }
        return (Automobile)this.automobili.get(i);
    }

    public void svuotaAutomobili() {
        this.automobili = new ArrayList<Automobile>();
    }

    public void aggiungiAutomobile(Automobile automobile) {
        int posizione = cercaAutomobile(automobile.getTarga());
        if (posizione == -1) {
            this.automobili.add(automobile);
            automobile.setProprietario(this);
        }
    }

    public void eliminaAutomobile(int i) {
        if (i < 0 || i >= automobili.size()) {
            throw new IllegalArgumentException("Posizione scorretta");
        }
        this.automobili.remove(i);
    }

    public int getNumeroAutomobili() {
        return this.automobili.size();
    }

    public int cercaAutomobile(String targa) {
        for (int i = 0; i < automobili.size(); i++) {
            Automobile corrente = (Automobile)automobili.get(i);
            if (corrente.getTarga().equals(targa)) {
                return i;
            }
        }
        return -1;
    }

	@Override
	public String toString() {
		
		
		String token="\n";
		String s1="Nome Proprietario : "+this.nome;
		String s2="Cittˆ di Residenza : "+this.cittaDiResidenza;
		String s3="Codice Fiscale : "+this.codiceFiscale;
		String s4="Anno Patente : "+new Integer(this.annoPatente).toString();
		
		String car="";
		
		for(Automobile automobile:this.automobili){
			
			car=car+token+automobile.toString();
			
		}
		
		
		if(car.equals("")){
		
			return s1+token+s2+token+s3+token+s4;	
			
		}
		
		else{
		car= "Tutte le automobile dello stesso proprietario :"+ token+car;
		
		
		return s1+token+s2+token+s3+token+s4+token+car;
		}	
	}






}



