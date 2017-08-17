/**
 *  Unité d'enseignement: GENIE LOGICIEL AVANCE 
 *  Author: MEDOU Daniel Magloire
 *  Ecole: IFI
 *  Niveau: Master 1
 *  Option: Systèmes Intelligents et Multimédia
 *  Date: 17 Août 2017
 *  Description: TP1
 */

package org.Tp1;

import java.util.List;
import java.util.Scanner;

import org.Tp1.entities.Membre;
import org.Tp1.entities.Tache;

public class ApplicationTP1 {

	private static Scanner sc = new  Scanner(System.in);

	
	public static void main(String[] args) {
		int entree=0;
		
		accueil();
		
		while(true){
			menuPrincipal();
			while(entree<1 || entree>3){
				verificationDeLaSaisie();
				entree=sc.nextInt();
				if(entree<1 || entree>3){
					System.out.println("`Entrez un numero qui est dans le menu\n");
				}
			}
			entree=0;
		}
		
	}
	
	public static void accueil(){
		System.out.println("\n\n\t\t\t\t\t===============================================");
		System.out.println("\t\t\t\t\t!!! MODULE DE GENIE LOGICIEL AVANCE!!!\n\n");
		System.out.println("\t\t\t\t\t TP1.  MEDOU DANIEL MAGLOIRE\n");
		System.out.println("\t\t\t\t\t===============================================\n\n");
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
		System.out.print("\n\n\t\tMENU PRINCIPAL DE GESTACHE\n");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		System.out.print("1 : CREER TACHE\n");
		System.out.print("2 : MODIFIER TACHE\n");
		System.out.print("3 : SUPPRIMER TACHE\n");
		System.out.print("4 : RECHERCHER TACHE\n");
		System.out.print("5 : AFFICHER TACHE\n");
		System.out.print("6 : FIN\n");
		System.out.print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\nChoix : ");
	}
	
	public static void menuMembre(){
		System.out.print("\n\n\t\tMENU PRINCIPAL DE GESTACHE\n");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		System.out.print("1 : GESTION DES TACHES\n");
		System.out.print("2 : GESTION DES MEMBRES\n");
		System.out.print("3 : FIN\n");
		System.out.print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\nChoix : ");
	}
	
	//Fonction de verification de type 
	public static void verificationDeLaSaisie(){
		if(!sc.hasNextInt()){
			System.out.print("Saisir votre numéro d'opération !!!\nChoix :");
			sc.nextLine();
			verificationDeLaSaisie();
		}
	}
}
