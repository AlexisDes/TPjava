package fr.hn.banque.run;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.hn.banque.composant.Client;
import fr.hn.banque.composant.Compte;
import fr.hn.banque.composant.CompteCourant;
import fr.hn.banque.composant.CompteEpargne;
import fr.hn.banque.composant.Credit;
import fr.hn.banque.composant.Debit;
import fr.hn.banque.composant.Flux;
import fr.hn.banque.composant.Virement;
import fr.hn.banque.deserialization.DeserialisationJson;

public class Main {

	public static void main(String[] args) {

		Client[] tabClient = generateClient(4);
		afficherClient(tabClient);

		Compte[] tabCompte = generateCompte(tabClient);
		afficherCompte(tabCompte);

		LOGGER.info("----------------------------");
		Hashtable<Integer, Compte> ht = generateHashtable(tabCompte);
		afficherHashtable(ht);

		Flux[] tabFlux = genererFlux(tabCompte);

		majSolde(tabFlux, ht);
		afficherCompte(tabCompte);

		Flux[] tabFluxJson = null;
		// Read Json File
		try {
			DeserialisationJson[] objJson = mapper.readValue(new File("Flux.json"), DeserialisationJson[].class);
			tabFluxJson = genererFlux(objJson);
		} catch (IOException e) {
			LOGGER.log(Level.WARNING,e.toString());
		}
		majSolde(tabFluxJson, ht);
		afficherCompte(tabCompte);

		Compte[] tabComptefromXml = generateCompteXml(tabClient);
		afficherCompte(tabComptefromXml);
	}

	private static final ObjectMapper mapper = new ObjectMapper();
	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
	private static final String CREDIT ="credit";

	/*
	 * methode Client
	 * 
	 */
	private static Client[] generateClient(int nbClient) {
		Client[] tabClient = new Client[nbClient];
		for (int i = 0; i < nbClient; i++) {
			tabClient[i] = new Client("nom" + i, " prenom" + i);
		}
		return tabClient;
	}

	public static void afficherClient(Client[] tab) {
		String msg;
		for (Client client : tab) {
			msg = client.toString();
			LOGGER.info(msg);
		}
	}

	/*
	 * methode compte
	 */
	private static Compte[] generateCompte(Client[] tabClient) {
		Compte[] tabCompte = new Compte[2 * tabClient.length];
		int i = 0;
		for (Client client : tabClient) {
			tabCompte[i] = new CompteCourant("Compte courant", client);
			i++;
			tabCompte[i] = new CompteEpargne("compte epargne", client);
			i++;
		}
		return tabCompte;
	}

	public static void afficherCompte(Compte[] tab) {
		String msg;
		for (Compte compte : tab) {
			msg = compte.toString();
			LOGGER.info(msg);
		}
	}

	/*
	 * Methode Hashtable
	 */
	private static Hashtable<Integer, Compte> generateHashtable(Compte[] tab) {
		Hashtable<Integer, Compte> ht = new Hashtable<>();
		for (Compte c : tab) {
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
		Flux[] flux = new Flux[tabCompte.length + 2];
		int i = 0;
		flux[i] = new Debit("", i, 50, 1, false, new Date());
		i++;
		for (Compte compte : tabCompte) {
			if (compte instanceof CompteCourant) {
				flux[i] = new Credit(CREDIT, i, 100.5, compte.getNumCompte(), false, new Date());
			} else if (compte instanceof CompteEpargne) {
				flux[i] = new Credit(CREDIT, i, 1500, compte.getNumCompte(), false, new Date());
			}
			i++;
		}
		flux[i] = new Virement("virement", i, 50, 2, false, new Date(), 1);
		return flux;
	}

	private static void majSolde(Flux[] tabFlux, Hashtable<Integer, Compte> htCompte) {
		for (Flux flux : tabFlux) {
			htCompte.get(flux.getNumCompteCible()).setSolde(flux);
			if (flux instanceof Virement) {
				htCompte.get(((Virement) flux).getNumCompteEmetteur()).setSolde(flux);
			}
		}
	}

	/*
	 * Methode Flux from Json
	 */
	private static Flux[] genererFlux(DeserialisationJson[] objJson) {

		Flux[] tabFlux = new Flux[objJson.length];
		int i = 0;
		for (DeserialisationJson dJson : objJson) {
			if (CREDIT.equalsIgnoreCase(dJson.getType())) {
				tabFlux[i] = new Credit(dJson.getCommentaire(), i, dJson.getMontant(), dJson.getNumCompteCible(), false,
						new Date());
			} else if ("Debit".equalsIgnoreCase(dJson.getType())) {
				tabFlux[i] = new Debit(dJson.getCommentaire(), i, dJson.getMontant(), dJson.getNumCompteCible(), false,
						new Date());
			} else if ("Virement".equalsIgnoreCase(dJson.getType())) {
				tabFlux[i] = new Virement(dJson.getCommentaire(), i, dJson.getMontant(), dJson.getNumCompteCible(),
						false, new Date(), dJson.getNumCompteEmetteur());
			}
			i++;
		}
		return tabFlux;
	}
	/*
	 * Methode Compte from xml
	 */

	private static Compte[] generateCompteXml(Client[] tabClient) {
		Compte[] tabCompte=null;
		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);
			factory.setValidating(false);

			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File("Comptes.xml"));
			doc.getDocumentElement().normalize();

			NodeList listeCc = doc.getElementsByTagName("CompteCourant");
			NodeList listeCe = doc.getElementsByTagName("CompteEpargne");

			Node cC;
			Element e;

			tabCompte = new Compte[listeCc.getLength() + listeCe.getLength()];
			int lengthCc=listeCc.getLength();

			for (int i = 0; i < lengthCc; i++) {

				cC = listeCc.item(i);
				e = (Element) cC;
				int numclient = Integer.parseInt(e.getElementsByTagName("client").item(0).getTextContent());
				tabCompte[i] = new CompteCourant(e.getElementsByTagName("libelle").item(0).getTextContent(), tabClient[numclient]);

			}
			for (int i = 0; i < listeCe.getLength(); i++) {

				cC = listeCe.item(i);
				e = (Element) cC;
				int numclient = Integer.parseInt(e.getElementsByTagName("client").item(0).getTextContent());
				tabCompte[i + lengthCc] = new CompteCourant(
						e.getElementsByTagName("libelle").item(0).getTextContent(),
						tabClient[numclient]);

			}
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error reading configuration file:");
			LOGGER.log(Level.WARNING, e.getMessage());
		}
		return tabCompte;
	}
}
