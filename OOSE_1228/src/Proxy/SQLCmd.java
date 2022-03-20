package Proxy;

import java.sql.*;

public abstract class SQLCmd {
    protected Connection conDB = null;
    protected String sql;
    protected ResultSet rows;
    protected String username;
    protected Object result;

    //abstract hook method
    public abstract void makequery();
    public abstract void processResult() throws SQLException;

    //template method
    public Object excute(){
        connectDB();
        makequery();
        try {
            executeQuery();
            processResult();
            disconnectDB();
        }catch (SQLException E){
            E.printStackTrace();
        };
        return result;
    }

    //steps that are common.
    public void connectDB(){
        // Load the JDBC driver
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Create a connection to the database
            String serverName = "localhost";
            String portNumber = "1521"; // fill in your database port (ex:1521)
            String sid = "xe";
            String url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
            String username = "SYSTEM";
            String password = "Ab570317baby2&";
            // set the connection object
            conDB = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected.");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Database connection failed.");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Database connection failed.");
        }
    }

    public void executeQuery() throws SQLException {
        Statement stmt = conDB.createStatement();
        rows = stmt.executeQuery(sql);
    }

    public void disconnectDB() throws SQLException {
        conDB.close();
    }
}
