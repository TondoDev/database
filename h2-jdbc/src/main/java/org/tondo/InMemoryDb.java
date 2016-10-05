package org.tondo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * In memory database
 *
 */
public class InMemoryDb {
    public static void main( String[] args ) throws ClassNotFoundException, SQLException {
    	// initialize driver
        Class.forName("org.h2.Driver");
        
        // connection to private in memory DB (last colon is required)
        try (Connection con = DriverManager.getConnection("jdbc:h2:mem:;INIT=runscript from 'classpath:initdb.sql'")) {
        	System.out.println(con.getClass().getName());
        	
        	PreparedStatement statement = con.prepareStatement("SELECT * FROM PERSON");
        	ResultSet rs = statement.executeQuery();
        	while(rs.next()) {
        		System.out.println(rs.getString("name"));
        		System.out.println(rs.getDate("birthdate"));
        	}
        }
    }
}
