package org.tondo;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.h2.jdbc.JdbcSQLException;
import org.junit.BeforeClass;
import org.junit.Test;

public class InMemoryDbTest {
	
	
	@BeforeClass
	public static void setup() throws ClassNotFoundException {
		Class.forName("org.h2.Driver");
	}

	@Test
	public void testCreateAndInitializeNamedDatabase() throws SQLException {
		try (Connection con = DriverManager.getConnection("jdbc:h2:mem:t1;INIT=runscript from 'classpath:create.sql'")) {
			try (PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) as CNT FROM PERSON")) {
				ResultSet rs = stmt.executeQuery();
				assertTrue(rs.next());
				assertEquals("no records were inserted", 0, rs.getInt("cnt"));
			}
		}
	}
	
	/**
	 * By default in memory DB is deleted with all its data when connection to that DB is closed.
	 * 
	 * Same database name is used for all connections
	 */
	@Test
	public void testCreateSquentialConnections() throws SQLException {
		// DDL with another script containing data initialization
		// \\; - required to separate scripts executed after connection is created
		try (Connection con = DriverManager.getConnection("jdbc:h2:mem:t1;INIT=RUNSCRIPT FROM 'classpath:create.sql'\\;RUNSCRIPT FROM 'classpath:insert.sql'")) {
			try (PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) as CNT FROM PERSON")) {
				ResultSet rs = stmt.executeQuery();
				assertTrue(rs.next());
				assertEquals("One record in DB from insert.sql", 1, rs.getInt("cnt"));
			}
		}
		
		// create another connection without insert script, so table will exists but will be empty
		try (Connection con = DriverManager.getConnection("jdbc:h2:mem:t1;INIT=RUNSCRIPT FROM 'classpath:create.sql'")) {
			try (PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) as CNT FROM PERSON")) {
				ResultSet rs = stmt.executeQuery();
				assertTrue(rs.next());
				assertEquals("No data found because closing connection by default remove data fro memory DB", 
						0, rs.getInt("cnt"));
			}
		}
		
		// connection without even without DDL script
		try (Connection con = DriverManager.getConnection("jdbc:h2:mem:t1")) {
			try (PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) as CNT FROM PERSON")) {
				stmt.executeQuery();
				fail("table not exists JdbcSQLException expected!");
			} catch (JdbcSQLException e) {}
		}
	}
}
