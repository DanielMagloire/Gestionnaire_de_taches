/**
 *  Author: MEDOU Daniel Magloire
 *  Unité d'enseignement: GENIE LOGICIEL AVANCE
 *  Ecole: IFI
 *  Niveau: Master 1
 *  Option: Systèmes Intelligents et Multimédia (SIM)
 *  Description: TP1, Conception et Réalisation d'une Application de Gestion de Tâches: "GESTACHE"
 */

package org.Tp1.entities;



public class Tache {
	//Déclaration des variables
	private int id_Tache;
	private String nomTache;
	private String desription;
	private String statut;
	private int Id_Membre; //Cette variable nous permettra de connaitre le membre donc la tache a été assignée
	
	//private Membre membre = new Membre();
	
	
	//Constructeur sans parametres
	public Tache() {
		super();
		// TODO Auto-generated constructor stub
	}

	//Constructeur avec parametres
	

	//Constructeur avec seul parametre
	
	public Tache(int id_Tache) {
		super();
		this.id_Tache = id_Tache;
	}

	public Tache(int id_Tache, String nomTache, String desription, String statut, int id_Membre) {
		super();
		this.id_Tache = id_Tache;
		this.nomTache = nomTache;
		this.desription = desription;
		this.statut = statut;
		Id_Membre = id_Membre;
	}

	//GETTERS ET SETTERS
	
	public int getId_Tache() {
		return id_Tache;
	}

	public void setId_Tache(int id_Tache) {
		this.id_Tache = id_Tache;
	}

	public String getNomTache() {
		return nomTache;
	}

	public void setNomTache(String nomTache) {
		this.nomTache = nomTache;
	}

	public String getDesription() {
		return desription;
	}

	public void setDesription(String desription) {
		this.desription = desription;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public int getId_Membre() {
		return Id_Membre;
	}

	public void setId_Membre(int id_Membre) {
		Id_Membre = id_Membre;
	}

/*	public Membre getMembre() {
		return membre;
	}

	public void setMembre(Membre membre) {
		this.membre = membre;
	}*/

}
