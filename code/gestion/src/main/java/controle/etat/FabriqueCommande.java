package controle.etat;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import entite.Article;
import entite.Categorie;
import entite.Client;
import entite.Commande;
import entite.Ligne;
import entite.ModeReglements;

public class FabriqueCommande {

	public static JRDataSource getDataSource() {
		Commande commande = new Commande("code_fictif");
		commande.maintenant();
		Client client = new Client();
		client.setCode("CLIENT1");
		client.setNom("Dupont");
		client.setPrenom("Jean");
		commande.setClient(client);
		
		ModeReglements reglement = new ModeReglements();
		reglement.setType("Internet");
		commande.setReglement(reglement);
		
		Ligne premiereLigne = new Ligne();
		Article unArticle = new Article();
		unArticle.setCode("AAA");
		unArticle.setDesignation("Table");
		unArticle.setPrixUnitaire(10d);
		unArticle.setQuantite(20);
		unArticle.setReference(new Categorie().setCode("CAT1").setDesignation("Meubles"));
		premiereLigne.setArticle(unArticle);
		premiereLigne.setQuantite(2);
		commande.ajouter(premiereLigne);

		Ligne deuxiemeLigne = new Ligne();
		Article unAutreArticle = new Article();
		unAutreArticle.setCode("BBB");
		unAutreArticle.setDesignation("Lampe");
		unAutreArticle.setPrixUnitaire(14d);
		unAutreArticle.setQuantite(25);
		unAutreArticle.setReference(new Categorie().setCode("CAT2").setDesignation("Lumière"));
		deuxiemeLigne.setArticle(unAutreArticle);
		deuxiemeLigne.setQuantite(3);
		commande.ajouter(deuxiemeLigne);

		return new JRBeanCollectionDataSource(commande.getLignes());
	}
}
