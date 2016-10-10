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
	
	/**
	 * Another connection is created before first connection is closed. 
	 * Second connection is closed before first connection is closed.
	 * Second connection doesn't call initialization scripts
	 * 
	 */
	@Test
	public void testSimultaneousConnection() throws SQLException {
		try (Connection con = DriverManager.getConnection("jdbc:h2:mem:t1;INIT=RUNSCRIPT FROM 'classpath:create.sql'\\;RUNSCRIPT FROM 'classpath:insert.sql'")) {
			try (PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) as CNT FROM PERSON")) {
				ResultSet rs = stmt.executeQuery();
				assertTrue(rs.next());
				assertEquals("One record in DB from insert.sql", 1, rs.getInt("cnt"));
			}
			
			try (PreparedStatement stmt = con.prepareStatement("INSERT INTO PERSON(name, birthdate) VALUES('Optimus', '1988-04-28')")) {
				stmt.executeUpdate();
			}
			
			// t1 DB now contains 2 rows - one from init scripts one inserted by code 
			try (PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) as CNT FROM PERSON")) {
				ResultSet rs = stmt.executeQuery();
				assertTrue(rs.next());
				assertEquals("Another row appeared", 2, rs.getInt("cnt"));
			}
			
			// opening another connection to T1 Db before original connection is closed
			try (Connection anotherCon = DriverManager.getConnection("jdbc:h2:mem:t1")) {
				try (PreparedStatement stmt = anotherCon.prepareStatement("SELECT COUNT(*) as CNT FROM PERSON")) {
					ResultSet rs = stmt.executeQuery();
					assertTrue(rs.next());
					assertEquals("Data are still present", 2, rs.getInt("cnt"));
				}
				
				// modify DB
				try (PreparedStatement stmt = anotherCon.prepareStatement("INSERT INTO PERSON(name, birthdate) VALUES('Optimus', '1988-04-28')")) {
					stmt.executeUpdate();
				}
			}
			
			try (PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) as CNT FROM PERSON")) {
				ResultSet rs = stmt.executeQuery();
				assertTrue(rs.next());
				// changes made by second connections are still accessible
				assertEquals("Data are still accessible when second connection is closed", 3, rs.getInt("cnt"));
			}
		}
	}
	
	/**
	 * First created connection initialize DB, then second connection is created.
	 * First connection is closed immediately and second connection try to access data.
	 * @throws SQLException 
	 */
	@Test
	public void testSimultaneousConnection2() throws SQLException {
		Connection anotherConnection = null;
		try (Connection con = DriverManager.getConnection("jdbc:h2:mem:t1;INIT=RUNSCRIPT FROM 'classpath:create.sql'\\;RUNSCRIPT FROM 'classpath:insert.sql'")) {
			try (PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) as CNT FROM PERSON")) {
				ResultSet rs = stmt.executeQuery();
				assertTrue(rs.next());
				assertEquals("One record in DB from insert.sql", 1, rs.getInt("cnt"));
			}
			
			// second connection is created just before first is closed
			anotherConnection = DriverManager.getConnection("jdbc:h2:mem:t1");
		}
		
		if (anotherConnection != null) {
			try (PreparedStatement stmt = anotherConnection.prepareStatement("SELECT COUNT(*) as CNT FROM PERSON")) {
				ResultSet rs = stmt.executeQuery();
				assertTrue(rs.next());
				assertEquals("Data are still present", 1, rs.getInt("cnt"));
			}
			
			anotherConnection.close();
		} 
	}
	
	/**
	 * Executing initialization scripts in simultaneous will cause applying them on current db state (
	 * data are inserted again)   
	 */
	@Test
	public void testReinitSimulConnection() throws SQLException {
		try (Connection con = DriverManager.getConnection("jdbc:h2:mem:t1;INIT=RUNSCRIPT FROM 'classpath:create.sql'\\;RUNSCRIPT FROM 'classpath:insert.sql'")) {
			try (PreparedStatement stmt = con.prepareStatement("INSERT INTO PERSON(name, birthdate) VALUES('Optimus', '1988-04-28')")) {
				stmt.executeUpdate();
			}
			
			// t1 DB now contains 2 rows - one from init scripts one inserted by code 
			try (PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) as CNT FROM PERSON")) {
				ResultSet rs = stmt.executeQuery();
				assertTrue(rs.next());
				assertEquals("Another row appeared", 2, rs.getInt("cnt"));
			}
			
			// opening connection with initialization script
			// data is applied on current DB state (if DDL script was present, same table will tempt to be created)
			try (Connection anotherCon = DriverManager.getConnection("jdbc:h2:mem:t1;INIT=RUNSCRIPT FROM 'classpath:insert.sql'")) {
				try (PreparedStatement stmt = anotherCon.prepareStatement("SELECT COUNT(*) as CNT FROM PERSON")) {
					ResultSet rs = stmt.executeQuery();
					assertTrue(rs.next());
					assertEquals("Insert script is applied on actual state so third row added.", 3, rs.getInt("cnt"));
				}
			}
		}
	}
}
