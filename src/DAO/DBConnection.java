/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;



/**
 * Provides database connectivity functionality
 * 
 * @author Trevor Ross, tros114@wgu.edu
 */
public class DBConnection {
    private static final String databaseName="WJ07PP7";
    private static final String DB_URL="jdbc:mysql://wgudb.ucertify.com/"+databaseName;
    private static final String username="U07PP7";
    private static final String password="53689092615";
    private static final String driver="com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;
    
    /**
     * Opens the connection to the database.
     * 
     * @return
     */
    public static Connection startConnection()
    {
        try {
        Class.forName(driver);
        conn = (Connection) DriverManager.getConnection(DB_URL,username,password);
        System.out.println("Connection Successful");
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Error: " +e.getMessage());
        }
        catch(SQLException e)
        {
            System.out.println("Error: " +e.getMessage());
        }
        
        return conn;
    }
    
    /**
     * Closes the connection to the database.
     * 
     */
    public static void endConnection()
    {
        try {
        conn.close();
        System.out.println("Connection Closed");
        }
        catch(SQLException e)
        {
            System.out.println("Error: " +e.getMessage());
        }
    }
}
