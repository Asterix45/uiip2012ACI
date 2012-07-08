package it.unina.aci.modello;

import it.unina.utilita.Logger;

public class Utente {

    private long id;
    private String nomeUtente;
    private String password;
    private String nome;
    private String ruolo;
    private boolean autenticato;

    public void setId(long id){
        this.id = id;
    }

    public long getId(){
        return this.id;
    }

    public void setNomeUtente(String nomeUtente){
        this.nomeUtente = nomeUtente;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setRuolo(String ruolo){
        this.ruolo=ruolo;
    }

    public String getNomeUtente(){
        return this.nomeUtente;
    }

    public String getPassword(){
        return this.password;
    }

    public String getNome(){
        return this.nome;
    }

    public String getRuolo(){
        return this.ruolo;
    }

    public boolean getAutenticato(){
        return this.autenticato;
    }

    public void verifica(String password){
        String hashPasswordFornita = md5hash(password);
        Logger.logFine("Utente", "verifica", "password utente: " + this.password);
        Logger.logFine("Utente", "verifica", "password fornita: " + hashPasswordFornita);
        if (this.password.equals(hashPasswordFornita)) {
            this.autenticato = true;
        } else {
            this.autenticato = false;
        }
    }

    public  String md5hash(String password) {
        String hashString = null;
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(password.getBytes());
            hashString = "";
            for (int i = 0; i < hash.length; i++) {
                hashString += Integer.toHexString( 
                                  (hash[i] & 0xFF) | 0x100 
                              ).toLowerCase().substring(1,3);
            }
            Logger.logFine("Utente", "md5hash", hashString);
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return hashString;
    }





public static void main(String args[])
{
	
String s="antonio";

Utente u= new Utente();

System.out.println(u.md5hash(s));

}
}