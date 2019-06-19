package fr.hn.banque.composant;

import java.util.Date;

public class Virement extends Flux{
	private int numCompteEmetteur;

	public Virement(String commentaire, int identifiant, double montant, int numCompteCible, boolean effectue,
			Date date, int numCompteEmetteur) {
		super(commentaire, identifiant, montant, numCompteCible, effectue, date);
		this.numCompteEmetteur=numCompteEmetteur;
	}

	public int getNumCompteEmetteur() {
		return numCompteEmetteur;
	}

	public void setNumCompteEmetteur(int numCompteEmetteur) {
		this.numCompteEmetteur = numCompteEmetteur;
	}

	
}
