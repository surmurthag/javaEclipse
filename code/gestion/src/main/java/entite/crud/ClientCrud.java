package entite.crud;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import controle.connection.Connexion;
import entite.Client;

public class ClientCrud {

	// Propriété pour établir la connection avec la BD
	// -----------------------------------------------
	private final Connexion laConnexion;

	// CONSTRUCTEUR
	// -------------
	public ClientCrud(Connexion connexion) {
		this.laConnexion = connexion;
	}

	// Ajout d'un nouveau client dans la BD
	// ------------------------------------
	public void creer(Client client) {
		laConnexion.modifier((gestionnaireEntite) -> {			
			gestionnaireEntite.persist(client);
			return client;
		});
	}

	/*
	 * Lecture et récupération des enregistrements de la BD
	 */
	@SuppressWarnings("unchecked")
	public List<Client> lire() {
		return laConnexion.chercher((gestionnaireEntite) -> {
			Query query = gestionnaireEntite.createQuery("SELECT c FROM Client c");
			return query.getResultList();			
		});
	}

	public Client lire(String code) {
		StringBuilder requete = new StringBuilder("SELECT c FROM Client c ");
		requete.append("WHERE c.code = ");
		requete.append("'");
		requete.append(code);
		requete.append("'");
		return laConnexion.chercher((gestionnaireEntite) -> {
			Query query = gestionnaireEntite.createQuery(requete.toString());
			
			try {
				return (Client) query.getSingleResult();
			} catch (NoResultException e) {
				return null;
			}
		});
	}

	// Modification d'un client dans la BD
	// -----------------------------------
	public Client modifier(Client leClient) {
		return laConnexion.modifier((gestionnaireEntite) -> {			
			String code = leClient.getCode();
			Client client = gestionnaireEntite.find(Client.class, code);
			
			if (client == null) {
				throw new IllegalArgumentException("Pas de client pour le code " + code);
			} else {
				client.setNom(leClient.getNom());
				client.setPrenom(leClient.getPrenom());
				client.setCarteFidele(leClient.isCarteFidele());				
				return client;
			}
		});
	}

	// Suppression d'un client dans la BD
	// ----------------------------------
	public boolean supprimer(String code) {
		return laConnexion.modifier((gestionnaireEntite) -> {
			
			// Vérifier avant qu'il n'existe aucune commande
			String requeteClient = "SELECT COUNT(c.code) FROM Commande c "
					+ " WHERE c.client.code LIKE '" + code + "'";
			Query requete = gestionnaireEntite.createQuery(requeteClient);
			
			long result = (long) requete.getSingleResult();
			if (result != 0) {
				throw new IllegalArgumentException(
						"Il existe des commandes ("
						+ result + ") pour ce client."
						+ " Suppression interdite.");
			}
			Client client = gestionnaireEntite.find(Client.class, code);
			
			if (client == null) {
				throw new IllegalArgumentException(
						"Aucun enregistrement correspondant au code "
						+ code + ".");
			} else {
				gestionnaireEntite.remove(client);
			}
			return true;
		});
	}

	// Recherche dans la BD --> avec une chaîne de caracactère(s) quelconque
	@SuppressWarnings("unchecked")
	public List<Client> chercher(String recherche) {
		return laConnexion.chercher((gestionnaireEntite) -> {
			Query query = gestionnaireEntite.createNamedQuery("chercherClient");
			query.setParameter("recherche", "%" + recherche + "%");
			return query.getResultList();			
		});
	}

	// Recherche dans la BD
	// --------------------
	@SuppressWarnings("unchecked")
	public List<Client> chercher(String unCode, String unNom, String unPrenom) {
		String code, nom, prenom;
		if (unCode.equals("")) {
			code = "%";
		} else {
			code = "%" +unCode +"%";
		}
		if (unNom.equals("")) {
			nom = "%";
		} else {
			nom = "%" +unNom +"%";
		}
		if (unPrenom.equals("")) {
			prenom = "%";
		} else {
			prenom = "%" +unPrenom +"%";
		}

		return laConnexion.chercher((gestionnaireEntite) -> {			
			Query query = gestionnaireEntite.createNamedQuery("chercherPrecis");
			query.setParameter(1, code);
			query.setParameter(2, nom);
			query.setParameter(3, prenom);
			
			return query.getResultList();
		});
	}
}
