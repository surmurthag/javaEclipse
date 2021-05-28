package entite.crud;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import controle.connection.Connexion;
import entite.Commande;

public class CommandeCrud {

	private final Connexion laConnexion;

	public CommandeCrud(Connexion connexion) {
		this.laConnexion = connexion;
	}

	/**
	 * Ajout d'une nouvelle commande dans la BD.
	 * @throws EniException si il est impossible de créer la commande.
	 */
	public void creer(Commande commande) throws EniException {
		try {			
			laConnexion.modifier((gestionnaireEntite) -> {				
				gestionnaireEntite.persist(commande);
				return commande;
			});
		} catch(RuntimeException e) {
			throw new EniException(e.getMessage(), e);
		}
	}

	/*
	 * Lecture et récupération des enregistrements de la BD
	 */
	public List<Commande> lire() {
		return laConnexion.chercher((gestionnaireEntite) -> {
			TypedQuery<Commande> query =
					gestionnaireEntite.createQuery("SELECT c FROM Commande c",
													Commande.class);
			
			return query.getResultList();
		});
	}

	/*
	 * Requête équivalente à
	 * 	"SELECT c FROM Commande c WHERE c.code = :code"
	 */
	public Optional<Commande> lire(String code) {
		return laConnexion.chercher((gestionnaireEntite) -> {			
			CriteriaBuilder monteur = gestionnaireEntite.getCriteriaBuilder();
			CriteriaQuery<Commande> critère = monteur.createQuery(Commande.class);
			Root<Commande> objet = critère.from(Commande.class);
			ParameterExpression<String> paramètre = monteur.parameter(String.class);
			critère.select(objet).where(monteur.equal(objet.get("code"), paramètre));
			
			TypedQuery<Commande> query = gestionnaireEntite.createQuery(critère);
			
			query.setParameter("code", code);
			try {
				Commande commande = (Commande) query.getSingleResult();
				return Optional.ofNullable(commande);
			} catch (RuntimeException e) {
				return Optional.empty();
			}
		});
	}

	/*
	 * Modification d'une commande dans la BD
	 */
	public Commande modifier(Commande laCommande) throws EniException {
		
		Object retour = laConnexion.modifier((gestionnaireEntite) -> {
			String code = laCommande.getCode();
			
			Commande commande = gestionnaireEntite.find(Commande.class, code);
			
			if (commande == null) {
				return new EniException("Pas de commande pour le code " + code);
			} else {
				try {
					commande.fusionner(laCommande);
				} catch(RuntimeException e) {
					return new EniException(e.getMessage(), e);
				}
				
				return commande;
			}
		});
		Commande commande = null;
		if (retour instanceof Commande) {
			commande = (Commande) retour;
		}
		if (retour instanceof EniException) {
			throw (EniException) retour;
		}
		return commande;
	}	

	/*
	 * Suppression d'une commande dans la BD
	 */
	public void supprimer(String code) throws EniException{
		EniException probleme = laConnexion.modifier((gestionnaireEntite) -> {
			EniException exception = null;
			Commande commande = gestionnaireEntite.find(Commande.class, code);
			
			if (commande == null) {
				exception = new EniException("Pas de commande pour le code " + code);
			} else {
				try {			
					gestionnaireEntite.remove(commande);
					
				} catch(RuntimeException e) {
					exception = new EniException(e.getMessage(), e);
				}
			}
			return exception;
		});
		if (probleme != null) {
			throw probleme;
		}
	}

	/*
	 * Recherche dans la BD
	 */
	public List<Commande> chercher(String recherche) {
		return laConnexion.chercher((gestionnaireEntite) -> {			
			TypedQuery<Commande> query = gestionnaireEntite.createNamedQuery("chercherCommande", Commande.class);
			query.setParameter("recherche", "%" +recherche +"%");
			return query.getResultList();
		});
	}
}
