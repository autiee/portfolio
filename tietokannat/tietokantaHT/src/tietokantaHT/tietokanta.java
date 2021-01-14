package tietokantaHT;

import java.sql.*;
import java.util.*;

public class tietokanta {
	
	
	public static void main(String[] args) throws SQLException {
		Connection db = DriverManager.getConnection("jdbc:sqlite:C://kurssit/tietokannat/tietokantaHT/kurssit.db");
        toiminto(db);
	}
	
	
	public static void toiminto(Connection db) throws SQLException {
		Scanner input = new Scanner(System.in);
        System.out.println("Valitse toiminto:");
        String toiminto = input.nextLine();
        int i = Integer.parseInt(toiminto);
        
        if(i == 1) vuosi(db);
        else if(i == 2) opiskelija(db);
        else if(i == 3) kurssi(db);
        else if(i == 4) opettajat(db);
        else toiminto(db);
	}
	
	
	public static void vuosi(Connection db) throws SQLException {
		Scanner input = new Scanner(System.in);
        System.out.println("Anna vuosi:");
        String op = input.nextLine();
        
        PreparedStatement p = db.prepareStatement("SELECT SUM(K.laajuus) FROM Kurssit K, Suoritukset S WHERE K.id = S.kurssi_id AND strftime('%Y',S.paivays) = ?");
        p.setString(1,op);

        ResultSet r = p.executeQuery();
        if (r.next()) {
        	if(r.getFloat(1) == 0) System.out.println("Vuotta ei löytynyt");
        	else System.out.println("Opintopisteiden määrä: "+r.getFloat(1));
        }
        toiminto(db);
	}
	
	
	public static void opiskelija(Connection db) throws SQLException {
		Scanner input = new Scanner(System.in);
        System.out.println("Anna opiskelijan nimi:");
        String tiedot = input.nextLine();

        PreparedStatement p = db.prepareStatement("SELECT K.nimi AS 'kurssi', K.laajuus AS 'op', S.paivays AS 'päiväys', S.arvosana FROM Opiskelijat O, Kurssit K, Suoritukset S WHERE K.id = S.kurssi_id AND O.id = S.opiskelija_id AND O.nimi=?");
        p.setString(1,tiedot);

        ResultSet r = p.executeQuery();
        
        ResultSetMetaData rsmd = r.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        if(r.next()) {
        	for (int i = 1; i <= columnsNumber; i++) {
            	if (i > 1) System.out.print("    ");
            	System.out.print(rsmd.getColumnName(i));
        	}
        	System.out.println("");
        	while (r.next()) {
            	for (int i = 1; i <= columnsNumber; i++) {
                	if (i > 1) System.out.print("   ");
                	String columnValue = r.getString(i);
                	System.out.print(columnValue);
            	}
            	System.out.println("");
        	}
        }
        else {
            System.out.println("Opiskelijaa ei löytynyt");
        }
        toiminto(db);

        
	}
	
	
	public static void kurssi(Connection db) throws SQLException {
		Scanner input = new Scanner(System.in);
        System.out.println("Anna kurssin nimi:");
        String kurssi = input.nextLine();

        PreparedStatement p = db.prepareStatement("SELECT AVG(S.arvosana) FROM Kurssit K, Suoritukset S WHERE K.id = S.kurssi_id AND K.nimi=?");
        p.setString(1,kurssi);

        ResultSet r = p.executeQuery();
        if (r.next()) {
        	if(r.getFloat(1) == 0) System.out.println("Kurssia ei löytynyt"); 
        	else System.out.println("Keskiarvo: "+r.getFloat(1));
        } 
        toiminto(db);
	}
	
	
	public static void opettajat(Connection db) throws SQLException {
		Scanner input = new Scanner(System.in);
        System.out.println("Anna opettajien määrä:");
        String maara = input.nextLine();
        int maaraInt = Integer.parseInt(maara);
        maaraInt++;
        maara =  Integer.toString(maaraInt);

        PreparedStatement p = db.prepareStatement("SELECT O.nimi AS 'opettaja', SUM(K.laajuus) AS 'op' FROM Opettajat O, Kurssit K, Suoritukset S WHERE O.id = K.opettaja_id AND K.id = S.kurssi_id GROUP BY O.id ORDER BY op DESC LIMIT ?");
        p.setString(1,maara);

        ResultSet r = p.executeQuery();
        ResultSetMetaData rsmd = r.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        if(r.next()) {
        	for (int i = 1; i <= columnsNumber; i++) {
            	if (i > 1) System.out.print("    ");
            	System.out.print(rsmd.getColumnName(i));
        	}
        	System.out.println("");
        	while (r.next()) {
            	for (int i = 1; i <= columnsNumber; i++) {
                	if (i > 1) System.out.print("   ");
                	String columnValue = r.getString(i);
                	System.out.print(columnValue);
            	}
            	System.out.println("");
        	}
        }
        toiminto(db);
	}
}
