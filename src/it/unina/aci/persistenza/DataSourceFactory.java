package it.unina.aci.persistenza;

import it.unina.aci.parameter.ApplicationParameter;
import it.unina.utilita.Logger;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class DataSourceFactory {

    private static DataSourceFactory singleton = new DataSourceFactory();

    public static DataSourceFactory getInstance() {
        return singleton;
    }
    
    private Properties proprieta;
    private DataSource dataSource;
    
    public DataSource getDataSource() {
        return this.dataSource;
    }

    private DataSourceFactory() {
        try {
            caricaProprieta();
            inizializza();
        } catch (DAOException e) {
            Logger.logSevere("Impossibile inizializzare la DataSourceFactory");
        }
    }
         
    private void caricaProprieta() throws DAOException {
        String nomeFile = ApplicationParameter.getNamePathFileConfDB();

        FileInputStream fis =null;
        
        InputStream stream = null;
        try {
            //stream = this.getClass().getResourceAsStream(nomeFile);
            fis= new FileInputStream(nomeFile);
            
            this.proprieta = new Properties();
            this.proprieta.load(fis);
            Logger.logFiner(this.proprieta.toString());
            
        } catch (Exception e) {
            Logger.logSevere(e.toString());
            throw new DAOException("Impossibile caricare il file di configurazione " + e);
        } finally {
            if (fis != null) {
                try {fis.close();} catch (Exception e) {}
            }
        }
    }
    
    private void inizializza() throws DAOException {
        String tipoDataSource = this.proprieta.getProperty("tipoDataSource");
        if (!tipoDataSource.equals("semplice") && !tipoDataSource.equals("pool")) {
            throw new DAOException("Valore scorretto del parametro di configurazione tipoDataSource: " + tipoDataSource);
        }
        String driver = this.proprieta.getProperty("driver");
        String databaseUri = this.proprieta.getProperty("databaseUri");
        String userName = this.proprieta.getProperty("userName");
        String password = this.proprieta.getProperty("password");
        if (tipoDataSource.equals("semplice")) {
            this.dataSource = new DataSourceSemplice(driver, databaseUri, userName, password);
        } else if (tipoDataSource.equals("pool")) {
            this.dataSource = new DataSourcePool(driver, databaseUri, userName, password);
        }
    }

    
}
