/**
 *  Author: MEDOU Daniel Magloire
 *  Unité d'enseignement: GENIE LOGICIEL AVANCE
 *  Ecole: IFI
 *  Niveau: Master 1
 *  Option: Systèmes Intelligents et Multimédia (SIM)
 *  Description: TP1, Conception et Réalisation d'une Application de Gestion de Tâches: "GESTACHE"
 */

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
		Connection connection = null;
		Statement statement = null;
		ResultSet result=null;

		//Constructeur de la classe permettant d'initialiser, d'instancier et de creer une connexion a la base de donnees 
		public DataBaseConnexion() {		
			boolean fichierExiste=false;
			File file = new File("GESTACHE.DB"); 
			
			//Test de l'existence de la base
			if(file.exists()){
			   fichierExiste=true;
			}
			
			try {
				Class.forName("org.sqlite.JDBC");
				connection = DriverManager.getConnection("jdbc:sqlite:" + "GESTACHE.DB");
				connection.setAutoCommit(true);

				statement = connection.createStatement();
				
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
				statement.executeUpdate(sql);

				sql="CREATE TABLE MEMBRE " +
						"(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," +
						" NOM TEXT NOT NULL)";					
				statement.executeUpdate(sql);

			} catch ( Exception e ) {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				System.exit(0);
			}
			System.out.println("BRAVO LES TABLES TACHE et MEMBRE CREES AVEC SUCCES\n");
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
				statement.executeUpdate("INSERT INTO TACHE (NOM, DESCRIPTION, STATUS, ID_MEMBRE) " + 
						"VALUES ('" + tache.getNomTache()+"','"+ tache.getDesription() +"','" + status + "'," + id_Membre + ");");
				System.out.println("\nTache creee et enregistree avec succes\n");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//Methode permettant de creer un membre
		public void creerMembre(Membre membre) {		
			try {
				statement.executeUpdate("INSERT INTO MEMBRE (NOM) " + 
						"VALUES ('" + membre.getNomMembre()+"');");
				System.out.println("\nMembre cree et enregistre avec succes\n");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//Methode permettant d'afficher les taches assignees a un membre
		public List<Tache> afficherTachesMembre(int identifiant) throws SQLException {
			result = statement.executeQuery("SELECT * FROM TACHE WHERE ID_MEMBRE = " + identifiant + ";");
			List<Tache> listeTachesmembre = new ArrayList<Tache>();

			while (result.next()) {
				int id = result.getInt("ID");
				String name = result.getString("NOM");
				String description = result.getString("DESCRIPTION");
				String status = result.getString("STATUS");
				int idMembre = result.getInt("ID_MEMBRE");
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
			result = statement.executeQuery("SELECT T.ID AS tacheID, T.NOM AS tacheNOM, T.DESCRIPTION AS tacheDESCRIPTION,"
					+ "T.STATUS AS tacheSTATUS, M.ID AS membreID, M.NOM AS membreNOM  FROM TACHE AS T "
					+ "LEFT JOIN MEMBRE AS M  ON T.ID_MEMBRE=M.ID WHERE T.STATUS = '"+stringStatus+"';");

			while (result.next()) {
				//Creation objet tache
				int id = result.getInt("tacheID");
				String name = result.getString("tacheNOM");
				String description = result.getString("tacheDESCRIPTION");
				String status = result.getString("tacheSTATUS");

				//Creation objet membre
				int idMembre = result.getInt("membreID");
				String nomMembre=result.getString("membreNOM");

				temporaire[0]=new Tache(id, name, description, status, idMembre);
				temporaire[1]=new Membre(idMembre,nomMembre);
				listeTachesStatus.add(temporaire);
			}
			return listeTachesStatus;
		}

		// Methode permettant de recuperer et d'afficher tous les membres
		public List<Membre> afficherMembres() throws SQLException {
			result = statement.executeQuery("SELECT * FROM MEMBRE;");
			List<Membre> listeMembres = new ArrayList<Membre>();
			while (result.next()) {
				int id = result.getInt("ID");
				String name = result.getString("NOM");
				listeMembres.add(new Membre(id, name));
			}
			return listeMembres;
		}

		// Methode permettant de recuperer et d'afficher toutes les taches
		public List<Tache> afficherTaches() throws SQLException {
			result = statement.executeQuery("SELECT * FROM TACHE;");
			List<Tache> listeTaches = new ArrayList<Tache>();

			while (result.next()) {
				int id = result.getInt("ID");
				String name = result.getString("NOM");
				String description = result.getString("DESCRIPTION");
				String status = result.getString("STATUS");
				int idMembre = result.getInt("ID_MEMBRE");
				listeTaches.add(new Tache(id, name, description, status, idMembre));
			}
			return listeTaches;
		}

		//Modifications
		public void modifierTache(Tache tache) {			
			try {
				statement.executeUpdate("UPDATE TACHE SET NOM='"+tache.getNomTache()+"',DESCRIPTION='"+tache.getDesription()+
						"',STATUS='"+tache.getStatut()+"',ID_MEMBRE='"+tache.getId_Membre()+
						"' WHERE ID='"+tache.getId_Tache()+"';"); 

				System.out.println("\nMise a jour de tache a ete bien faite et enregistree avec succes\n");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public void modifierMembre(Membre membre) {					
			try {
				statement.executeUpdate("UPDATE MEMBRE SET NOM='"+membre.getNomMembre()+
						"' WHERE ID='"+membre.getId_Membre()+"';"); 

				System.out.println("\nMise a jour du membre a ete bien faite et enregistree avec succes\n");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//Methode d'assignation de tache
		public void assignerTacheAUnMembre(int idTache, int idMembre){	
			try {
				statement.executeUpdate("UPDATE TACHE SET ID_MEMBRE='"+idMembre
						+"'WHERE ID='"+idTache+"';"); 

				System.out.println("\nTache assignee et enregistree avec succes\n");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 

		//Suppressions
		public void supprimerTache(int idTache) {
			String sql = "DELETE FROM TACHE WHERE ID="+ idTache + ";";
			try {
				statement.executeUpdate(sql);
				System.out.println("\nTache supprimee avec succes\n");
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		public void supprimerMembre(int idMembre) {
			String sql = "DELETE FROM MEMBRE WHERE ID="+ idMembre + ";";
			try {
				statement.executeUpdate(sql);
				sql="UPDATE TACHE SET ID_MEMBRE = NULL WHERE ID_MEMBRE=0;";
				statement.executeUpdate(sql);
				System.out.println("\nMembre supprime avec succes\n");
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		// Methode permettant de fermer et liberer les ressources
		public void fermerRessourceBaseDeDonnees() {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Erreur de fermeture des ressources de la base de donnees\n\n");
			}
		}
}
