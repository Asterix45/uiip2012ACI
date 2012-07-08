package it.unina.aci.persistenza;

import it.unina.aci.modello.Automobile;
import it.unina.aci.modello.Proprietario;
import it.unina.utilita.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;

public class DAOAutomobile {
    
    public static Automobile doSelectTarga(String targa) throws DAOException {
        Automobile automobile = null;
        Connection connection = null;
        DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
        try {
            connection = dataSource.getConnection();
            automobile = doSelectTarga(targa, connection);
        } finally {
            dataSource.close(connection);
        }
        return automobile;
    }
    
    public static Automobile doSelectTarga(String targa, Connection connection) throws DAOException {
        Automobile automobile = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("select * from automobili where targa = ?");
            statement.setString(1, targa);
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                automobile = new Automobile();
                automobile.setId(resultSet.getLong("id"));
                automobile.setVersione(resultSet.getInt("versione"));
                automobile.setTarga(resultSet.getString("targa"));
                automobile.setModello(resultSet.getString("modello"));
                automobile.setCilindrata(resultSet.getInt("cilindrata"));
                automobile.setIdProprietario(resultSet.getLong("idProprietario"));
            }
        } catch (SQLException sqle) {
            throw new DAOException(sqle);
        } finally {
            DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
            dataSource.close(resultSet);
            dataSource.close(statement);
        }
        return automobile;
    }
    
    public static Automobile doSelectTargaForUpdate(String targa) throws DAOException {
        Automobile automobile = null;
        Connection connection = null;
        DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
        try {
            connection = dataSource.getConnection();
            automobile = doSelectTargaForUpdate(targa, connection);
        } finally {
            dataSource.close(connection);
        }
        return automobile;
    }
    
    public static Automobile doSelectTargaForUpdate(String targa, Connection connection) throws DAOException {
        Automobile automobile = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("select * from automobili where targa = ? for update");
            statement.setString(1, targa);
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                automobile = new Automobile();
                automobile.setId(resultSet.getLong("id"));
                automobile.setVersione(resultSet.getInt("versione"));
                automobile.setTarga(resultSet.getString("targa"));
                automobile.setModello(resultSet.getString("modello"));
                automobile.setCilindrata(resultSet.getInt("cilindrata"));
                automobile.setIdProprietario(resultSet.getLong("idProprietario"));
            }
        } catch (SQLException sqle) {
            throw new DAOException(sqle);
        } finally {
            DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
            dataSource.close(resultSet);
            dataSource.close(statement);
        }
        return automobile;
    }

    public static void doInsert(Automobile automobile) throws DAOException {
        Connection connection = null;
        DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
        try {
            connection = dataSource.getConnection();
            doInsert(automobile, connection);
        } finally {
            dataSource.close(connection);
        }
    }
    
    public static void doInsert(Automobile automobile, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            automobile.setId(GeneratoreDiId.getInstance().getNuovoId());
            //automobile.setVersione(automobile.getVersione() + 1);
            statement = connection.prepareStatement("insert into automobili(id,versione,targa,modello,cilindrata,idproprietario) values (?,?, ?, ?, ?, ?)");
            
            
            statement.setLong(1, automobile.getId());
            statement.setInt(2, automobile.getVersione());
            statement.setString(3, automobile.getTarga());
            statement.setString(4, automobile.getModello());
            statement.setInt(5, automobile.getCilindrata());
            statement.setLong(6, automobile.getProprietario().getId());
            
            
            statement.executeUpdate();
        } catch (SQLException sqle) {
            throw new DAOException(sqle);
        } finally {
            DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
            dataSource.close(statement);
        }
    }
    
    public static void doUpdate(Automobile automobile) throws DAOException {
        Connection connection = null;
        DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
        try {
            connection = dataSource.getConnection();
            doUpdate(automobile, connection);
        } finally {
            dataSource.close(connection);
        }
    }
    
    public static void doUpdate(Automobile automobile, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            automobile.setVersione(automobile.getVersione() + 1);
            statement = connection.prepareStatement("update automobili set versione = ?, " + 
                                                    "targa = ?, modello = ?, cilindrata = ?," + 
                                                    "idproprietario = ? " + 
                                                    "where id = ?");
            statement.setInt(1,  automobile.getVersione());
            statement.setString(2, automobile.getTarga());
            statement.setString(3, automobile.getModello());
            statement.setInt(4, automobile.getCilindrata());
            statement.setLong(5, automobile.getProprietario().getId());
            statement.setLong(6, automobile.getId());
            statement.executeUpdate();
        } catch (SQLException sqle) {
            throw new DAOException(sqle);
        } finally {
            DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
            dataSource.close(statement);
        }
    }
    
    public static void doDelete(Automobile automobile) throws DAOException {
        Connection connection = null;
        DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
        try {
            connection = dataSource.getConnection();
            doDelete(automobile, connection);
        } finally {
            dataSource.close(connection);
        }
    }
    
    public static void doDelete(Automobile automobile, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("delete from automobili where id = ?");
            statement.setLong(1, automobile.getId());
            statement.executeUpdate();
        } catch (SQLException sqle) {
            throw new DAOException(sqle);
        } finally {
            DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
            dataSource.close(statement);
        }
    }
    
    public static void caricaProprietario(Automobile automobile) throws DAOException {
        Connection connection = null;
        DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
        try {
            connection = dataSource.getConnection();
            caricaProprietario(automobile, connection);
        } finally {
            dataSource.close(connection);
        }
    }
    
    public static void caricaProprietario(Automobile automobile, Connection connection) throws DAOException {
        Proprietario proprietario = DAOProprietario.doSelectId(automobile.getIdProprietario(), connection);
        automobile.setProprietario(proprietario);
    }
    
    public static void scambioTarghe(Automobile automobile1, Automobile automobile2) throws DAOException {
        Connection connection = null;
        DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            Automobile automobile1New = DAOAutomobile.doSelectTargaForUpdate(automobile1.getTarga(), connection);
            if (automobile1New == null) {
                connection.rollback();
                throw new DAOException("Scambio di targa: Prima automobile inesistente");
            } else if (automobile1New.getVersione() != automobile1.getVersione()) {
                connection.rollback();
                throw new DAOException("Errore: i dati dell'automobile n.1 sono cambiati");
            }
            DAOAutomobile.caricaProprietario(automobile1, connection);
            Automobile automobile2New = DAOAutomobile.doSelectTargaForUpdate(automobile2.getTarga(), connection);
            if (automobile2New == null) {
                connection.rollback();
                throw new DAOException("Scambio di targa: Seconda automobile inesistente");
            } else if (automobile2New.getVersione() != automobile2.getVersione()) {
                connection.rollback();
                throw new DAOException("Errore: i dati dell'automobile n.2 sono cambiati");
            }
            DAOAutomobile.caricaProprietario(automobile2, connection);
            String targa1 = automobile1.getTarga();
            String targa2 = automobile2.getTarga();
            automobile1.setTarga("tmp");
            DAOAutomobile.doUpdate(automobile1, connection);
            automobile2.setTarga(targa1);
            DAOAutomobile.doUpdate(automobile2, connection);
            automobile1.setTarga(targa2);
            DAOAutomobile.doUpdate(automobile1, connection);
            connection.commit();
        } catch (SQLException sqle) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                Logger.logSevere(e.toString());
            }
            throw new DAOException(sqle);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    dataSource.close(connection);
                }
            } catch (SQLException sqle) {
                Logger.logSevere(sqle.toString());
            }
        }
    }
    
    
    public static Collection<Proprietario> doSelectElencoProprietariEAutomobili() throws DAOException{
    	 Connection connection = null;
         Statement statement = null;
         ResultSet resultSet = null;
         HashMap<String,Proprietario> elenco = new HashMap<String,Proprietario>();
         DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
         try {
             connection = dataSource.getConnection();
             statement = connection.createStatement();
            String query = "select * " +
                    "from proprietari left join automobili " +
                    "on proprietari.id= automobili.idproprietario";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                String codiceFiscale = resultSet.getString("codiceFiscale");
                Proprietario proprietario = (Proprietario)elenco.get(codiceFiscale);
                if (proprietario == null){
                    proprietario = new Proprietario();
                    proprietario.setCodiceFiscale(codiceFiscale);
                    proprietario.setNome(resultSet.getString("nome"));
                    proprietario.setCittaDiResidenza(resultSet.getString("cittaDiResidenza"));
                    proprietario.setAnnoPatente(resultSet.getInt("annoPatente"));
                    elenco.put(codiceFiscale, proprietario);
                }
                String targa = resultSet.getString("targa");
                if (targa != null) {
                    Automobile automobile = new Automobile();
                    automobile.setTarga(resultSet.getString("targa"));
                    automobile.setModello(resultSet.getString("modello"));
                    automobile.setCilindrata(resultSet.getInt("cilindrata"));
                    automobile.setProprietario(proprietario);
                    proprietario.aggiungiAutomobile(automobile);
                }
            }
        } catch (SQLException sqle) {
            throw new DAOException(sqle);
        } finally {
            dataSource.close(resultSet);
            dataSource.close(statement);
            dataSource.close(connection);
        }
        return elenco.values();
    }

    
}



