import java.sql.*;

public class Ex2 {
	// Declaration des variables
	static final String DB_URL = "jdbc:h2:tcp://localhost:9093/~/base1";
	static final String USER = "moi";
  static final String PASS = "";

public static void main(String[] args){
	// Declaration des variables
	Connection conn = null;
  Statement stmt = null;
	ResultSet rs2 = null;
	
	try{
		// Pilote
		Class.forName("org.h2.Driver");

		// Connexion		
		System.out.println("Connecting to database...");
    conn = DriverManager.getConnection(DB_URL,USER,PASS);

		System.out.println("Creating statement...");
    stmt = conn.createStatement();
    String sql;
    sql = "SELECT * FROM Ens";
		long tempsDebut = System.currentTimeMillis();
    ResultSet rs = stmt.executeQuery(sql);

		// Requete parametree
		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM Module WHERE ens =? ");
		System.out.println("ID_ENS  NOM    PRENOM    ID_MODULE  NOM_MODULE");
		while(rs.next()){
			int id_ens= rs.getInt("id_ens");
			String nom = rs.getString("nom");
      String prenom = rs.getString("prenom");
			pstmt.setInt(1,id_ens);
			// Execution de la requete parametree pour la valeur de id_ens
			rs2=pstmt.executeQuery();
			while(rs2.next()){
				int id_mod= rs2.getInt("id_module");
				String module = rs2.getString("nom_module");
				System.out.print(id_ens + "       ");
				System.out.print(nom + "   ");
      	System.out.print(prenom + "   ");
      	System.out.print(id_mod + "          ");
      	System.out.println(module);
			}
		}		
		long tempsFin = System.currentTimeMillis();
    float seconds = (tempsFin - tempsDebut) / 1000F;
		System.out.println("Jointure effectuée en: "+ Float.toString(seconds) + " secondes.");
		rs.close();
		rs2.close();
    stmt.close();
    pstmt.close();
    conn.close();
	}
	catch(SQLException se){
		se.printStackTrace();
	}
	catch (Exception e){
		e.printStackTrace();
	}
	
	}
}
