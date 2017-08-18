package org.Tp1;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.Tp1.BD.DataBaseConnexion;
import org.Tp1.entities.Membre;
import org.Tp1.entities.Tache;

public class ApplicationTP1 {

	private static Scanner sc = new  Scanner(System.in);
	private static DataBaseConnexion baseDeDonnees = new DataBaseConnexion();
	private  static int entree=0;

	
	//Methode main
	public static void main(String[] args) throws SQLException {		
		accueil();		
		while(true){
			menuPrincipal();
			vericationDeLaSaisie(1,3);			
			switch(entree){
				case 1:
					entree=0;
					menuTache();
					vericationDeLaSaisie(1,6);
						switch(entree){
							case 1 :
								//creerTache
								creerTache();
								break;
							case 2 :
								//modifierTache
								//modifierTache();
								break;
							case 3 :
								//supprimerTache
								//supprimerTache();
								break;
							case 4 :
								//assignerTache
								//assignerTache();
								break;
							case 5 :
								rechercherEtAfficherTaches();
								break;				
						}					
					break;
				case 2 :
					entree=0;
					menuMembre();
					vericationDeLaSaisie(1,5);
						switch(entree){
							case 1 :
								//creerMembre
								creerMembre();
								break;
							case 2 :
								//modifierMembre();
								break;
							case 3 :
								//supprimerMembre();
								break;
							case 4 :
								rechercherEtAfficherMembres();
								break;		
						}	
					break;
				case 3 :
					mettreFinAuProgramme();
					break;	
			}
		}		
	}
	
	public static void accueil(){
		System.out.println("\n\n===============================================");
		System.out.println("!!! MODULE DE GENIE LOGICIEL AVANCE!!!\n\n");
		System.out.println(" TP1.  MEDOU DANIEL MAGLOIRE\n");
		System.out.println("===============================================\n\n");
	}

	public static void menuPrincipal(){
		System.out.print("\n\n\t\tMENU PRINCIPAL DE GESTACHE\n");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		System.out.print("1 : GESTION DES TACHES\n");
		System.out.print("2 : GESTION DES MEMBRES\n");
		System.out.print("3 : FIN\n");
		System.out.print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\nChoix : ");
	}
	
	public static void menuTache(){
		System.out.print("\n\n\t\tTACHES\n");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		System.out.print("1 : CREER TACHE\n");
		System.out.print("2 : MODIFIER TACHE\n");
		System.out.print("3 : SUPPRIMER TACHE\n");
		System.out.print("4 : ASSIGNER TACHE\n");
		System.out.print("5 : AFFICHER TACHE\n");
		System.out.print("6 : SORTIR\n");
		System.out.print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\nChoix : ");
	}
	
	public static void menuMembre(){
		System.out.print("\n\n\t\tMEMBRE\n");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		System.out.print("1 : CREER MEMBRE\n");
		System.out.print("2 : MODIFIER MEMBRE\n");
		System.out.print("3 : SUPPRIMER MEMBRE\n");
		System.out.print("4 : AFFICHER MEMBRE\n");
		System.out.print("5 : SORTIR\n");
		System.out.print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\nChoix : ");
	}	
	
	public static void vericationDeLaSaisie(int borneInferieure, int borneSuperieure){
		while(entree<borneInferieure || entree>borneSuperieure){
			verificationTypeDeDonnee();
			entree=sc.nextInt();
			if(entree<borneInferieure || entree>borneSuperieure){
				System.out.println("Entrez un numero entre "+borneInferieure+" et "+borneSuperieure+"\n");
			}
		}		
	}
	
	//Fonction de verification de type 
	public static void verificationTypeDeDonnee(){
		if(!sc.hasNextInt()){
			System.out.print("Saisir votre numero d'operation !!!\nChoix :");
			sc.nextLine();
			verificationTypeDeDonnee();
		}
	}
	
	public static void mettreFinAuProgramme(){
		System.out.println("\nFermeture du programme !\n");
		System.exit(0);
	}
	
	public static void creerTache() throws SQLException{
		int idMembre=0;
		System.out.print("\n\n~~~CREATION DE TACHE~~~\n\n");
		System.out.print("NOM DE LA TACHE : \n");
		sc.nextLine();
		String nom=sc.nextLine();
		System.out.print("DESCRIPTION DE LA TACHE : \n");
		System.out.println("nom Tache" + nom);
		String description=sc.nextLine();
		System.out.print("STATUT DE LA TACHE : \n");
		String statut=sc.nextLine();
		System.out.print("Voulez-vous assigner cette nouvelle tache a un membre?\t1 : OUI\t2 : NON\n");
		vericationDeLaSaisie(1,2);
		entree=sc.nextInt();		
		if(entree==1){
			List<Membre> membres = baseDeDonnees.afficherMembres();
			afficherMembres(membres);
			System.out.print("Entrez l'identifiant du tache :\n");
			verificationTypeDeDonnee();
			idMembre=sc.nextInt();		
		}
		Tache tache = new Tache(0,nom,description,statut,idMembre);
		System.out.println("Voulez-vous ajouter la tache nouvellement cree\n1 : OUI\t2 : NON");
		entree=sc.nextInt();
		vericationDeLaSaisie(1,2);
		if(entree==1){
			baseDeDonnees.creerTache(tache);
		}
		entree=0;
	}
	
	public static void creerMembre() throws SQLException{
		int idMembre=0;
		System.out.print("\n\n~~~CREATION DE MEMBRE~~~\n\n");
		sc.nextLine();
		System.out.print("NOM DU MEMBRE : \n");
		String nom=sc.nextLine();		
		Membre membre = new Membre(0,nom);
		System.out.println("Voulez-vous ajouter le membre nouvellement cree\n1 : OUI\t2 : NON");
		entree=sc.nextInt();
		vericationDeLaSaisie(1,2);
		if(entree==1){
			baseDeDonnees.creerMembre(membre);
		}
		entree=0;
	}
	
	public static void afficherMembres(List<Membre> liste){
		System.out.print("\nListe de tous les membres existants dans la base de donnees\n");
		for(Membre membre : liste){
			System.out.print("\n\nID : " + membre.getId_Membre() + " \n");
			System.out.println("NOM : " + membre.getNomMembre() + " \n");
		}		
	}
	
	public static void afficherTaches(List<Tache> liste){
		System.out.print("\nListe de toutes les taches existants dans la base de donnees\n");
		for(Tache tache : liste){
			System.out.print("\n\nID : " + tache.getId_Tache() + " \n");
			System.out.print("NOM : " + tache.getNomTache() + " \n");
			System.out.print("DESCRIPTION : " + tache.getDesription() + " \n");
			System.out.print("STATUT : " + tache.getStatut() + " \n");
			System.out.print("ID MEMBRE : " + tache.getId_Membre() + " \n");
		}		
	}
	
	public static List<Membre> rechercherMembres() throws SQLException{
		List<Membre> liste=baseDeDonnees.afficherMembres();
		return  liste;
	}
		
	public static List<Tache> rechercherTaches() throws SQLException{
		List<Tache> liste=baseDeDonnees.afficherTaches();
		return liste;
	}
	
	public static void rechercherEtAfficherTaches() throws SQLException{
		List<Tache> liste=rechercherTaches();
		afficherTaches(liste);
	}
	
	public static void rechercherEtAfficherMembres() throws SQLException{
		List<Membre> liste=rechercherMembres();
		afficherMembres(liste);
	}
}
