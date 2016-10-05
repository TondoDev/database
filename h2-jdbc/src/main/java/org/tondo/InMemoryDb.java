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
        // driverManager is obsolete, preferred is DataSource from JDBC >= 2
        // connection to private in memory DB (last colon is required)
        try (Connection con = DriverManager.getConnection("jdbc:h2:mem:aaa;INIT=runscript from 'classpath:initdb.sql'")) {
        	System.out.println(con.getClass().getName());
        	
        	try(PreparedStatement statement = con.prepareStatement("SELECT * FROM PERSON")) {
        		ResultSet rs = statement.executeQuery();
            	while(rs.next()) {
            		System.out.println(rs.getString("name"));
            		System.out.println(rs.getDate("birthdate"));
            	}
        	}
        	
        	try(PreparedStatement statement = con.prepareStatement("SELECT COUNT(*) as CNT FROM PERSON")) {
        		ResultSet rs = statement.executeQuery();
            	if(rs.next()) {
            		System.out.println(rs.getInt("cnt"));
            	}
        	}
        	
        	try(PreparedStatement statement = con.prepareStatement("INSERT INTO Person(name, birthdate) VALUES('Jano', '1988-05-07')")) {
        		statement.executeUpdate();
        	}
        	
        	try(PreparedStatement statement = con.prepareStatement("SELECT COUNT(*) as CNT FROM PERSON")) {
        		ResultSet rs = statement.executeQuery();
            	if(rs.next()) {
            		System.out.println(rs.getInt("cnt"));
            	}
        	}
        }
        
        System.out.println("------------------------------------");
        // try (Connection con = DriverManager.getConnection("jdbc:h2:mem:aaa")) {
        // - when connection to in memory db is closed, all DB data are lost!!!
        // -- we can presever data after conenction to NAMED DB is closed with using DB_CLOSE_DELAY 
        // in conenction string: jdbc:h2:mem:test;DB_CLOSE_DELAY=-1
        try (Connection con = DriverManager.getConnection("jdbc:h2:mem:aaa:")) {
        	System.out.println(con.getClass().getName());
        	
        	try(PreparedStatement statement = con.prepareStatement("SELECT * FROM PERSON")) {
        		ResultSet rs = statement.executeQuery();
            	while(rs.next()) {
            		System.out.println(rs.getString("name"));
            		System.out.println(rs.getDate("birthdate"));
            	}
        	}
        	
        	try(PreparedStatement statement = con.prepareStatement("SELECT COUNT(*) as CNT FROM PERSON")) {
        		ResultSet rs = statement.executeQuery();
            	if(rs.next()) {
            		System.out.println(rs.getInt("cnt"));
            	}
        	}
        	
        	try(PreparedStatement statement = con.prepareStatement("INSERT INTO Person(name, birthdate) VALUES('Jano', '1988-05-07')")) {
        		statement.executeUpdate();
        	}
        	
        	try(PreparedStatement statement = con.prepareStatement("SELECT COUNT(*) as CNT FROM PERSON")) {
        		ResultSet rs = statement.executeQuery();
            	if(rs.next()) {
            		System.out.println(rs.getInt("cnt"));
            	}
        	}
        }
    }
}
