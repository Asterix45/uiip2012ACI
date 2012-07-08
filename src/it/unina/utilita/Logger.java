package it.unina.utilita;

/**
 * Questa classe consente di effettuare operazioni di logging
 * su console e su file utilizzando la libreria java.util.logging
 * Sono previsti quattro diversi livelli di logging
 * (in ordine di rilevanza decrescente):
 * <ul>
 * <li>SEVERE</li>
 * <li>INFO (livello standard)</li>
 * <li>FINE</li>
 * <li>FINER</li>
 * </ul>
 * E' possibile inoltre specificare i livelli off e all
 * Il livello di default (stabilito in %JRE_HOME%\lib\logging.properties) e' INFO
 * Per stabilire un livello di logging diverso utilizzare l'opzione:<br />
 * -Dit.unina.utilita.LogLevel=<livello><br />
 * es: java -Dit.unina.utilita.LogLevel=fine<br />
 * E' possibile scegliere se effettuare il logging su console (default) o su un file
 * Per scegliere il file utilizzare l'opzione: <br />
 * -Dit.unina.utilita.LogFile=<nomeFile><br />
 * es: java -Dit.unina.utilita.LogLevel=finer -Dit.unina.utilita.LogFile=e:\tmp\log.txt<br />
 *
 *
 */
public class Logger {

    /**
     * Viene utilizzato un unico logger chiamato "it.unibas.utilita"
     */
    private static final java.util.logging.Logger logger;

    /**
     * Il blocco di inizializzazione statica inizializza il logger,
     * imposta il livello di logging utilizzando la proprieta' it.unina.utilita.LogLeve
     * e sceglie il flusso su cui indirizzare il logging sulla base del valore della
     * proprieta' it.unina.utilita.LogFile
     */
    static {
        logger = java.util.logging.Logger.getLogger("it.unina.utilita");
        java.util.logging.Level livello = selezionaLivello();
        java.util.logging.Handler handler = selezionaHandler();
        logger.setUseParentHandlers(false);
        logger.addHandler(handler);
        setLevel(livello);
    }

    /**
     * Restituisce il livello scelto dall'utente o quello standard
     */
    private static java.util.logging.Level selezionaLivello() {
        String livello = System.getProperty("it.unina.utilita.LogLevel");
        logger.fine(livello);
        if (livello != null) {
            if (livello.equalsIgnoreCase("finer")) {
                return java.util.logging.Level.FINER;
            } else if (livello.equalsIgnoreCase("fine")) {
                return java.util.logging.Level.FINE;
            } else if (livello.equalsIgnoreCase("severe")) {
                return java.util.logging.Level.SEVERE;
            } else if (livello.equalsIgnoreCase("off")) {
                return java.util.logging.Level.OFF;
            } else if (livello.equalsIgnoreCase("all")) {
                return java.util.logging.Level.ALL;
            }
        }
        return java.util.logging.Level.INFO;
    }

    /**
     * Imposta l'handler corrispondente al flusso scelto dall'utente
     */
    private static java.util.logging.Handler selezionaHandler(){
        java.util.logging.Handler handler = null;
        String nomeFile = System.getProperty("it.unina.utilita.LogFile");
        logger.fine(nomeFile);
        if (nomeFile != null) {
            try {
                java.util.logging.FileHandler fileHandler = new java.util.logging.FileHandler(nomeFile);
                fileHandler.setFormatter(new java.util.logging.SimpleFormatter());
                handler = fileHandler;
            } catch (java.io.IOException ioe) {
                logger.severe("Impossibile utilizzare il file di log specificato");
            }
        } else {
            java.util.logging.ConsoleHandler consoleHandler = new java.util.logging.ConsoleHandler();
            handler = consoleHandler;
        }
        return handler;
    }

    /**
     * Restituisce il livello di logging
     */
    public static java.util.logging.Level getLevel() {
       return logger.getLevel();
    }

    /**
     * Imposta il livello di logging
     */
    public static void setLevel(java.util.logging.Level livello) {
       logger.setLevel(livello);
       java.util.logging.Handler[] handlers = logger.getHandlers();
       for (int i = 0; i < handlers.length; i++) {
           handlers[i].setLevel(livello);
       }
    }

    /**
     * Effettua il log di un messaggio con il livello "finer"
     * @param messaggio la stringa di cui effettuare il logging
     */
    public static void logFiner(String messaggio) {
        logger.logp(java.util.logging.Level.FINER, "", "", messaggio);
    }

    /**
     * Effettua il log di un messaggio con il livello "finer" specificando
     * inoltre la classe e il metodo da cui origina la chiamata
     * @param classe il nome della classe da cui proviene la chiamata
     * @param metodo il nome del metodo da cui proviene la chiamata
     * @param messaggio la stringa di cui effettuare il logging
     */
    public static void logFiner(String classe, String metodo, String messaggio) {
        logger.logp(java.util.logging.Level.FINER, classe, metodo, messaggio);
    }

    /**
     * Effettua il log di un messaggio con il livello "fine"
     * @param messaggio la stringa di cui effettuare il logging
     */
    public static void logFine(String messaggio) {
        logger.logp(java.util.logging.Level.FINE, "", "", messaggio);
    }

    /**
     * Effettua il log di un messaggio con il livello "fine" specificando
     * inoltre la classe e il metodo da cui origina la chiamata
     * @param classe il nome della classe da cui proviene la chiamata
     * @param metodo il nome del metodo da cui proviene la chiamata
     * @param messaggio la stringa di cui effettuare il logging
     */
    public static void logFine(String classe, String metodo, String messaggio) {
        logger.logp(java.util.logging.Level.FINE, classe, metodo, messaggio);
    }

    /**
     * Effettua il log di un messaggio con il livello "info"
     * @param messaggio la stringa di cui effettuare il logging
     */
    public static void logInfo(String messaggio) {
        logger.logp(java.util.logging.Level.INFO, "", "", messaggio);
    }

    /**
     * Effettua il log di un messaggio con il livello "info" specificando
     * inoltre la classe e il metodo da cui origina la chiamata
     * @param classe il nome della classe da cui proviene la chiamata
     * @param metodo il nome del metodo da cui proviene la chiamata
     * @param messaggio la stringa di cui effettuare il logging
     */
    public static void logInfo(String classe, String metodo, String messaggio) {
        logger.logp(java.util.logging.Level.INFO, classe, metodo, messaggio);
    }

    /**
     * Effettua il log di un messaggio con il livello "severe"
     * @param messaggio la stringa di cui effettuare il logging
     */
    public static void logSevere(String messaggio) {
        logger.logp(java.util.logging.Level.SEVERE, "", "", messaggio);
    }

    /**
     * Effettua il log di un messaggio con il livello "severe" specificando
     * inoltre la classe e il metodo da cui origina la chiamata
     * @param classe il nome della classe da cui proviene la chiamata
     * @param metodo il nome del metodo da cui proviene la chiamata
     * @param messaggio la stringa di cui effettuare il logging
     */
    public static void logSevere(String classe, String metodo, String messaggio) {
        logger.logp(java.util.logging.Level.SEVERE, classe, metodo, messaggio);
    }

}