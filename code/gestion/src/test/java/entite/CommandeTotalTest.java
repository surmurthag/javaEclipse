package entite;

import org.junit.Assert;
import org.junit.Test;

public class CommandeTotalTest {

	@Test
	public void testTotal() {
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

		double totalTTC = commande.getTotalTTC();
		Assert.assertEquals("total incorrect",  62d, totalTTC, 0.0001);
	}
}
