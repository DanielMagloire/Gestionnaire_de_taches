package org.Tp1.BD;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.Tp1.entities.Membre;
import org.Tp1.entities.Tache;

public class DataBaseConnexion {

	//Variables representant les ressources manipulees
		Connection c = null;
		Statement stmt = null;
		ResultSet rs=null;

		//Constructeur de la classe permettant d'initialiser, d'instancier et de creer une connexion a la base de donnees 
		public DataBaseConnexion() {		
			boolean fichierExiste=false;
			File f1 = new File("GESTACHE.DB"); 
			//Test de l'existence de la base
			if(f1.exists()){
			   fichierExiste=true;
			}
			
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:" + "GESTACHE.DB");
				c.setAutoCommit(true);

				stmt = c.createStatement();
				
				if(!fichierExiste){
					creerTablesDansLaBaseDeDonnees();
				}
				//System.out.println("\nBase de donnees cree avec success\n");
				
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
		}

		
		//Methode de creation des tables dans la base de donnees
		public void creerTablesDansLaBaseDeDonnees(){

			String sql;

			try {
				sql = "CREATE TABLE TACHE " +
						"(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," +
						" NOM TEXT NOT NULL," +
						"DESCRIPTION TEXT NOT NULL,"+
						"STATUS TEXT NOT NULL," +
						"ID_MEMBRE INTEGER NULL)";					
				stmt.executeUpdate(sql);

				sql="CREATE TABLE MEMBRE " +
						"(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," +
						" NOM TEXT NOT NULL)";					
				stmt.executeUpdate(sql);

			} catch ( Exception e ) {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				System.exit(0);
			}
			System.out.println("Creation des tables TACHE et MEMBRE reussie\n");
		}

		//Methode permettant de creer une tache
		public void creerTache(Tache tache) {
			int id_Membre=tache.getId_Membre();
			String status="";

			if( id_Membre==0 || !(id_Membre > 0)){
				status="nouveau";
			}else{
				status="en-progres";
			}

			try {
				stmt.executeUpdate("INSERT INTO TACHE (NOM, DESCRIPTION, STATUS, ID_MEMBRE) " + 
						"VALUES ('" + tache.getNomTache()+"','"+ tache.getDesription() +"','" + status + "'," + id_Membre + ");");
				System.out.println("\nTache enregistree\n");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//Methode permettant de creer un membre
		public void creerMembre(Membre membre) {		
			try {
				stmt.executeUpdate("INSERT INTO MEMBRE (NOM) " + 
						"VALUES ('" + membre.getNomMembre()+"');");
				System.out.println("\nMembre enregistre\n");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//Methode permettant d'afficher les taches assignees a un membre
		public List<Tache> afficherTachesMembre(int identifiant) throws SQLException {
			rs = stmt.executeQuery("SELECT * FROM TACHE WHERE ID_MEMBRE = " + identifiant + ";");
			List<Tache> listeTachesmembre = new ArrayList<Tache>();

			while (rs.next()) {
				int id = rs.getInt("ID");
				String name = rs.getString("NOM");
				String description = rs.getString("DESCRIPTION");
				String status = rs.getString("STATUS");
				int idMembre = rs.getInt("ID_MEMBRE");
				listeTachesmembre.add(new Tache(id, name, description, status, idMembre));
			}
			return listeTachesmembre;
		}

		//Methode permettant d'afficher toutes les taches selon leurs status et les infos du membre associe
		public List<Object[]> afficherStatusTaches(int status_a_chercher) throws SQLException {

			List<Object[]> listeTachesStatus = new ArrayList<Object[]>();	
			//List temporaire=new ArrayList();
			Object[] temporaire =new Object[2];
			String stringStatus;

			if (status_a_chercher == 1) {
				stringStatus = "nouveau";
			} else if (status_a_chercher == 2) {
				stringStatus = "en-progres";
			} else {
				stringStatus = "termine";
			}
			rs = stmt.executeQuery("SELECT T.ID AS tacheID, T.NOM AS tacheNOM, T.DESCRIPTION AS tacheDESCRIPTION,"
					+ "T.STATUS AS tacheSTATUS, M.ID AS membreID, M.NOM AS membreNOM  FROM TACHE AS T "
					+ "INNER JOIN MEMBRE AS M  ON T.ID_MEMBRE=M.ID WHERE T.STATUS = '"+stringStatus+"';");

			while (rs.next()) {
				//Creation objet tache
				int id = rs.getInt("tacheID");
				String name = rs.getString("tacheNOM");
				String description = rs.getString("tacheDESCRIPTION");
				String status = rs.getString("tacheSTATUS");

				//Creation objet membre
				int idMembre = rs.getInt("membreID");
				String nomMembre=rs.getString("membreNOM");

				temporaire[0]=new Tache(id, name, description, status, idMembre);
				temporaire[1]=new Membre(idMembre,nomMembre);
				listeTachesStatus.add(temporaire);
			}
			return listeTachesStatus;
		}

		// Methode permettant de recuperer et d'afficher tous les membres
		public List<Membre> afficherMembres() throws SQLException {
			rs = stmt.executeQuery("SELECT * FROM MEMBRE;");
			List<Membre> listeMembres = new ArrayList<Membre>();
			while (rs.next()) {
				int id = rs.getInt("ID");
				String name = rs.getString("NOM");
				listeMembres.add(new Membre(id, name));
			}
			return listeMembres;
		}

		// Methode permettant de recuperer et d'afficher toutes les taches
		public List<Tache> afficherTaches() throws SQLException {
			rs = stmt.executeQuery("SELECT * FROM TACHE;");
			List<Tache> listeTaches = new ArrayList<Tache>();

			while (rs.next()) {
				int id = rs.getInt("ID");
				String name = rs.getString("NOM");
				String description = rs.getString("DESCRIPTION");
				String status = rs.getString("STATUS");
				int idMembre = rs.getInt("ID_MEMBRE");
				listeTaches.add(new Tache(id, name, description, status, idMembre));
			}
			return listeTaches;
		}

		//Modifications
		public void modifierTache(Tache tache) {			
			try {
				stmt.executeUpdate("UPDATE TACHE SET NOM='"+tache.getNomTache()+"',DESCRIPTION='"+tache.getDesription()+
						"',STATUS='"+tache.getStatut()+"',ID_MEMBRE='"+tache.getId_Membre()+
						"' WHERE ID='"+tache.getId_Tache()+"';"); 

				System.out.println("\nTache modifie\n");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public void modifierMembre(Membre membre) {					
			try {
				stmt.executeUpdate("UPDATE MEMBRE SET NOM='"+membre.getNomMembre()+
						"' WHERE ID='"+membre.getId_Membre()+"';"); 

				System.out.println("\nMembre modifie\n");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//Methode d'assignation de tache
		public void assignerTacheAUnMembre(int idTache, int idMembre){	
			try {
				stmt.executeUpdate("UPDATE TACHE SET ID_MEMBRE='"+idMembre
						+"'WHERE ID='"+idTache+"';"); 

				System.out.println("\nTache assignee\n");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 

		//Suppressions
		public void supprimerTache(int idTache) {
			String sql = "DELETE FROM TACHE WHERE ID="+ idTache + ";";
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("\nTache supprimee\n");
		}

		public void supprimerMembre(int idMembre) {
			String sql = "DELETE FROM MEMBRE WHERE ID="+ idMembre + ";";
			try {
				stmt.executeUpdate(sql);
				sql="UPDATE TACHE SET ID_MEMBRE = NULL WHERE ID_MEMBRE=" + idMembre +";";
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("\nMembre supprime\n");
		}

		// Methode permettant de fermer et liberer les ressources
		public void fermerRessourceBaseDeDonnees() {
			try {
				stmt.close();
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Erreur de fermeture des ressources de la base de donnees\n\n");
			}
		}
}
