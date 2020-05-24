import java.sql.*;

public class Ex1 {
	// Declaration des variables
	static final String DB_URL = "jdbc:h2:tcp://localhost:9093/~/base1";
	static final String USER = "moi";
  static final String PASS = "";

public static void main(String[] args){
	// Declaration des variables
	Connection conn = null;
  Statement stmt = null;
	try{
		// Pilote
		Class.forName("org.h2.Driver");

		// Connexion	
		System.out.println("Connecting to database...");
    conn = DriverManager.getConnection(DB_URL,USER,PASS);

		//Requete		
		System.out.println("Creating statement...");
    stmt = conn.createStatement();
    String sql;
    sql = "SELECT * FROM Ens";
    ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()){
			String nom = rs.getString("nom");
      String prenom = rs.getString("prenom");
		  System.out.print("Nom : " + nom);
      System.out.println(", Prenom: " + prenom);
		}
		rs.close();
    stmt.close();
    conn.close();
	}
	catch (Exception e){
		e.printStackTrace();
	}
	
	}
}
