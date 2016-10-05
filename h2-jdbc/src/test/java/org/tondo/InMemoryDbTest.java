package org.tondo;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
