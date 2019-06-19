package fr.hn.banque.composant;

public abstract class Compte {
	private String libelle;
	private double solde;
	private int numCompte;
	private Client client;
	private static int staticNumCompte;
	
	public Compte(String libelle, Client client) {
		super();
		this.libelle = libelle;
		this.client = client;
		this.solde=0;
		this.numCompte=staticNumCompte;
		staticNumCompte++;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public double getSolde() {
		return solde;
	}

	
	public void setSolde(Flux flux) {
		if(flux instanceof Credit) {
			solde+=flux.getMontant();
		}else if(flux instanceof Debit) {
			solde-=flux.getMontant();
		}else if(flux instanceof Virement) {
			if(flux.getNumCompteCible()==numCompte) {
				solde+=flux.getMontant();
			}
			if(((Virement) flux).getNumCompteEmetteur()==numCompte) {
				solde-=flux.getMontant();
			}
		}
	}

	public int getNumCompte() {
		return numCompte;
	}

	public void setNumCompte(int numCompte) {
		this.numCompte = numCompte;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public String toString() {
		return "libelle : "+libelle+
				"\nnumcompte : "+numCompte+
				"\nsolde : "+solde+
				"\nnumero client : "+client.getNumero();
	}
	

}
