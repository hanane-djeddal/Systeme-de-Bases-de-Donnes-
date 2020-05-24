import java.sql.*;

public class Ex3 {
	// Declaration des variables
	static final String DB_URL1 = "jdbc:h2:tcp://localhost:9093/~/base1"; //1ere base : Ens
	static final String DB_URL2 = "jdbc:h2:tcp://localhost:9094/~/base2"; //2ieme base : Module
	static final String USER = "moi";
  static final String PASS = "";

public static void main(String[] args){
	// Declaration des variables
	Connection conn1 = null;
	Connection conn2= null;
  Statement stmt = null;
  Statement stmt2 = null;
	
	try{
		// Pilote
		Class.forName("org.h2.Driver");

		System.out.println("Connecting to database...");
		// 1ere Connexion	
    conn1 = DriverManager.getConnection(DB_URL1,USER,PASS);
		// 2ieme Connexion	
    conn2 = DriverManager.getConnection(DB_URL2,USER,PASS);

		System.out.println("Creating statement...");
    stmt = conn1.createStatement();
    stmt2 = conn2.createStatement();
    String sql;
    sql = "SELECT * FROM Ens ORDER BY id_ens";
		long tempsDebut = System.currentTimeMillis();
    ResultSet rs = stmt.executeQuery(sql);  //Requete sur la 1ere base : Trier Ens 
		sql="SELECT * FROM Module ORDER BY ens";
		ResultSet rs2 = stmt2.executeQuery(sql); //Requete sur la 2ieme base : Trier Module
		System.out.println("ID_ENS  NOM    PRENOM    ID_MODULE  NOM_MODULE");
		boolean tmp1=rs.next(); //tmp1 : si la table Ens est vide
		boolean tmp2=rs2.next(); //tmp2 : si la table Module est vide
		while(tmp1 && tmp2){
			int id_ens= rs.getInt("id_ens"); 
			String nom = rs.getString("nom");
      String prenom = rs.getString("prenom");

			int ens= rs2.getInt("ens");
			while(tmp2  && ens == id_ens){  //recuperer toutes les valeurs de Module.ens = Ens.id_ens
				int id_mod= rs2.getInt("id_module");
				String module = rs2.getString("nom_module");
				System.out.print(id_ens + "       ");
				System.out.print(nom + "   ");
      	System.out.print(prenom + "   ");
      	System.out.print(id_mod + "          ");
      	System.out.println(module);
				if(tmp2=rs2.next()){
					ens= rs2.getInt("ens");
				}
			}
			if(tmp2){  //Passer à la valeur suivante de Ens.id_ens
				while(rs.next() && rs.getInt("id_ens")<ens){
				}
			}
		}
		long tempsFin = System.currentTimeMillis();
    float seconds = (tempsFin - tempsDebut) / 1000F;
		System.out.println("Jointure effectuée en: "+ Float.toString(seconds) + " secondes.");
		rs.close();
		rs2.close();
    stmt.close();
    stmt2.close();
    conn1.close();
    conn2.close();
	}
	catch(SQLException se){
		se.printStackTrace();
	}
	catch (Exception e){
		e.printStackTrace();
	}
	
	}
}
