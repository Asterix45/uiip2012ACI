package it.unina.aci.persistenza;

import it.unina.utilita.Logger;

import java.sql.*;

public class GeneratoreDiId {
    
    private static GeneratoreDiId singleton = new GeneratoreDiId();
    
    private GeneratoreDiId() {}
    
    public static GeneratoreDiId getInstance() {
        return singleton;
    }
    
    private long high;
    private int low = 0;
    private int maxlow = 0;
            
    public long getNuovoId() throws DAOException {
        if (this.low == this.maxlow) {
            acquisisciNuovoHigh();
        }
        long nuovoId = this.high + this.low;
        this.low++;
        return nuovoId;
    }
    
    private void acquisisciNuovoHigh() throws DAOException {
        DataSource dataSource = DataSourceFactory.getInstance().getDataSource();
        Connection connection = null;
        PreparedStatement statementSelect = null;
        PreparedStatement statementUpdate = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            statementSelect = connection.prepareStatement("select * from idtable for update");
            resultSet = statementSelect.executeQuery();
            resultSet.next();
            this.high = resultSet.getLong("high");
            this.maxlow = resultSet.getInt("maxlow");
            this.low = 0;
            statementUpdate = connection.prepareStatement("update idtable set high = high + ?");
            statementUpdate.setInt(1, this.maxlow);
            statementUpdate.executeUpdate();
            connection.commit();
            Logger.logFine("Acquisito blocco di id da: " + this.high + " a " + (this.high + this.maxlow - 1));
        } catch (SQLException sqle) {
            Logger.logSevere(sqle.toString());
            try {
                connection.rollback();
            } catch (SQLException e) {}
            throw new DAOException(sqle);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException sqle) {}
                dataSource.close(resultSet);
                dataSource.close(statementSelect);
                dataSource.close(statementUpdate);
                dataSource.close(connection);
            }
        }
    }
}
