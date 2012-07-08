package it.unina.aci.persistenza;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import it.unina.utilita.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSourcePool implements DataSource {
    
    private String driver;
    private String databaseUri;
    private String userName;
    private String password;
    private ComboPooledDataSource pooledDataSource;
    
    DataSourcePool(String driver, String databaseUri, String userName, String password) throws DAOException {
        try {
            this.pooledDataSource = new ComboPooledDataSource();
            this.pooledDataSource.setDriverClass(driver);
            this.pooledDataSource.setJdbcUrl(databaseUri);
            this.pooledDataSource.setUser(userName);
            this.pooledDataSource.setPassword(password);
            Logger.logFine("Inizializzata DataSourcePool");
        } catch (Exception e) {
            Logger.logSevere(e.toString());
            throw new DAOException(e);
        }
    }
    
    public Connection getConnection() throws DAOException {
        Connection connection = null;
        try {
            connection = this.pooledDataSource.getConnection();
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
