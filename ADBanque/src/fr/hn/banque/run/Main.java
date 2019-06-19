package fr.hn.banque.run;

import java.util.Date;
import java.util.Hashtable;
import java.util.logging.Logger;

import fr.hn.banque.composant.*;

public class Main {

	public static void main(String[] args) {
		
		Client[] tabClient=generateClient(4);
		afficherClient(tabClient);
		
		Compte[] tabCompte=generateCompte(tabClient);
		afficherCompte(tabCompte);
		
		LOGGER.info("----------------------------");
		Hashtable<Integer, Compte> ht= generateHashtable(tabCompte);
//		afficherHashtable(ht);
		
		Flux[] tabFlux=genererFlux(tabCompte);
		
		majSolde(tabFlux, ht);
		afficherCompte(tabCompte);
	}
	

	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

	/*methode Client
	 * 
	 */
	private static Client[] generateClient(int nbClient) {
		Client[] tabClient= new Client[nbClient];
		for(int i=0;i<nbClient;i++) {
			tabClient[i] = new Client("nom"+i," prenom"+i); 
		}
		return tabClient;
	}
	
	public static void afficherClient(Client[] tab) {
		String msg;
		for(Client client : tab) {
			msg=client.toString();
			LOGGER.info(msg);
		}
	}
	 /*
	  * methode compte
	  */
	private static Compte[] generateCompte(Client[] tabClient) {
		Compte[] tabCompte = new Compte[2*tabClient.length];
		int i=0;
		for(Client client :tabClient) {
			tabCompte[i]= new CompteCourant("Compte courant", client);
			i++;
			tabCompte[i]= new CompteEpargne("compte epargne", client);
			i++;
		}
		return tabCompte;
	}
	
	public static void afficherCompte(Compte[] tab) {
		String msg;
		for(Compte compte:tab) {
			msg=compte.toString();
			LOGGER.info(msg);
		}
	}
	
	/*
	 * Methode Hashtable
	 */
	private static Hashtable<Integer, Compte> generateHashtable(Compte[] tab) {
		Hashtable<Integer, Compte> ht= new Hashtable<>();
		for(Compte c : tab) {
			ht.put(c.getNumCompte(), c);
		}
		
		return ht;
	}
	
	private static void afficherHashtable(Hashtable<Integer, Compte> ht) {
		String msg = ht.toString();
		LOGGER.info(msg);
		
	}
	
	/*
	 * Methodes Flux
	 * 
	 */

	private static Flux[] genererFlux(Compte[] tabCompte) {
		Flux[] flux = new Flux[tabCompte.length+2];
		int i=0;
		flux[i]= new Debit("", i, 50, 1, false, new Date());
		i++;
		for(Compte compte:tabCompte) {
			if(compte instanceof CompteCourant) {
				flux[i]= new Credit("credit", i, 100.5,compte.getNumCompte(),false,new Date());
				i++;
			}
			else if(compte instanceof CompteEpargne){
				flux[i]= new Credit("credit", i, 1500,compte.getNumCompte(),false,new Date());
				i++;
			}
		}
		flux[i] = new Virement("virement", i, 50, 2, false, new Date(), 1);
		return flux;
	}
	
	private static void majSolde(Flux[] tabFlux, Hashtable<Integer, Compte> htCompte) {
		for(Flux flux:tabFlux) {
			htCompte.get(flux.getNumCompteCible()).setSolde(flux);
			if(flux instanceof Virement) {
				htCompte.get(((Virement) flux).getNumCompteEmetteur()).setSolde(flux);
			}
		}
	}

}
