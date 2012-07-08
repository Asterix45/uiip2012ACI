package it.unina.utilita;

import java.io.*;

/**
 * Questa classe consente di effettuare operazioni di lettura
 * dallo standard input per i principali tipi di dato primitivi
 * 
 */

public class Console {
    
    private Console() {}
    
    /**
     * La proprietà stdin trasforma l'input stream System.in
     * in un BufferedReader su cui usare readLine
     */
    private static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    
    /**
     * Preleva una stringa dallo standard input
     * @return la stringa acquisita dallo standard input
     */
    public static String leggiStringa() {
        while (true) {
            try {
                String valore = stdin.readLine();
                return valore;
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
    
    /**
     * Preleva un numero intero dallo standard input;
     * costringe l'utente a ripetere l'immissione finche' il dato fornito
     * non e' effettivamente un valore ammissibile per il tipo int
     * @return il numero intero prelevato dallo standard input
     */
    public static int leggiIntero() {
        while (true) {
            try {
                int valore = Integer.parseInt(stdin.readLine());
                return valore;
            } catch (IOException e) {
                System.out.println(e);
            } catch (NumberFormatException nfe) {
                System.out.print("*** Errore: non si tratta di un numero intero. Riprova --> ");
            }
        }
    }
    
    /**
     * Preleva un numero reale di tipo float dallo standard input;
     * costringe l'utente a ripetere l'immissione finche' il dato fornito
     * non e' effettivamente un valore ammissibile per il tipo float
     * @return il valore float prelevato dallo standard input
     */
    public static float leggiFloat() {
        while (true) {
            try {
                float valore = Float.parseFloat(stdin.readLine());
                return valore;
            } catch (IOException e) {
                System.out.println(e);
            } catch (NumberFormatException nfe) {
                System.out.print("*** Errore: non si tratta di un numero reale. Riprova --> ");
            }
        }
    }
    
    /**
     * Preleva un numero reale di tipo double dallo standard input;
     * costringe l'utente a ripetere l'immissione finche' il dato fornito
     * non e' effettivamente un valore ammissibile per il tipo double
     * @return il valore double prelevato dallo standard input
     */
    public static double leggiDouble() {
        while (true) {
            try {
                double valore = Double.parseDouble(stdin.readLine());
                return valore;
            } catch (IOException e) {
                System.out.println(e);
            } catch (NumberFormatException nfe) {
                System.out.print("*** Errore: non si tratta di un numero reale. Riprova --> ");
            }
        }
    }
    
    /**
     * Preleva un carattere dallo standard input;
     * costringe l'utente a ripetere l'immissione finche' il dato fornito
     * non e' effettivamente un singolo carattere
     * @return il valore char prelevato dallo standard input
     */
    public static char leggiCarattere() {
        while (true) {
            try {
                String linea = stdin.readLine();
                if (linea.length() == 1) {
                    char valore = linea.charAt(0);
                    return valore;
                } else {
                    System.out.print("*** Errore: non si tratta di un carattere. Riprova --> ");
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
    
}



