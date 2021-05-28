package entite.crud;

import java.util.List;

import javax.persistence.Query;

import controle.connection.Connexion;
import entite.ModeReglements;

public class ModeReglementsCrud {

	/**
	 * la connexion vers la base de données.
	 */
	private final Connexion laConnexion;

	/**
	 * Constructeur
	 */
	public ModeReglementsCrud(Connexion uneConnexion) {
		laConnexion = uneConnexion;
	}

	/**
	 * Ajout d'un nouvel item dans la base.
	 * 
	 * @param type
	 *            le nom textuel du mode de règlement.
	 * @return le mode de règlement créé ou null si il est impossible de le
	 *         créer.
	 */
	public ModeReglements creer(String type) {
		return laConnexion.modifier((gestionnaireEntite) -> {			
			if ("".equals(type)) {
				throw new IllegalArgumentException(
						"Le nom du mode de reglement est obligatoire");
			} else {
				ModeReglements reglements = new ModeReglements(type);
				gestionnaireEntite.persist(reglements);
				return reglements;
			}
		});
	}

	/**
	 * Lecture des enregistrements.
	 * 
	 * @return la liste des modes de règlement ou une liste vide si aucun
	 *         n'existe.
	 */
	@SuppressWarnings("unchecked")
	public List<ModeReglements> lire() {
		return laConnexion.chercher((gestionnaireEntite) -> {			
			Query requete = gestionnaireEntite
					.createQuery("SELECT m FROM ModeReglements m");
			return requete.getResultList();
		});
	}

	/**
	 * Modification d'une entrée.
	 * 
	 * @param code
	 *            la clé primaire du mode de règlement.
	 * @param type
	 *            la nouvelle description du mode de règlement.
	 * @return true si le mode a été modifié, sinon false.
	 */
	public boolean modifier(int code, String type) {
		return laConnexion.modifier(gestionnaireEntite -> {			
			boolean modifié = false;
			if ("".equals(type)) {
				throw new IllegalArgumentException(
						"Le nom du mode de reglement est obligatoire");
			} else {
				ModeReglements mode = gestionnaireEntite.find(ModeReglements.class, code);
				if (mode != null) {
					mode.setType(type);
					modifié = true;
				}
			}
			return modifié;
		});
	}

	/**
	 * Suppression d'un enregistrement.
	 * 
	 * @param code
	 *            la clé primaire du mode de règlement.
	 * @return true si le mode a été supprimé, sinon false.
	 */
	public boolean supprimer(int code) {
		return laConnexion.modifier((gestionnaireEntite) -> {
			boolean modif = false;
			ModeReglements mode = gestionnaireEntite.find(ModeReglements.class, code);
			if (mode != null) {
				gestionnaireEntite.remove(mode);
				modif = true;
			}
			return modif;
		});
	}
	
	/**
	 * Recherche d'une commande.
	 * 
	 * @param recherche
	 *            une partie du type de mode de règlement à chercher.
	 * @return la liste des modes de règlement ou une liste vide si aucun
	 *         n'existe.
	 */
	@SuppressWarnings("unchecked")
	public List<ModeReglements> chercher(String recherche) {
		return laConnexion.chercher((gestionnaireEntite) -> {			
			String requete = "";
			requete += "SELECT m ";
			requete += "FROM ModeReglements m ";
			requete += "WHERE m.type LIKE '%" + recherche + "%'";
			
			Query query = gestionnaireEntite.createQuery(requete);
			return query.getResultList();
		});
	}
}
