package fr.hn.banque.composant;

import java.util.Date;

public class Debit extends Flux {

	public Debit(String commentaire, int identifiant, double montant, int numCompteCible, boolean effectue, Date date) {
		super(commentaire, identifiant, montant, numCompteCible, effectue, date);
	}

}
