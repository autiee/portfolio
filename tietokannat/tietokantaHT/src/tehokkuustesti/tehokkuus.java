package tehokkuustesti;

import java.sql.*;
import java.util.*;

public class tehokkuus {
	
	 public static void createNewTable(String url) {	        	        
	        String sql = "CREATE TABLE IF NOT EXISTS Elokuvat (\n"
	                + "	nimi TEXT NOT NULL,\n"
	                + "	vuosi INTEGER \n"
	                + ");";
	        
	        try (Connection conn = DriverManager.getConnection(url);
	            Statement stmt = conn.createStatement()) {
	            stmt.execute(sql);
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        
	    }
	 
	 
	 static String getString(int n) 
	    { 
	  
	        // chose a Character random from this String 
	        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	                                    + "0123456789"
	                                    + "abcdefghijklmnopqrstuvxyz"; 
	  
	        // create StringBuffer size of AlphaNumericString 
	        StringBuilder sb = new StringBuilder(n); 
	  
	        for (int i = 0; i < n; i++) { 
	  
	            // generate a random number between 
	            // 0 to AlphaNumericString variable length 
	            int index 
	                = (int)(AlphaNumericString.length() 
	                        * Math.random()); 
	  
	            // add Character one by one in end of sb 
	            sb.append(AlphaNumericString 
	                          .charAt(index)); 
	        } 
	  
	        return sb.toString(); 
	    } 
	 
	 
	 public static void rivit(String url) {
		 try (Connection conn = DriverManager.getConnection(url)) {
	        	Statement stmt = conn.createStatement();
	            String x = "DELETE FROM Elokuvat";
	            stmt.executeUpdate(x);
	        	PreparedStatement p = conn.prepareStatement("BEGIN TRANSACTION");
	        	p.execute();	        	  
	        	for(int i = 1; i <= 1000000; i++) {
	          
	        		String query = " insert into Elokuvat (nimi, vuosi)"
	        		+ " values (?, ?)";

	        		PreparedStatement preparedStmt = conn.prepareStatement(query);
	        		preparedStmt.setString (1, getString(10));
	        		preparedStmt.setInt    (2, new Random().nextInt(100 + 1) + 1900);

	        		preparedStmt.execute();
	        	}
	        	
	        	PreparedStatement c = conn.prepareStatement("COMMIT");
	        	c.execute();
	        	
	        	} catch (Exception e) {
	        		System.err.println("Got an exception!");
	        		System.err.println(e.getMessage());
	        	}
	 }
	 
	 
	 public static void kysely(String url) {
		 	
		 try (Connection conn = DriverManager.getConnection(url)) {
		            
		 PreparedStatement ps = conn.prepareStatement("CREATE INDEX idx_vuosi ON Elokuvat (vuosi);");
	     ps.execute();
		 for(int i = 1; i <= 1000; i++) {
	          
     		int vuosi = new Random().nextInt(100 + 1) + 1900;

     		PreparedStatement preparedStmt = conn.prepareStatement("SELECT COUNT(*) FROM Elokuvat WHERE vuosi=?");
     		preparedStmt.setInt(1,vuosi);

     		preparedStmt.execute();
     		}
		 }
		 catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	 }
	 
	 
	 public static void main(String[] args) {
		 String url = "jdbc:sqlite:C://kurssit/tietokannat/tietokantaHT/tehokkuus.db";   
		 
		 long tableStartTime = System.nanoTime();
		 createNewTable(url);
		 long tableEndTime = System.nanoTime();
		 System.out.println((tableEndTime - tableStartTime)/1000000000.0);
		 long rivitStartTime = System.nanoTime();
		 rivit(url);
		 long rivitEndTime = System.nanoTime();
		 System.out.println((rivitEndTime - rivitStartTime)/1000000000.0);
		 long kyselyStartTime = System.nanoTime();
		 kysely(url);
		 long kyselyEndTime = System.nanoTime();
		 System.out.println((kyselyEndTime - kyselyStartTime)/1000000000.0);
		 System.out.println("valmis");
	 }
}