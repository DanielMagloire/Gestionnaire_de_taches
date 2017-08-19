/**
 *  Author: MEDOU Daniel Magloire
 *  Unité d'enseignement: GENIE LOGICIEL AVANCE
 *  Ecole: IFI
 *  Niveau: Master 1
 *  Option: Systèmes Intelligents et Multimédia (SIM)
 *  Description: TP1, Conception et Réalisation d'une Application de Gestion de Tâches: "GESTACHE"
 */

package org.Tp1.entities;

import java.util.ArrayList;
import java.util.List;

public class Membre {
	//Déclaration des variables
	private int id_Membre;
	private String nomMembre;
	
	//private List<Tache> taches = new ArrayList<>();
	
	
	//Constructeur sans parametres
	public Membre() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//Constructeur avec parametres
	public Membre(int id_Membre, String nomMembre) {
		super();
		this.id_Membre = id_Membre;
		this.nomMembre = nomMembre;
	}

	//GETTERS AND SETTERS
	
	public int getId_Membre() {
		return id_Membre;
	}

	public void setId_Membre(int id_Membre) {
		this.id_Membre = id_Membre;
	}

	public String getNomMembre() {
		return nomMembre;
	}

	public void setNomMembre(String nomMembre) {
		this.nomMembre = nomMembre;
	}

	/*public List<Tache> getTaches() {
		return taches;
	}

	public void setTaches(List<Tache> taches) {
		this.taches = taches;
	}*/
	
	
}
