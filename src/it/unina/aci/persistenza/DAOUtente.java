package it.unina.aci.persistenza;

import java.sql.*;
import java.util.*;

import it.unina.aci.modello.Utente;

public class DAOUtente {

    public static Utente doSelectNomeUtente(String nomeUtente) throws DAOException {
        Utente utente = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("select * from utenti where nomeutente = ?");
            statement.setString(1, nomeUtente);                
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                utente = new Utente();
                utente.setId(resultSet.getLong("id"));
                utente.setNomeUtente(resultSet.getString("nomeutente"));
                utente.setPassword(resultSet.getString("password"));
                utente.setNome(resultSet.getString("nome"));
                utente.setRuolo(resultSet.getString("ruolo"));
            }
        } catch (SQLException sqle) {
            throw new DAOException(sqle);
        } finally {
            dataSource.close(resultSet);
            dataSource.close(statement);
            dataSource.close(connection);
        }
        return utente;
    }

}



