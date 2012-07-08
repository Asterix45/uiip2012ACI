package it.unina.aci.persistenza;

public interface DataSource {
    
    public java.sql.Connection getConnection() throws DAOException;
    
    public void close(java.sql.Connection connection);
    
    public void close(java.sql.Statement statement);

    public void close(java.sql.ResultSet resultSet);

}