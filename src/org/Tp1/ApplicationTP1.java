/**
 *  Author: MEDOU Daniel Magloire
 *  Unité d'enseignement: GENIE LOGICIEL AVANCE
 *  Ecole: IFI
 *  Niveau: Master 1
 *  Option: Systèmes Intelligents et Multimédia (SIM)
 *  Description: TP1, Conception et Réalisation d'une Application de Gestion de Tâches: "GESTACHE"
 */

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
			entree=0;
			vericationDeLaSaisie(1,3);			
			switch(entree){
				case 1:
					entree=0;
					menuTache();
					vericationDeLaSaisie(1,8);
						switch(entree){
							case 1 :
								//creerTache
								creerTache();
								break;
							case 2 :
								//modifierTache
								modifierTache();
								break;
							case 3 :
								//supprimerTache
								supprimerTache();
								break;
							case 4 :
								//assignerTache
								assignerTache();
								break;
							case 5 :
								//afficherTaches
								rechercherEtAfficherTaches();
								break;
							case 6 :
								//
								afficherTachesMembreParID();
								break;	
							case 7 :
								//
								afficherTachesSelonStatut();
								break;	
							case 8 :
								System.out.println("\n!!! Action annullee. Retour au menu principal !!!");
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
								modifierMembre();
								break;
							case 3 :
								supprimerMembre();
								break;
							case 4 :
								rechercherEtAfficherMembres();
								break;		
							case 5 :
								System.out.println("\n!!! Action annullee. Retour au menu principal !!!");
								break;
						}	
					break;
				case 3 :
					mettreFinAuProgramme();
					break;	
			}
		}		
	}
	
	private static void accueil(){
		System.out.println("\n\n===============================================");
		System.out.println("!!! MODULE DE GENIE LOGICIEL AVANCE!!!\n\n");
		System.out.println(" TP1.  MEDOU DANIEL MAGLOIRE\n");
		System.out.println("===============================================\n\n");
	}

	private static void menuPrincipal(){
		System.out.print("\n\n\t\tMENU PRINCIPAL DE GESTACHE\n");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		System.out.print("1 : GESTION DES TACHES\n");
		System.out.print("2 : GESTION DES MEMBRES\n");
		System.out.print("3 : FIN\n");
		System.out.print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\nChoix : ");
	}
	
	private static void menuTache(){
		System.out.print("\n\n\t\tTACHES\n");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		System.out.print("1 : CREER TACHE\n");
		System.out.print("2 : MODIFIER TACHE\n");
		System.out.print("3 : SUPPRIMER TACHE\n");
		System.out.print("4 : ASSIGNER TACHE\n");
		System.out.print("5 : AFFICHER TACHES\n");
		System.out.print("6 : AFFICHER TACHES MEMBRE PAR SON ID\n");
		System.out.print("7 : AFFICHER TACHES PAR STATUT\n");
		System.out.print("8 : SORTIR\n");
		System.out.print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\nChoix : ");
	}
	
	private static void menuMembre(){
		System.out.print("\n\n\t\tMEMBRE\n");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		System.out.print("1 : CREER MEMBRE\n");
		System.out.print("2 : MODIFIER MEMBRE\n");
		System.out.print("3 : SUPPRIMER MEMBRE\n");
		System.out.print("4 : AFFICHER MEMBRE\n");
		System.out.print("5 : SORTIR\n");
		System.out.print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\nChoix : ");
	}	
	
	private static void vericationDeLaSaisie(int borneInferieure, int borneSuperieure){
		while(entree<borneInferieure || entree>borneSuperieure){
			verificationTypeDeDonnee();
			entree=sc.nextInt();
			if(entree<borneInferieure || entree>borneSuperieure){
				System.out.println("Entrez un numero entre "+borneInferieure+" et "+borneSuperieure+"\n");
			}
		}		
	}
	
	private static void assignerTache() throws SQLException{
		rechercherEtAfficherTaches();
		System.out.print("\nID Tache : ");
		verificationTypeDeDonnee();
		int identifiantTache = sc.nextInt();
		
		rechercherEtAfficherMembres();
		System.out.print("\nID Membre : ");
		verificationTypeDeDonnee();
		int identifiantMembre = sc.nextInt();
		
		baseDeDonnees.assignerTacheAUnMembre(identifiantTache, identifiantMembre);		
	}
	
	private static void afficherTachesMembreParID() throws SQLException{
		rechercherEtAfficherMembres();
		System.out.print("\nID Membre : ");
		verificationTypeDeDonnee();
		int identifiantMembre = sc.nextInt();
		
		List<Tache> taches=baseDeDonnees.afficherTachesMembre(identifiantMembre);
		System.out.println("\nListe des taches du membre en recherchant par son ID\n");
		afficherTaches(taches);		
	}
	
	private static void afficherTachesSelonStatut() throws SQLException{
		System.out.println("Entrez : nouveau, en-progres ou termine pour afficher les taches selon leur statut\n");
		sc.nextLine();
		String statut=sc.nextLine();
		while(!statut.equalsIgnoreCase("nouveau") && !statut.equalsIgnoreCase("en-progres") && !statut.equalsIgnoreCase("termine")){
			System.out.print("STATUT DE LA TACHE : (nouveau, en-progres ou termine)\n");
			statut=sc.nextLine();
			if(!statut.equalsIgnoreCase("nouveau") && !statut.equalsIgnoreCase("en-progres") && !statut.equalsIgnoreCase("termine")){
				System.out.println("Mauvaise saisie. Entrez : nouveau ou en-progres ou termine");
			}
		}
		
		int statusACchercher=0;
		if(statut.equalsIgnoreCase("nouveau")){
			statusACchercher=1;
		}else if(statut.equalsIgnoreCase("en-progres")){
			statusACchercher=2;
		}else{			
			statusACchercher=3;
		}
		List<Object[]> tab = baseDeDonnees.afficherStatusTaches(statusACchercher);
		afficherTachesEtMembresSelonStatut(tab);		
	}
	
	private static void afficherTachesEtMembresSelonStatut(List<Object[]> tab){
		Tache tache;
		Membre membre;
		
		for(Object obj[]: tab){
			tache=(Tache)obj[0];
			membre=(Membre)obj[1];
			System.out.println(tache.getId_Tache());
			System.out.println("\t"+tache.getNomTache());
			System.out.println("\t"+tache.getDesription());
			System.out.println("\t"+tache.getStatut());
			System.out.println("\t"+membre.getId_Membre());
			System.out.println("\t"+membre.getNomMembre());
			System.out.println();
		}
	}
	
	private static void modifierTache() throws SQLException{
		
		List<Tache> taches=rechercherTaches();
		afficherTaches(taches);
		System.out.println("\n\n");	
			
		System.out.println("\n\nEntrez l'identifiant de la tache a modifier");
		verificationTypeDeDonnee();
		int identifiant=sc.nextInt();
		sc.nextLine();
		System.out.println("\n\nEntrez le nom de la tache");
		String nom=sc.nextLine();
		System.out.println("\n\nEntrez la description de la tache");
		String description=sc.nextLine();
		System.out.println("\n\nEntrez le statut de la tache");
		String statut=sc.nextLine();
		List<Membre> membres=rechercherMembres();
		afficherMembres(membres);
		System.out.println("\n\nEntrez l'identifiant du membre associe");
		verificationTypeDeDonnee();
		int identifiantMembre=sc.nextInt();
		
		baseDeDonnees.modifierTache(new Tache(identifiant,nom,description,statut,identifiantMembre));
	}
	
	private static void modifierMembre() throws SQLException{
		List<Membre> membres=rechercherMembres();
		afficherMembres(membres);		
		System.out.println("\n\nEntrez l'identifiant du membre a modifier");
		verificationTypeDeDonnee();
		int identifiant=sc.nextInt();
		sc.nextLine();
		System.out.println("\n\nEntrez le nom du membre");
		String nom=sc.nextLine();
		
		baseDeDonnees.modifierMembre(new Membre(identifiant, nom));
	}
	
	private static void supprimerMembre() throws SQLException{
		List<Membre> membres=rechercherMembres();
		afficherMembres(membres);		
		System.out.println("\n\nEntrez l'identifiant du membre a supprimer");
		verificationTypeDeDonnee();
		int identifiant=sc.nextInt();
		baseDeDonnees.supprimerMembre(identifiant);
	}
	
	private static void supprimerTache() throws SQLException{
		List<Tache> taches=rechercherTaches();
		afficherTaches(taches);		
		System.out.println("\n\nEntrez l'identifiant de la tache a supprimer");
		verificationTypeDeDonnee();
		int identifiant=sc.nextInt();
		baseDeDonnees.supprimerTache(identifiant);		
	}
	
	//Fonction de verification de type 
	private static void verificationTypeDeDonnee(){
		if(!sc.hasNextInt()){
			System.out.print("Saisir votre numero d'operation !!!\nChoix :");
			sc.nextLine();
			verificationTypeDeDonnee();
		}
	}
	
	private static void mettreFinAuProgramme(){
		System.out.println("\nFermeture du programme !\n");
		System.exit(0);
	}
	
	private static void creerTache() throws SQLException{
		int idMembre=0;
		System.out.print("\n\n~~~CREATION DE TACHE~~~\n\n");
		System.out.print("NOM DE LA TACHE : \n");
		sc.nextLine();
		String nom=sc.nextLine();
		System.out.print("DESCRIPTION DE LA TACHE : \n");
		System.out.println("nom Tache" + nom);
		String description=sc.nextLine();
		String statut="";
		while(!statut.equalsIgnoreCase("nouveau") && !statut.equalsIgnoreCase("en-progres") && !statut.equalsIgnoreCase("termine")){
			System.out.print("STATUT DE LA TACHE : (nouveau, en-progres ou termine)\n");
			statut=sc.nextLine();
			if(!statut.equalsIgnoreCase("nouveau") && !statut.equalsIgnoreCase("en-progres") && !statut.equalsIgnoreCase("termine")){
				System.out.println("Mauvaise saisie. Entrez : nouveau ou en-progres ou termine");
			}
		}
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
	
	private static void creerMembre() throws SQLException{
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
	
	private static void afficherMembres(List<Membre> liste){
		System.out.print("\nListe de tous les membres existants dans la base de donnees\n");
		for(Membre membre : liste){
			System.out.print("\n\nID : " + membre.getId_Membre() + " \n");
			System.out.println("NOM : " + membre.getNomMembre() + " \n");
		}		
	}
	
	private static void afficherTaches(List<Tache> liste){
		//System.out.print("\nListe de toutes les taches\n");
		for(Tache tache : liste){
			System.out.print("\n\nID : " + tache.getId_Tache() + " \n");
			System.out.print("NOM : " + tache.getNomTache() + " \n");
			System.out.print("DESCRIPTION : " + tache.getDesription() + " \n");
			System.out.print("STATUT : " + tache.getStatut() + " \n");
			System.out.print("IDMEMBRE ASSIGNE A LA TACHE : " + tache.getId_Membre() + " \n");
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
	
	private static void rechercherEtAfficherTaches() throws SQLException{
		List<Tache> liste=rechercherTaches();
		afficherTaches(liste);
	}
	
	private static void rechercherEtAfficherMembres() throws SQLException{
		List<Membre> liste=rechercherMembres();
		afficherMembres(liste);
	}
}
