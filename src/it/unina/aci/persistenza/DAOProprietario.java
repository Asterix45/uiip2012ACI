package it.unina.aci.persistenza;

import java.sql.*;
import java.util.*;

import it.unina.aci.modello.*;

public class DAOProprietario {
    
    public static Proprietario doSelectId(long id) throws DAOException{
        Proprietario proprietario = null;
        Connection connection = null;
        DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
        try {
            connection = dataSource.getConnection();
            proprietario = doSelectId(id, connection);
        } finally {
            dataSource.close(connection);
        }
        return proprietario;
    }
    
    public static Proprietario doSelectId(long id, Connection connection) throws DAOException{
        Proprietario proprietario = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("select * from proprietari where id = ?");
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                proprietario = new Proprietario();
                proprietario.setId(resultSet.getLong("id"));
                proprietario.setCodiceFiscale(resultSet.getString("codicefiscale"));
                proprietario.setNome(resultSet.getString("nome"));
                proprietario.setCittaDiResidenza(resultSet.getString("cittadiresidenza"));
                proprietario.setAnnoPatente(resultSet.getInt("annoPatente"));
            }
        } catch (SQLException sqle) {
            throw new DAOException("doSelectId" + sqle);
        } finally {
            DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
            dataSource.close(resultSet);
            dataSource.close(statement);
        }
        return proprietario;
    }
    
    public static Proprietario doSelectCodiceFiscale(String codiceFiscale) throws DAOException{
        Proprietario proprietario = null;
        Connection connection = null;
        DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
        try {
            connection = dataSource.getConnection();
            proprietario = doSelectCodiceFiscale(codiceFiscale, connection);
        } finally {
            dataSource.close(connection);
        }
        return proprietario;
    }
    
    public static Proprietario doSelectCodiceFiscale(String codiceFiscale, Connection connection) throws DAOException{
        Proprietario proprietario = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("select * from proprietari where codicefiscale = ?");
            statement.setString(1, codiceFiscale);
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                proprietario = new Proprietario();
                proprietario.setId(resultSet.getLong("id"));
                proprietario.setCodiceFiscale(resultSet.getString("codicefiscale"));
                proprietario.setNome(resultSet.getString("nome"));
                proprietario.setCittaDiResidenza(resultSet.getString("cittadiresidenza"));
                proprietario.setAnnoPatente(resultSet.getInt("annoPatente"));
            }
        } catch (SQLException sqle) {
            throw new DAOException("doSelectCodiceFiscale" + sqle);
        } finally {
            DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
            dataSource.close(resultSet);
            dataSource.close(statement);
        }
        return proprietario;
    }
    
    public static List doSelectNome(String nome) throws DAOException{
        List<Proprietario> listaProprietari = new LinkedList<Proprietario>();
        Connection connection = null;
        DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
        try {
            connection = dataSource.getConnection();
            listaProprietari = doSelectNome(nome, connection);
        } finally {
            dataSource.close(connection);
        }
        return listaProprietari;
    }
    
    public static List<Proprietario> doSelectNome(String nome, Connection connection) throws DAOException{
        List<Proprietario> listaProprietari = new LinkedList<Proprietario>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("select * from proprietari where nome = ?");
            statement.setString(1, nome);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                Proprietario proprietario = new Proprietario();
                proprietario.setId(resultSet.getLong("id"));
                proprietario.setCodiceFiscale(resultSet.getString("codicefiscale"));
                proprietario.setNome(resultSet.getString("nome"));
                proprietario.setCittaDiResidenza(resultSet.getString("cittadiresidenza"));
                proprietario.setAnnoPatente(resultSet.getInt("annopatente"));
                listaProprietari.add(proprietario);
            }
        } catch (SQLException sqle) {
            throw new DAOException("doSelectNome (c): " + sqle);
        } finally {
            DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
            dataSource.close(resultSet);
            dataSource.close(statement);
        }
        return listaProprietari;
    }
    
    public static void caricaAutomobili(Proprietario proprietario) throws DAOException {
        Connection connection = null;
        DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
        try {
            connection = dataSource.getConnection();
            caricaAutomobili(proprietario, connection);
        } finally {
            dataSource.close(connection);
        }
    }
    
    public static void caricaAutomobili(Proprietario proprietario, Connection connection) throws DAOException{
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("select automobili.* " +
                    "from automobili where idproprietario = ?");
            statement.setLong(1, proprietario.getId());
            resultSet = statement.executeQuery();
            proprietario.svuotaAutomobili();
            while (resultSet.next()){
                Automobile automobile = new Automobile();
                automobile.setId(resultSet.getLong("id"));
                automobile.setVersione(resultSet.getInt("versione"));
                automobile.setTarga(resultSet.getString("targa"));
                automobile.setModello(resultSet.getString("modello"));
                automobile.setCilindrata(resultSet.getInt("cilindrata"));
                automobile.setProprietario(proprietario);
                proprietario.aggiungiAutomobile(automobile);
            }
        } catch (SQLException sqle) {
            throw new DAOException("caricaAutomobili: " + sqle);
        } finally {
            DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
            dataSource.close(resultSet);
            dataSource.close(statement);
        }
    }
    
    public static void doInsert(Proprietario proprietario) throws DAOException {
        Connection connection = null;
        DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
        try {
            connection = dataSource.getConnection();
            doInsert(proprietario, connection);
        } finally {
            dataSource.close(connection);
        }
    }
    
    public static void doInsert(Proprietario proprietario, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            proprietario.setId(GeneratoreDiId.getInstance().getNuovoId());
            statement = connection.prepareStatement("insert into proprietari values (?, ?, ?, ?, ?)");
            statement.setLong(1, proprietario.getId());
            statement.setString(2, proprietario.getCodiceFiscale());
            statement.setString(3, proprietario.getNome());
            statement.setString(4, proprietario.getCittaDiResidenza());
            statement.setInt(5, proprietario.getAnnoPatente());
            statement.executeUpdate();
        } catch (SQLException sqle) {
            throw new DAOException("doInsert" + sqle);
        } finally {
            DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
            dataSource.close(statement);
        }
    }
    
    public static void doUpdate(Proprietario proprietario) throws DAOException {
        Connection connection = null;
        DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
        try {
            connection = dataSource.getConnection();
            doUpdate(proprietario, connection);
        } finally {
            dataSource.close(connection);
        }
    }
    
    public static void doUpdate(Proprietario proprietario, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("update proprietari set codicefiscale = ?, " +
                    "nome = ?, cittadiresidenza = ?, annopatente = ? " + 
                    "where id = ?");
            statement.setString(1, proprietario.getCodiceFiscale());
            statement.setString(2, proprietario.getNome());
            statement.setString(3, proprietario.getCittaDiResidenza());
            statement.setInt(4, proprietario.getAnnoPatente());
            statement.setLong(5, proprietario.getId());
            statement.executeUpdate();
        } catch (SQLException sqle) {
            throw new DAOException("doUpdate" + sqle);
        } finally {
            DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
            dataSource.close(statement);
        }
    }
    
    public static void doDelete(Proprietario proprietario) throws DAOException {
        Connection connection = null;
        DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
        try {
            connection = dataSource.getConnection();
            doDelete(proprietario, connection);
        } finally {
            dataSource.close(connection);
        }
    }
    
    public static void doDelete(Proprietario proprietario, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("delete from proprietari where id = ?");
            statement.setLong(1, proprietario.getId());
            statement.executeUpdate();
        } catch (SQLException sqle) {
            throw new DAOException("doDelete" + sqle);
        } finally {
            DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
            dataSource.close(statement);
        }
    }
    
    public static Collection<Proprietario> elencoProprietariEAutomobili() throws DAOException{
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        HashMap<String,Proprietario> elenco = new HashMap<String,Proprietario>();
        DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            String query = "select proprietari.*, automobili.id as idautomobile, targa, modello, cilindrata, idproprietario " +
                    "from proprietari inner join automobili " +
                    "on proprietari.id = automobili.idproprietario";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                String codiceFiscale = resultSet.getString("codiceFiscale");
                Proprietario proprietario = (Proprietario)elenco.get(codiceFiscale);
                if (proprietario == null){
                    proprietario = new Proprietario();
                    proprietario.setCodiceFiscale(codiceFiscale);
                    proprietario.setId(resultSet.getLong("id"));
                    proprietario.setNome(resultSet.getString("nome"));
                    proprietario.setCittaDiResidenza(resultSet.getString("cittaDiResidenza"));
                    proprietario.setAnnoPatente(resultSet.getInt("annoPatente"));
                    elenco.put(codiceFiscale, proprietario);
                }
                Automobile automobile = new Automobile();
                automobile.setId(resultSet.getLong("idautomobile"));
                automobile.setTarga(resultSet.getString("targa"));
                automobile.setModello(resultSet.getString("modello"));
                automobile.setCilindrata(resultSet.getInt("cilindrata"));
                automobile.setIdProprietario(proprietario.getId());
                automobile.setProprietario(proprietario);
                proprietario.aggiungiAutomobile(automobile);
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



