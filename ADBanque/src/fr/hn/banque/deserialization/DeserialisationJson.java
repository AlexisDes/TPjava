package fr.hn.banque.deserialization;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "type", "commentaire", "montant", "numCompteCible", "numCompteEmetteur" })
public class DeserialisationJson {

	@JsonProperty("type")
	private String type;
	@JsonProperty("commentaire")
	private String commentaire;
	@JsonProperty("montant")
	private Integer montant;
	@JsonProperty("numCompteCible")
	private Integer numCompteCible;
	@JsonProperty("numCompteEmetteur")
	private Integer numCompteEmetteur;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("commentaire")
	public String getCommentaire() {
		return commentaire;
	}

	@JsonProperty("commentaire")
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	@JsonProperty("montant")
	public Integer getMontant() {
		return montant;
	}

	@JsonProperty("montant")
	public void setMontant(Integer montant) {
		this.montant = montant;
	}

	@JsonProperty("numCompteCible")
	public Integer getNumCompteCible() {
		return numCompteCible;
	}

	@JsonProperty("numCompteCible")
	public void setNumCompteCible(Integer numCompteCible) {
		this.numCompteCible = numCompteCible;
	}

	@JsonProperty("numCompteEmetteur")
	public Integer getNumCompteEmetteur() {
		return numCompteEmetteur;
	}

	@JsonProperty("numCompteEmetteur")
	public void setNumCompteEmetteur(Integer numCompteEmetteur) {
		this.numCompteEmetteur = numCompteEmetteur;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}