package fr.hn.banque.composant;

public class Client {
	private String nom;
	private String prenom;
	private int numero;
	private static int staticNumero;
	
	public Client(String nom, String prenom) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.numero=staticNumero;
		staticNumero++;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	
	public String toString() {
		return "numero : " + numero +"\nnom : "+ nom+"\nprenom : "+prenom;
	}
	

	
	

}
