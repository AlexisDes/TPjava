package fr.hn.banque.composant;

import java.util.Date;

public class Flux {
	private String commentaire;
	private int identifiant;
	private double montant;
	private int numCompteCible;
	private boolean effectue;
	private Date date;
	
	public Flux(String commentaire, int identifiant, double montant, int numCompteCible, boolean effectue, Date date) {
		super();
		this.commentaire = commentaire;
		this.identifiant = identifiant;
		this.montant = montant;
		this.numCompteCible = numCompteCible;
		this.effectue = effectue;
		this.date = date;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public int getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(int identifiant) {
		this.identifiant = identifiant;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public int getNumCompteCible() {
		return numCompteCible;
	}

	public void setNumCompteCible(int numCompteCible) {
		this.numCompteCible = numCompteCible;
	}

	public boolean isEffectue() {
		return effectue;
	}

	public void setEffectue(boolean effectue) {
		this.effectue = effectue;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
