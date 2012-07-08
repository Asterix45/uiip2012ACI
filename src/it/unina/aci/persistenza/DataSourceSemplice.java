package it.unina.aci.persistenza;

import it.unina.utilita.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSourceSemplice implements DataSource {
    
    private String databaseUri;
    private String userName; 
    private String password;

    DataSourceSemplice(String driver, String databaseUri, String userName, String password) throws DAOException {
        try {
            Class.forName(driver);
            this.databaseUri = databaseUri;
            this.userName = userName;
            this.password = password;
        } catch (Exception e) {
            Logger.logSevere("Valore scorretto del parametro di configurazione driver: " + e);
            throw new DAOException(e);
        }        
    }
    
    public Connection getConnection() throws DAOException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(databaseUri, userName, password);
        } catch (SQLException sqle) {
            close(connection);
            throw new DAOException("getConnection: " + sqle);
        }
        return connection;
    }

    public void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException sqle) {
            Logger.logSevere(sqle.toString());
        }
    }

    public void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException sqle) {
            Logger.logSevere(sqle.toString());
        }
    }

    public void close(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException sqle) {
            Logger.logSevere(sqle.toString());
        }
    }    

}
