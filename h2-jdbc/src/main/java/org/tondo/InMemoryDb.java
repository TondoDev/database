package org.tondo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * In memory database
 *
 */
public class InMemoryDb {
    public static void main( String[] args ) throws ClassNotFoundException, SQLException {
    	// initialize driver
        Class.forName("org.h2.Driver");
        
        // connection to private in memory DB
        try (Connection con = DriverManager.getConnection("jdbc:h2:mem:")) {
        	System.out.println(con.getClass().getName());
        }
    }
}
